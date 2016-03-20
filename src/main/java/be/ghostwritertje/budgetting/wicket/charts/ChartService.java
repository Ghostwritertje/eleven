package be.ghostwritertje.budgetting.wicket.charts;

import be.ghostwritertje.budgetting.domain.Rekening;
import be.ghostwritertje.budgetting.services.RekeningService;
import com.googlecode.wickedcharts.highcharts.options.*;
import com.googlecode.wickedcharts.highcharts.options.series.SimpleSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jorandeboever
 * on 19/03/16.
 */
@Service
public class ChartService {
    @Autowired
    private RekeningService rekeningService;



    public Options buildOverviewChartOptions(List<Rekening> rekeningen) {
        Options options = new Options();
        options.setChartOptions(new ChartOptions()
                .setType(SeriesType.COLUMN));

        options
                .setTitle(new Title("Overview"));
        options.setxAxis(new Axis().setCategories(""));

        options
                .setLegend(new Legend()
                        .setLayout(LegendLayout.VERTICAL)
                        .setAlign(HorizontalAlignment.RIGHT)
                        .setVerticalAlign(VerticalAlignment.TOP)
                        .setX(0)
                        .setY(100)
                        .setBorderWidth(0));

        for (Rekening rekening : rekeningen) {
            options
                    .addSeries(new SimpleSeries()
                            .setName(rekening.getNaam())
                            .setData(
                                    Arrays
                                            .asList(new Number[]{rekeningService.getTotaal(rekening)})));


        }
        return options;

    }
}
