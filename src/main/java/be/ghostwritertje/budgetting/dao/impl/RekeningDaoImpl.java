package be.ghostwritertje.budgetting.dao.impl;

import be.ghostwritertje.budgetting.dao.api.RekeningDao;
import be.ghostwritertje.budgetting.domain.Rekening;
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
public class RekeningDaoImpl implements RekeningDao {
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public List<Rekening> getRekeningen(String username) {
        Transaction transaction = sessionFactory.getCurrentSession().beginTransaction();

        Query query = sessionFactory.getCurrentSession().createQuery("from Rekening r where r.user.username = :username");
        query.setParameter("username", username);


        return query.list();

    }

    @Override
    public void create(final Rekening rekening) {
        Transaction transaction = sessionFactory.getCurrentSession().beginTransaction();
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(rekening);
            transaction.commit();
        } catch (ConstraintViolationException e) {
            transaction.rollback();
        }
    }

    public double getBalans(final Rekening rekening) {
        Transaction transaction = sessionFactory.getCurrentSession().beginTransaction();

        Query query = sessionFactory.getCurrentSession().createQuery("select sum(s.bedrag) from Statement s where s.aankomstRekening.nummer = :rekeningNummer");
        query.setParameter("rekeningNummer", rekening.getNummer());

        double balans = (double) query.uniqueResult();

        query = sessionFactory.getCurrentSession().createQuery("select sum(s.bedrag) from Statement s where s.vertrekRekening.nummer = :rekeningNummer");
        query.setParameter("rekeningNummer", rekening.getNummer());

        balans -= (double) query.uniqueResult();

        return balans;
    }
}
