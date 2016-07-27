package be.estateeleven.dao.impl;

import be.estateeleven.dao.HibernateUtil;
import be.estateeleven.dao.api.UserDao;
import be.estateeleven.domain.User;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();


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
}
