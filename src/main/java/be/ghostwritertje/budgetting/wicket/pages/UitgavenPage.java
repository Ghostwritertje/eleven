package be.ghostwritertje.budgetting.wicket.pages;

import be.ghostwritertje.budgetting.wicket.charts.ChartService;
import com.googlecode.wickedcharts.wicket7.highcharts.Chart;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Created by jorandeboever
 * on 6/05/16.
 */
public class UitgavenPage extends WicketPage {
    @SpringBean
    private ChartService chartService;

    public UitgavenPage() {
        add(new Chart("chart", chartService.buildUitgavenChart("Joran")));
    }
}
