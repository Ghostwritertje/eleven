package be.ghostwritertje.budgetting.dao.impl;

import be.ghostwritertje.budgetting.dao.HibernateUtil;
import be.ghostwritertje.budgetting.dao.api.GoalDao;
import be.ghostwritertje.budgetting.domain.Goal;
import be.ghostwritertje.budgetting.domain.Rekening;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
}
