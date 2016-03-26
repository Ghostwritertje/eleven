package be.ghostwritertje.budgetting.dao.api;

import be.ghostwritertje.budgetting.domain.Goal;
import be.ghostwritertje.budgetting.domain.Rekening;
import be.ghostwritertje.budgetting.domain.Statement;

import java.util.List;

/**
 * Created by jorandeboever
 * on 19/03/16.
 */
public interface GoalDao {
    List<Goal> getGoals(Rekening rekening);
    void create(final Goal goal);
    void deleteAllGoals();

    void setGoal(Statement statement, Goal goal);
}
