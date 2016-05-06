package be.ghostwritertje.budgetting.dao.impl;

import be.ghostwritertje.budgetting.dao.HibernateUtil;
import be.ghostwritertje.budgetting.dao.api.RekeningDao;
import be.ghostwritertje.budgetting.dao.api.StatementDao;
import be.ghostwritertje.budgetting.domain.Categorie;
import be.ghostwritertje.budgetting.domain.Rekening;
import be.ghostwritertje.budgetting.domain.Statement;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
@Repository
public class StatementDaoImpl implements StatementDao {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Autowired
    private RekeningDao rekeningDao;

    @Override
    public List<Statement> getStatements(Rekening rekening) {

        Transaction transaction = sessionFactory.getCurrentSession().beginTransaction();
        //TODO_JORAN: schrijf dit in één query
        Query query = sessionFactory.getCurrentSession().createQuery("" +
                "from Statement s " +
                "where  s.datum < :datum AND  s.aankomstRekening.nummer = :aankomstRekeningNummer "/* +
                "or s.vertrekRekening.nummer = :aankomstRekeningNummer " +
                "order by s.datum desc"*/);
        query.setParameter("aankomstRekeningNummer", rekening.getNummer());
        LocalDate datum = LocalDate.now();
        query.setParameter("datum", datum.toDate());
        final List<Statement> statements = query.list();

        query = sessionFactory.getCurrentSession().createQuery("from Statement s where s.vertrekRekening.nummer = :vertrekRekeningNummer");
        query.setParameter("vertrekRekeningNummer", rekening.getNummer());
        List<Statement> otherStatements = query.list();

        for (Statement statement : otherStatements) {
            statements.add(statement);
        }
        Collections.sort(statements, new Comparator<Statement>() {
            @Override
            public int compare(Statement o1, Statement o2) {
                return o2.getDatum().compareTo(o1.getDatum());
            }
        });
        transaction.commit();
        return statements;
    }

    @Deprecated
    public double getTotaalTussenDatumsOld(Rekening rekening, Date beginDatum, Date eindDatum) {
        Transaction transaction = sessionFactory.getCurrentSession().beginTransaction();

        Query query = sessionFactory.getCurrentSession().createQuery("SELECT SUM(s.bedrag) " +
                " FROM Statement s \n" +
                " WHERE s.aankomstRekening.nummer = :rekening AND s.datum BETWEEN :beginDatum and :eindDatum");
        query.setParameter("rekening", rekening.getNummer());
        query.setParameter("beginDatum", beginDatum);
        query.setParameter("eindDatum", eindDatum);
        double voorlopigTotaal = (double) query.uniqueResult();

        query = sessionFactory.getCurrentSession().createQuery("SELECT SUM(t.bedrag)" +
                "FROM Statement t  " +
                "WHERE t.vertrekRekening.nummer = :rekening AND t.datum BETWEEN :beginDatum and :eindDatum");
        query.setParameter("rekening", rekening.getNummer());
        query.setParameter("beginDatum", beginDatum);
        query.setParameter("eindDatum", eindDatum);
        voorlopigTotaal -= (double) query.uniqueResult();

        transaction.commit();
        return voorlopigTotaal;
    }

    @Override
    public Map<String, Double> getTotalenPerMaand(Rekening rekening) {
        Transaction transaction = sessionFactory.getCurrentSession().beginTransaction();


        Query query = sessionFactory.getCurrentSession().createQuery("SELECT\n" +
                "  concat(year(s.datum), '/', month(s.datum)),\n" +
                "  IFNULL((SELECT sum(t.bedrag)\n" +
                "          FROM Statement t\n" +
                "          WHERE t.aankomstRekening.nummer = :rekeningNummer and\n" +
                "            (year(t.datum) < year(s.datum) OR (year(t.datum) = year(s.datum) AND month(t.datum) <= month(s.datum)))\n" +
                "            ), 0) -\n" +
                "  IFNULL((SELECT sum(t.bedrag)\n" +
                "          FROM Statement t\n" +
                "          WHERE t.vertrekRekening.nummer = :rekeningNummer AND\n" +
                "                (year(t.datum) < year(s.datum) OR (year(t.datum) = year(s.datum) AND month(t.datum) <= month(s.datum)))\n" +
                "\n" +
                "         ), 0)\n" +
                "\n" +
                "FROM Statement s\n" +
                "WHERE s.datum < :datum " +
                "GROUP BY MONTH(s.datum), YEAR(s.datum)\n" +
                "ORDER BY concat(year(s.datum), '/', month(s.datum))");
        query.setParameter("rekeningNummer", rekening.getNummer());
        LocalDate datum = LocalDate.now();
        query.setParameter("datum", datum.toDate());
        List<Object[]> objects = query.list();

        Map<String, Double> waarden = new HashMap<>();
        for (Object[] object : objects) {
            waarden.put((String) object[0], (Double) object[1]);
        }

        transaction.commit();
        return waarden;
    }

    private List<Rekening> getRekeningen(String username) {
        Transaction transaction = sessionFactory.getCurrentSession().beginTransaction();

        Query query = sessionFactory.getCurrentSession().createQuery("from Rekening r where r.user.username = :username");
        query.setParameter("username", username);
        List<Rekening> rekeningen = query.list();
        transaction.commit();
        return rekeningen;

    }

    @Override
    public List<Map<String, Double>> getTotaleUitgavenPerMaand(String username) {
        Transaction transaction = sessionFactory.getCurrentSession().beginTransaction();

        List<Map<String, Double>> lijst = new ArrayList<>();
        for (Categorie categorie : Categorie.values()) {

            Query query = sessionFactory.getCurrentSession().createQuery("SELECT\n" +
                    "  concat(year(s.datum), '/', month(s.datum)),\n" +
                    "  IFNULL((SELECT sum(t.bedrag)\n" +
                    "          FROM Statement t\n" +
                    "          WHERE" +
                    "          (year(t.datum) = year(s.datum)  AND month(t.datum) = month(s.datum))\n" +
                    "            ), 0) -\n" +
                    "  0.00\n" +
                    "\n" +
                    "FROM Statement s\n" +
                    "WHERE s.datum < :datum " +
                    "AND s.categorie = :categorie " +
                    " and s.vertrekRekening.user.username = :username \n" +
                    " and s.aankomstRekening IS NULL  \n" +
                    "GROUP BY MONTH(s.datum), YEAR(s.datum)\n" +
                    "ORDER BY concat(year(s.datum), '/', month(s.datum))");
//            query.setParameter("rekeningenLijst", this.getRekeningen("Joran"));
            System.out.println("2.1");
            query.setParameter("username", "Joran");
            LocalDate datum = LocalDate.now();
            query.setParameter("datum", datum.toDate());
            query.setParameter("categorie", categorie);
            List<Object[]> objects = query.list();

            Map<String, Double> waarden = new HashMap<>();
            for (Object[] object : objects) {
                waarden.put((String) object[0], (Double) object[1]);
            }
            lijst.add(waarden);
        }

        transaction.commit();
        return lijst;
    }


    public Map<String, Double> getTotaalPerCategorie(String username) {
        Transaction transaction = sessionFactory.getCurrentSession().beginTransaction();

        Query query = sessionFactory.getCurrentSession().createQuery("SELECT t.categorie, sum(bedrag)\n" +
                "FROM Statement t\n" +
                "WHERE t.datum < :datum AND t.aankomstRekening.user.username = :username \n" +
                "AND  t.vertrekRekening IS NULL " + //TODO_JORAN: Case voor rekening van andere gebruiker?
                "GROUP BY t.categorie");
        query.setParameter("username", username);
        LocalDate datum = LocalDate.now();
        query.setParameter("datum", datum.toDate());
        List<Object[]> objects = query.list();

        Map<String, Double> waarden = new HashMap<>();
        for (Object[] object : objects) {
            waarden.put(object[0].toString(), (Double) object[1]);
            System.out.println(object[0].toString() + ": " + object[1]);
        }

        transaction.commit();
        return waarden;
    }


    @Override
    public void createStatement(Statement statement) {
        Transaction transaction = sessionFactory.getCurrentSession().beginTransaction();
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(statement);
            transaction.commit();
        } catch (ConstraintViolationException e) {
            System.err.println("Contraint violation exception");
            transaction.rollback();
        }
    }

    @Override
    public void deleteAllStatements() {
        Transaction transaction = sessionFactory.getCurrentSession().beginTransaction();

        Query query = sessionFactory.getCurrentSession().createQuery("delete from Statement r");
        query.executeUpdate();

        transaction.commit();
    }

    @Override
    public void createStatement(String vertrekRekeningNummer, String aankomstRekeningNummer, double bedrag, Date datum) {
        Transaction transaction = sessionFactory.getCurrentSession().beginTransaction();
        try {
            Rekening aankomstRekening = rekeningDao.getRekening(aankomstRekeningNummer);
            Rekening vertrekRekening = rekeningDao.getRekening(vertrekRekeningNummer);
            Statement statement = new Statement(vertrekRekening, aankomstRekening, bedrag, datum);
            sessionFactory.getCurrentSession().saveOrUpdate(statement);
            transaction.commit();
        } catch (ConstraintViolationException e) {
            transaction.rollback();
        }
    }

    @Override
    public List<Statement> getInkomendeStatementsOverAlleRekeningen(String userName) {
        Transaction transaction = sessionFactory.getCurrentSession().beginTransaction();

        Query query = sessionFactory.getCurrentSession().createQuery(" " +
                "FROM Statement t\n" +
                "WHERE t.datum < :datum AND t.aankomstRekening.Id IN (SELECT r.Id\n" +
                "                               FROM Rekening r\n" +
                "                               WHERE r.user.username = :userName)\n" +
                "      AND (t.vertrekRekening IS NULL OR t.vertrekRekening.Id NOT IN (SELECT r.Id\n" +
                "                                                                  FROM Rekening r\n" +
                "                                                                  WHERE r.user.username = :userName))");
        query.setParameter("userName", userName);
        LocalDate datum = LocalDate.now();
        query.setParameter("datum", datum.toDate());
        final List<Statement> statements = query.list();

        transaction.commit();
        return statements;
    }


}
