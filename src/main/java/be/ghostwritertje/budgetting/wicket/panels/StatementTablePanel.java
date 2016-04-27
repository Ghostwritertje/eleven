package be.ghostwritertje.budgetting.wicket.panels;

import be.ghostwritertje.budgetting.domain.Rekening;
import be.ghostwritertje.budgetting.domain.Statement;
import be.ghostwritertje.budgetting.services.RekeningService;
import be.ghostwritertje.budgetting.wicket.pages.GoalOptionForm;
import be.ghostwritertje.budgetting.wicket.pages.RekeningPage;
import be.ghostwritertje.budgetting.wicket.pages.StatementForm;
import de.agilecoders.wicket.core.markup.html.bootstrap.navigation.BootstrapPagingNavigator;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * Created by jorandeboever
 * on 27/04/16.
 */
public class StatementTablePanel extends Panel {

    @SpringBean
    RekeningService rekeningService;

    public StatementTablePanel(String id, List<Statement> statementList, List<Rekening> rekeningList) {
        super(id);
        this.init(statementList, rekeningList);
    }

    private void init(List<Statement> statementList, final List<Rekening> rekeningList) {
        PageableListView<Statement> listView = new PageableListView<Statement>("statements", statementList, 100) {

            @Override
            protected void populateItem(ListItem<Statement> statementListItem) {
                Statement statement = statementListItem.getModelObject();
                statementListItem.add(new Label("datum", statement.getDatumString()));
                statementListItem.add(new Label("categorie", statement.getCategorie()));
                statementListItem.add(new Label("mededeling", statement.getKorteMededeling()));
                statementListItem.add(new Label("andereRekening", ""));
//                statementListItem.add(new GoalOptionForm("goalOptionForm", statement, statement.getAankomstRekening()));
                statementListItem.addOrReplace(new Label("bedrag", ""));

                if (statement.getAankomstRekening() != null) {
                    if (rekeningList.contains(statement.getAankomstRekening())) {
                        statementListItem.addOrReplace(new Label("bedrag", statement.getBedrag()));

                    } else {
                        statementListItem.addOrReplace(new Label("andereRekening", statement.getAankomstRekening().getNummer()));
                    }

                }
                if (statement.getVertrekRekening() != null) {
                    if (rekeningList.contains(statement.getAankomstRekening())) {
                        statementListItem.addOrReplace(new Label("bedrag", -statement.getBedrag()));
                    } else {
                        statementListItem.addOrReplace(new Label("andereRekening", statement.getVertrekRekening().getNummer()));
                    }


                }
            }
        };
        this.add(listView);

        add(new BootstrapPagingNavigator("navigator", listView));
        add(new Label("totaal", rekeningService.getBalans(rekeningList.get(0))));


        this.add(new StatementForm("statementForm", rekeningList.get(0)));
    }


}
