package be.ghostwritertje.budgetting.wicket;

import be.ghostwritertje.budgetting.dao.api.RekeningDao;
import be.ghostwritertje.budgetting.dao.api.StatementDao;
import be.ghostwritertje.budgetting.domain.Rekening;
import be.ghostwritertje.budgetting.services.UserService;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
public class OverzichtPage extends WicketBudgettingPage {
    @SpringBean
    private UserService userServiceImpl;

    @SpringBean
    private RekeningDao rekeningDao;
    @SpringBean
    private StatementDao statementDao;

    public OverzichtPage() {
        add(new Label("userName", userServiceImpl.getUsername()));

        add(new ListView<Rekening>("rekeningen", rekeningDao.getRekeningen("Joran")) {
            @Override
            protected void populateItem(ListItem<Rekening> item) {
                System.out.println("Rekening: ");
                Rekening rekening = (Rekening) item.getModelObject();
                System.out.println("Rekening: " + rekening.getNummer());
                item.add(new Label("naam", rekening.getNaam()));
                item.add(new Label("nummer", rekening.getNummer()));
                item.add(new Label("balans", rekeningDao.getBalans(rekening)));
               /* item.add(new ListView<Statement>("statements", statementDao.getStatements(rekening)) {
                    @Override
                    protected void populateItem(ListItem<Statement> statementListItem) {
                        statementListItem.add(new Label("bedrag", statementListItem.getModelObject().getBedrag()));
                    }
                });*/
            }
        });
    }
}
