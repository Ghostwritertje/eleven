package be.ghostwritertje.budgetting.wicket;

import be.ghostwritertje.budgetting.dao.api.RekeningDao;
import be.ghostwritertje.budgetting.dao.api.StatementDao;
import be.ghostwritertje.budgetting.domain.Rekening;
import be.ghostwritertje.budgetting.domain.User;
import be.ghostwritertje.budgetting.services.UserService;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
public class OverzichtPage extends WicketPage {
    @SpringBean
    private UserService userServiceImpl;

    @SpringBean
    private RekeningDao rekeningDao;
    @SpringBean
    private StatementDao statementDao;

    public OverzichtPage() {
        User user = userServiceImpl.getUser("Joran");
        add(new Label("userName", user.getUsername()));

        add(new ListView<Rekening>("rekeningen", rekeningDao.getRekeningen("Joran")) {
            @Override
            protected void populateItem(ListItem<Rekening> item) {
                Rekening rekening =  item.getModelObject();

                PageParameters parameters = new PageParameters();
                parameters.add("rekeningNummer", rekening.getNummer());
                BookmarkablePageLink pageLink = new BookmarkablePageLink<>("link", RekeningPage.class , parameters);
                pageLink.add(new Label("naam", rekening.getNaam()));
                item.add(pageLink);

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
