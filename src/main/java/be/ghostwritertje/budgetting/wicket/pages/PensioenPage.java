package be.ghostwritertje.budgetting.wicket.pages;

import be.ghostwritertje.budgetting.services.SpeculatieService;
import be.ghostwritertje.budgetting.wicket.charts.ChartService;
import be.ghostwritertje.budgetting.wrappers.PensioenInfo;
import com.googlecode.wickedcharts.wicket7.highcharts.Chart;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Created by jorandeboever
 * on 27/04/16.
 */
public class PensioenPage extends WicketPage {

    private PensioenInfo pensioenInfo = new PensioenInfo();

    @SpringBean
    private ChartService chartService;

    @SpringBean
    private SpeculatieService speculatieService;

    public PensioenPage() {

        this.add(new Chart("pensioenChart", chartService.buildPensioenChartOptions(pensioenInfo.getEchtRendement() , pensioenInfo.getLeeftijd(), 0)));

        PensioenForm form = new PensioenForm("pensioenForm", pensioenInfo, this) {
            @Override
            protected void onSubmit() {
                super.onSubmit();
                this.getParentPage().renderPage();
            }
        };

        this.add(new Label("jaarlijkseIntrest", speculatieService.berekenGeannualiseerdRendement(pensioenInfo.getEchtRendement(), pensioenInfo.getLeeftijd())));
        this.add(form);
    }

    @Override
    public void renderPage() {
        this.addOrReplace(new Chart("pensioenChart", chartService.buildPensioenChartOptions(pensioenInfo.getEchtRendement() , pensioenInfo.getLeeftijd(), pensioenInfo.getVerwachteIndex() )));
        this.addOrReplace(new Label("jaarlijkseIntrest", speculatieService.berekenGeannualiseerdRendement(pensioenInfo.getEchtRendement(), pensioenInfo.getLeeftijd())));
        super.renderPage();
    }

    private class PensioenForm extends Form<PensioenInfo> {

        private PensioenInfo pensioenInfo;
        private Page parentPage;

        public PensioenForm(String id, PensioenInfo pensioenInfo, Page parentPage) {
            super(id);

            this.pensioenInfo = pensioenInfo;
            this.setModel(new CompoundPropertyModel<>(this.pensioenInfo));
            this.add(new NumberTextField("leeftijd"));
            this.add(new NumberTextField("rendement"));
            this.add(new NumberTextField("verwachteIndex"));

            this.add(new Button("submit"));

            this.parentPage = parentPage;
        }


        public Page getParentPage() {
            return parentPage;
        }
    }


}
