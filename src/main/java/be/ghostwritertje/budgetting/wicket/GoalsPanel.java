package be.ghostwritertje.budgetting.wicket;

import be.ghostwritertje.budgetting.domain.Goal;
import be.ghostwritertje.budgetting.domain.Rekening;
import be.ghostwritertje.budgetting.services.GoalService;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Created by jorandeboever
 * on 19/03/16.
 */
public class GoalsPanel extends Panel {
    @SpringBean
    private GoalService goalService;

    private Rekening rekening;
    public GoalsPanel(String id, Rekening rekening) {
        super(id);
        this.rekening = rekening;


        add(new ListView<Goal>("goals", goalService.getGoals(rekening)) {
            @Override
            protected void populateItem(ListItem<Goal> goalItem) {
                goalItem.add(new Label("nummer", goalItem.getModelObject().getId()));
                goalItem.add(new Label("naam", goalItem.getModelObject().getNaam()));
                goalItem.add(new Label("bedrag", goalItem.getModelObject().getBedrag()));
                goalItem.add(new Label("volgorde", goalItem.getModelObject().getVolgorde()));
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
        protected void onSubmit() {
           goalService.create(goal);
        }
    }


}
