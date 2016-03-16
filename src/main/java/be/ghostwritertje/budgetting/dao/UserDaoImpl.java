package be.ghostwritertje.budgetting.dao;

import be.ghostwritertje.budgetting.domain.Rekening;
import be.ghostwritertje.budgetting.domain.User;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
@Repository("userDaoImpl")
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @PostConstruct
    public void init() {
        User joran = new User("Joran");
        List<Rekening> rekeningen = joran.getRekeningen();
        Rekening rekening = new Rekening();
        rekening.setNummer("4058486845");
        rekeningen.add(rekening);

        this.create(joran);
    }

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