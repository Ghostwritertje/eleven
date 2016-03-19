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

        Query query = sessionFactory.getCurrentSession().createQuery("from Statement s where s.aankomstRekening.nummer = :aankomstRekeningNummer");
        query.setParameter("aankomstRekeningNummer", rekening.getNummer());
        List<Statement> statements = query.list();

        query = sessionFactory.getCurrentSession().createQuery("from Statement s where s.vertrekRekening.nummer = :aankomstRekeningNummer");
        query.setParameter("aankomstRekeningNummer", rekening.getNummer());
        List<Statement> otherStatements = query.list();

        for (Statement statement : otherStatements) {
            statement.setBedrag(-Math.abs(statement.getBedrag()));
            statements.add(statement);
        }
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
