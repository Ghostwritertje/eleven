package be.ghostwritertje.budgetting.dao.impl;

import be.ghostwritertje.budgetting.dao.api.UserDao;
import be.ghostwritertje.budgetting.domain.User;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
@Repository("userDaoImpl")
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public void create(final User user) {
        Transaction transaction = sessionFactory.getCurrentSession().beginTransaction();
        try {

            sessionFactory.getCurrentSession().saveOrUpdate(user);
            transaction.commit();
        } catch (ConstraintViolationException e) {
            transaction.rollback();
        }
    }


    @Override
    public User findUser(String username) {
        Transaction transaction = sessionFactory.getCurrentSession().beginTransaction();
        Query query = sessionFactory.getCurrentSession().createQuery("from User u where u.username = :name");
        query.setParameter("name", username);
        try {
            return (User) query.list().get(0);
        } catch (Exception e) {
            return null;
        }
    }


}