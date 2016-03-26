package be.ghostwritertje.budgetting.services;

import be.ghostwritertje.budgetting.dao.api.GoalDao;
import be.ghostwritertje.budgetting.domain.Goal;
import be.ghostwritertje.budgetting.domain.Rekening;
import be.ghostwritertje.budgetting.domain.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jorandeboever
 * on 19/03/16.
 */
@Service
public class GoalService {
    @Autowired
    private GoalDao goalDaoImpl;

    public List<Goal> getGoals(Rekening rekening) {
        return goalDaoImpl.getGoals(rekening);
    }

    public void create(final Goal goal) {
        goalDaoImpl.create(goal);
    }

    public void setGoal(Statement statement, Goal goal) {
        goalDaoImpl.setGoal(statement, goal);
    }
}
