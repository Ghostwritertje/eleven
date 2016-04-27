package be.ghostwritertje.budgetting.wicket.pages;

import be.ghostwritertje.budgetting.domain.Goal;
import be.ghostwritertje.budgetting.domain.Rekening;
import be.ghostwritertje.budgetting.domain.Statement;
import be.ghostwritertje.budgetting.services.GoalService;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * Created by jorandeboever
 * on 27/04/16.
 */
public class GoalOptionForm extends Form {

    @SpringBean
    private GoalService goalService;

    private IModel<Goal> gekozenGoal;
    private Statement statement;

    public GoalOptionForm(String id, Statement statement, Rekening rekening) {
        super(id);
        this.statement = statement;
        this.init(rekening);
    }

    private class GoalChoiceRenderer<Goal> extends ChoiceRenderer<Goal> {

        @Override
        public Object getDisplayValue(Goal goal) {
            return goal.toString();
        }
    }

    private void init(Rekening rekening) {
        List<Goal> goals = goalService.getGoals(rekening);
        //public DropDownChoice(final String id, IModel<T> model, final List<? extends T> choices,
        gekozenGoal = new Model<>(null);
        DropDownChoice<Goal> goalDropDownChoice = new DropDownChoice<Goal>("goal", gekozenGoal, goals){
            @Override
            protected boolean wantOnSelectionChangedNotifications() {
                return true;
            }

            @Override
            protected void onSelectionChanged(Goal newSelection) {
                super.onSelectionChanged(newSelection);
                goalService.setGoal(statement, newSelection);
            }

        };

        if(statement.getGoal() != null){
            goalDropDownChoice.setDefaultModelObject(statement.getGoal());
        }
        // goalDropDownChoice.setChoiceRenderer(new GoalChoiceRenderer<Goal>());

        this.add(goalDropDownChoice);


    }

    @Override
    protected void onSubmit() {

//            goalService.setGoal(statement, gekozenGoal);
    }


}