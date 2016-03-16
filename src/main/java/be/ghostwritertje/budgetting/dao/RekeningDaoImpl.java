package be.ghostwritertje.budgetting.dao;

import be.ghostwritertje.budgetting.domain.Rekening;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
}
