package be.ghostwritertje.budgetting.dao;

import org.springframework.stereotype.Repository;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
@Repository("userDaoImpl")
public class UserDaoImpl implements UserDao {
/*
    @Autowired
    private SessionFactory sessionFactory;

    @PostConstruct
    public void init() {
        this.create(new User("Joran"));
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

        Query query = sessionFactory.getCurrentSession().createQuery("from User u where u.username = :name");
        query.setParameter("name", username);
        try {
            return (User) query.list().get(0);
        } catch (Exception e) {
            return null;
        }
    }
*/


}