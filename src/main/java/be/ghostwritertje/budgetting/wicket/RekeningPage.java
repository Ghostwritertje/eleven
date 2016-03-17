package be.ghostwritertje.budgetting.wicket;

import be.ghostwritertje.budgetting.dao.api.RekeningDao;
import be.ghostwritertje.budgetting.dao.api.StatementDao;
import be.ghostwritertje.budgetting.domain.Rekening;
import be.ghostwritertje.budgetting.domain.Statement;
import be.ghostwritertje.budgetting.services.UserService;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
public class RekeningPage extends WicketBudgettingPage {
    @SpringBean
    private UserService userServiceImpl;

    @SpringBean
    private RekeningDao rekeningDao;
    @SpringBean
    private StatementDao statementDao;

    public RekeningPage() {
        //TODO_JORAN:  Rekening rekening =
        Rekening rekening = rekeningDao.getRekeningen("Joran").get(0);

        add(new Label("rekeningNaam", rekening.getNaam()));
        add(new ListView<Statement>("statements", statementDao.getStatements(rekening)) {
            @Override
            protected void populateItem(ListItem<Statement> statementListItem) {
                statementListItem.add(new Label("nummer", statementListItem.getModelObject().getId()));
                statementListItem.add(new Label("vertrekRekening", statementListItem.getModelObject().getVertrekRekening().getNummer()));

                statementListItem.add(new Label("aankomstRekening", statementListItem.getModelObject().getAankomstRekening().getNummer()));
                statementListItem.add(new Label("bedrag", statementListItem.getModelObject().getBedrag()));
            }
        });

        add(new Label("totaal", rekeningDao.getBalans(rekening)));


    }
}
