package be.ghostwritertje.budgetting.wicket.pages;

import be.ghostwritertje.budgetting.wicket.charts.ChartService;
import com.googlecode.wickedcharts.wicket7.highcharts.Chart;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Created by jorandeboever
 * on 27/04/16.
 */
public class PensioenPage extends WicketPage {

    private int leeftijd = 23;
    private double pensioenFondsRendement = 0.06;
    private double gewoonFondsRendement = 0.09;
    private double indexToegepastOpInleg = 0.02;

    @SpringBean
    private ChartService chartService;

    public PensioenPage() {
        this.add(new Chart("pensioenChart", chartService.buildPensioenChartOptions(pensioenFondsRendement, leeftijd, indexToegepastOpInleg)));

        this.add(new Chart("beleggingsChart", chartService.buildAlternatiefPensioenChartOptions(gewoonFondsRendement, leeftijd, indexToegepastOpInleg)));

        Form form = new Form("pensioenForm");

        form.add(new NumberTextField("leeftijd"));
        form.add(new NumberTextField("pensioenFondsRendement"));
        form.add(new NumberTextField("gewoonFondsRendement"));

        this.add(form);
    }

}
