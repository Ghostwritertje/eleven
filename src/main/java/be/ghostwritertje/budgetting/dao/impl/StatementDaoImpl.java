package be.ghostwritertje.budgetting.dao.impl;

import be.ghostwritertje.budgetting.dao.api.StatementDao;
import be.ghostwritertje.budgetting.domain.Rekening;
import be.ghostwritertje.budgetting.domain.Statement;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
@Repository
public class StatementDaoImpl implements StatementDao {
    @Autowired
    private SessionFactory sessionFactory;

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


}
