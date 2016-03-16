package be.ghostwritertje.budgetting.wicket;

import be.ghostwritertje.budgetting.dao.RekeningDao;
import be.ghostwritertje.budgetting.domain.Rekening;
import be.ghostwritertje.budgetting.services.UserService;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
public class HomePage extends WebPage {
    @SpringBean
    private UserService userServiceImpl;

    @SpringBean
    private RekeningDao rekeningDao;

    public HomePage() {
        add(new Label("userName", userServiceImpl.getUsername()));

        add(new ListView<Rekening>("rekeningen", rekeningDao.getRekeningen("Joran")) {
            @Override
            protected void populateItem(ListItem<Rekening> item) {
                System.out.println("Rekening: ");
                Rekening rekening = (Rekening) item.getModelObject();
                System.out.println("Rekening: " + rekening.getNummer());

                item.add(new Label("nummer", rekening.getNummer()));


            }
        });
    }
}
