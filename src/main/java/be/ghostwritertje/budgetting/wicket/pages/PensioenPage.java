package be.ghostwritertje.budgetting.wicket.pages;

import be.ghostwritertje.budgetting.wicket.charts.ChartService;
import com.googlecode.wickedcharts.wicket7.highcharts.Chart;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Created by jorandeboever
 * on 27/04/16.
 */
public class PensioenPage extends WicketPage {

    private int leeftijd = 23;
    private double pensioenFondsRendement = 0.05;
    private double gewoonFondsRendement = 0.07;
    private double indexToegepastOpInlegPensioen = 2.0;

    @SpringBean
    private ChartService chartService;

    public PensioenPage() {
        this.add(new Chart("pensioenChart", chartService.buildPensioenChartOptions(pensioenFondsRendement, leeftijd, indexToegepastOpInlegPensioen)));
    }

}
