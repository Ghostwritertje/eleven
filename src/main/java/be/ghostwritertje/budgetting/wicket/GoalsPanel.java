package be.ghostwritertje.budgetting.wicket;

import be.ghostwritertje.budgetting.dao.api.GoalDao;
import be.ghostwritertje.budgetting.domain.Goal;
import be.ghostwritertje.budgetting.domain.Rekening;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Created by jorandeboever
 * on 19/03/16.
 */
public class GoalsPanel extends Panel {
    @SpringBean
    private GoalDao goalDaoImpl;

    private Rekening rekening;
    public GoalsPanel(String id, Rekening rekening) {
        super(id);
        this.rekening = rekening;


        add(new ListView<Goal>("goals", goalDaoImpl.getGoals(rekening)) {
            @Override
            protected void populateItem(ListItem<Goal> goalItem) {
                goalItem.add(new Label("nummer", goalItem.getModelObject().getId()));
                goalItem.add(new Label("naam", goalItem.getModelObject().getNaam()));
                goalItem.add(new Label("bedrag", goalItem.getModelObject().getBedrag()));
                goalItem.add(new Label("volgorde", goalItem.getModelObject().getVolgorde()));
            }
        });
    }


}
