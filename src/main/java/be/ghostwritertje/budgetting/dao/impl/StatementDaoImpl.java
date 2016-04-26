package be.ghostwritertje.budgetting.dao.impl;

import be.ghostwritertje.budgetting.dao.HibernateUtil;
import be.ghostwritertje.budgetting.dao.api.RekeningDao;
import be.ghostwritertje.budgetting.dao.api.StatementDao;
import be.ghostwritertje.budgetting.domain.Rekening;
import be.ghostwritertje.budgetting.domain.Statement;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
                "where s.aankomstRekening.nummer = :aankomstRekeningNummer "/* +
                "or s.vertrekRekening.nummer = :aankomstRekeningNummer " +
                "order by s.datum desc"*/);
        query.setParameter("aankomstRekeningNummer", rekening.getNummer());
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
                return o1.getDatum().compareTo(o2.getDatum());
            }
        });
        transaction.commit();
        return statements;
    }

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

        // TODO_JORAN: schrijf dit in één query
        /*
        SELECT
             concat(year(s.datum), '/', month(s.datum)) AS "nieuweDatum",
            IFNULL((SELECT sum(t.bedrag)
            FROM t_statement t
             WHERE
             concat(year(t.datum), '/', MONTH(t.datum)) = nieuweDatum AND
              s.aankomstRekeningId = 94), 0) -
             IFNULL((SELECT sum(t.bedrag)
             FROM t_statement t
             WHERE
            concat(year(t.datum), '/', MONTH(t.datum)) = nieuweDatum AND
            s.vertrekRekeningId = 94), 0)

           AS 'totaalMin'

            FROM t_statement s
            GROUP BY MONTH(s.datum), YEAR(s.datum)
            ORDER BY nieuweDatum;

         */

        Query query = sessionFactory.getCurrentSession().createQuery("SELECT\n" +
                " concat(year(s.datum), '/', month(s.datum))," +
                " IFNULL((SELECT sum(t.bedrag)\n" +
                " FROM Statement t\n" +
                "  WHERE\n" +
                "  year(t.datum) < year(s.datum) OR ( year(t.datum) = year(s.datum) AND month(t.datum) < month(s.datum))  AND\n" +
                "   t.aankomstRekening.nummer = :rekeningNummer), 0) -\n" +
                "  IFNULL((SELECT sum(t.bedrag)\n" +
                "  FROM Statement t\n" +
                "  WHERE\n" +
                " year(t.datum) < year(s.datum) OR ( year(t.datum) = year(s.datum) AND month(t.datum) < month(s.datum))  AND\n" +
                " t.vertrekRekening.nummer = :rekeningNummer), 0)\n" +
                " FROM Statement s\n" +
                " GROUP BY MONTH(s.datum), YEAR(s.datum)\n" +
                " ORDER BY year(s.datum), month(s.datum)");
        query.setParameter("rekeningNummer", rekening.getNummer());
        List<Object[]> objects = query.list();

        Map<String, Double> waarden = new HashMap<>();
        for (Object[] object : objects) {
            waarden.put((String) object[0], (Double) object[1]);
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


}
