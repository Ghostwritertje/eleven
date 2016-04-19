package be.ghostwritertje.budgetting.wicket;

import be.ghostwritertje.budgetting.domain.Goal;
import be.ghostwritertje.budgetting.domain.Rekening;
import be.ghostwritertje.budgetting.domain.User;
import be.ghostwritertje.budgetting.services.RekeningService;
import be.ghostwritertje.budgetting.services.SignInService;
import be.ghostwritertje.budgetting.services.UserService;
import be.ghostwritertje.budgetting.wicket.charts.ChartService;
import com.googlecode.wickedcharts.wicket7.highcharts.Chart;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
public class OverzichtPage extends WicketPage {
    @SpringBean
    private UserService userService;

    @SpringBean
    private ChartService chartService;

    @SpringBean
    private RekeningService rekeningService;

    private User user;
    private boolean isAddRekeningVisible;

    private ListView<Rekening> rekeningenLijst;

    public OverzichtPage() {
        user = userService.getUser("Joran");
        add(new Label("userName", user.getUsername()));

     /*   add(new Button("addRekening") {
            @Override
            public void onSubmit() {
                System.out.println("make rekeningForm visible");
                isAddRekeningVisible = true;
            }
        });
        */
        add(new Link("addRekening") {
            @Override
            public void onClick() {
                isAddRekeningVisible = true;
            }
        });

        this.add(new RekeningForm("rekeningForm") {
            @Override
            public boolean isVisible() {
                return isAddRekeningVisible;
            }
        });

         rekeningenLijst = new ListView<Rekening>("rekeningen", rekeningService.getRekeningen("Joran")) {
            @Override
            protected void populateItem(ListItem<Rekening> item) {
                Rekening rekening = item.getModelObject();

                PageParameters parameters = new PageParameters();
                parameters.add("rekeningNummer", rekening.getNummer());
                BookmarkablePageLink pageLink = new BookmarkablePageLink<>("link", RekeningPage.class, parameters);
                item.add(pageLink);

                pageLink.add(new Label("nummer", rekening.getNummer()));
                item.add(new Label("balans", rekeningService.getBalans(rekening)));
            }
        };


        this.add(rekeningenLijst);

        add(new Label("totaal", rekeningService.getBalans(user)));

        add(new Chart("chart", chartService.buildOverviewChartOptions(user.getRekeningen())));
    }

    private class RekeningForm extends Form {

        private Rekening rekening = new Rekening();

        public RekeningForm(String id) {
            super(id);
            rekening.setUser(user);
            setModel(new CompoundPropertyModel<Rekening>(rekening));
            add(new TextField("nummer"));
            add(new Button("add"));
        }

        @Override
        protected void onSubmit() {
            System.out.println("Rekeningnummer = " + rekening.getNummer() + "rekeningNaam = " + rekening.getNaam());
            rekeningService.createRekening("", rekening.getNummer(), rekening.getUser());
            isAddRekeningVisible = false;
            rekeningenLijst.setList(rekeningService.getRekeningen(user.getUsername()));
        }
    }
}
