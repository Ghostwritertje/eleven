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
import java.util.List;

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
            statement.setBedrag(statement.getBedrag());
            statements.add(statement);
        }
        System.out.println("Aantal statements: " + statements.size());
        Collections.sort(statements, new Comparator<Statement>() {
            @Override
            public int compare(Statement o1, Statement o2) {
                return o1.getDatum().compareTo(o2.getDatum());
            }
        });
        transaction.commit();
        return statements;
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
