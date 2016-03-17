package be.ghostwritertje.budgetting.wicket;

import be.ghostwritertje.budgetting.dao.api.RekeningDao;
import be.ghostwritertje.budgetting.dao.api.StatementDao;
import be.ghostwritertje.budgetting.services.UserService;
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
        /*add(new ListView<Statement>("statements", statementDao.getStatements(rekening)) {
            @Override
            protected void populateItem(ListItem<Statement> statementListItem) {
                statementListItem.add(new Label("bedrag", statementListItem.getModelObject().getBedrag()));
            }
        });*/


    }
}
