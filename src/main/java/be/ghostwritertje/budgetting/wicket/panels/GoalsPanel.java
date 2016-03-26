package be.ghostwritertje.budgetting.wicket.panels;

import be.ghostwritertje.budgetting.domain.Goal;
import be.ghostwritertje.budgetting.domain.Rekening;
import be.ghostwritertje.budgetting.services.GoalService;
import de.agilecoders.wicket.core.markup.html.bootstrap.components.progress.ProgressBar;
import de.agilecoders.wicket.core.markup.html.bootstrap.components.progress.Stack;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Created by jorandeboever
 * on 19/03/16.
 */
public class GoalsPanel extends Panel {
    @SpringBean
    private GoalService goalService;

    private boolean isAddGoalVisible;

    private Rekening rekening;

    public GoalsPanel(String id, Rekening rekening) {
        super(id);
        this.rekening = rekening;

        add(new Link("addGoal") {
            @Override
            public void onClick() {
                isAddGoalVisible = true;
            }
        });


        add(new ListView<Goal>("goals", goalService.getGoals(rekening)) {
            @Override
            protected void populateItem(ListItem<Goal> goalItem) {
                goalItem.add(new Label("naam", goalItem.getModelObject().getNaam()));
                goalItem.add(new Label("bedrag", goalItem.getModelObject().getBedrag()));
                ProgressBar progressBar = new ProgressBar("progress");
                goalItem.add(progressBar);
                Stack labeledStack = new Stack(progressBar.getStackId(), Model.of(100)) {
                    @Override
                    protected IModel<String> createLabelModel() {
                        return new AbstractReadOnlyModel<String>() {
                            @Override
                            public String getObject() {
                                return String.format("%s%%", getModelObject());
                            }
                        };
                    }
                };
                labeledStack.labeled(true).type(ProgressBar.Type.SUCCESS);
                progressBar.addStacks(labeledStack);
            }
        });

        add(new GoalForm("goalForm"));


    }

    private class GoalForm extends Form {

        private Goal goal = new Goal();


        public GoalForm(String id) {
            super(id);
            goal.setRekening(rekening);
            setModel(new CompoundPropertyModel<Goal>(goal));
            add(new TextField("naam"));
            add(new TextField("bedrag"));
        }

        @Override
        public boolean isVisible() {
            return isAddGoalVisible;
        }

        @Override
        protected void onSubmit() {
            goalService.create(goal);
        }
    }


}
