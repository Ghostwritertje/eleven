package be.ghostwritertje.budgetting.dao.impl;

import be.ghostwritertje.budgetting.dao.HibernateUtil;
import be.ghostwritertje.budgetting.dao.api.GoalDao;
import be.ghostwritertje.budgetting.domain.Goal;
import be.ghostwritertje.budgetting.domain.Rekening;
import be.ghostwritertje.budgetting.domain.Statement;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jorandeboever
 * on 19/03/16.
 */
@Repository
public class GoalDaoImpl implements GoalDao {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public List<Goal> getGoals(Rekening rekening) {
        Transaction transaction = sessionFactory.getCurrentSession().beginTransaction();

        Query query = sessionFactory.getCurrentSession().createQuery("from Goal g where g.rekening.nummer = :rekening");
        query.setParameter("rekening", rekening.getNummer());
        List<Goal> goals = query.list();

        transaction.commit();
        return goals;
    }

    @Override
    public void create(final Goal goal) {
        Transaction transaction = sessionFactory.getCurrentSession().beginTransaction();
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(goal);
            transaction.commit();
        } catch (ConstraintViolationException e) {
            transaction.rollback();
        }
    }

    @Override
    public void deleteAllGoals() {
        Transaction transaction = sessionFactory.getCurrentSession().beginTransaction();

        Query query = sessionFactory.getCurrentSession().createQuery("delete from Goal g");
        query.executeUpdate();

        transaction.commit();
    }

    @Override
    public void setGoal(Statement statement, Goal goal) {
        Transaction transaction = sessionFactory.getCurrentSession().beginTransaction();
        try {
            statement.setGoal(goal);
            sessionFactory.getCurrentSession().saveOrUpdate(statement);
            transaction.commit();
        } catch (ConstraintViolationException e) {
            transaction.rollback();
        }


    }
}
