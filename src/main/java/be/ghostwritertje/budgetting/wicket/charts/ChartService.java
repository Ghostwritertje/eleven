package be.ghostwritertje.budgetting.wicket.charts;

import be.ghostwritertje.budgetting.domain.Rekening;
import be.ghostwritertje.budgetting.services.RekeningService;
import be.ghostwritertje.budgetting.services.StatementService;
import com.googlecode.wickedcharts.highcharts.options.Axis;
import com.googlecode.wickedcharts.highcharts.options.ChartOptions;
import com.googlecode.wickedcharts.highcharts.options.CssStyle;
import com.googlecode.wickedcharts.highcharts.options.DataLabels;
import com.googlecode.wickedcharts.highcharts.options.Function;
import com.googlecode.wickedcharts.highcharts.options.HorizontalAlignment;
import com.googlecode.wickedcharts.highcharts.options.Labels;
import com.googlecode.wickedcharts.highcharts.options.Legend;
import com.googlecode.wickedcharts.highcharts.options.LegendLayout;
import com.googlecode.wickedcharts.highcharts.options.Options;
import com.googlecode.wickedcharts.highcharts.options.PlotOptions;
import com.googlecode.wickedcharts.highcharts.options.PlotOptionsChoice;
import com.googlecode.wickedcharts.highcharts.options.SeriesType;
import com.googlecode.wickedcharts.highcharts.options.StackLabels;
import com.googlecode.wickedcharts.highcharts.options.Stacking;
import com.googlecode.wickedcharts.highcharts.options.Title;
import com.googlecode.wickedcharts.highcharts.options.Tooltip;
import com.googlecode.wickedcharts.highcharts.options.VerticalAlignment;
import com.googlecode.wickedcharts.highcharts.options.color.HexColor;
import com.googlecode.wickedcharts.highcharts.options.functions.StackTotalFormatter;
import com.googlecode.wickedcharts.highcharts.options.series.SimpleSeries;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by jorandeboever
 * on 19/03/16.
 */
@Service
public class ChartService {
    @Autowired
    private RekeningService rekeningService;

    @Autowired
    private StatementService statementService;

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


    public Options buildHistoryChartOptions(List<Rekening> rekeningen) {
        List<Map<String, Double>> lijst = new ArrayList<>();
        for (Rekening rekening : rekeningen) {
            lijst.add(statementService.getTotalenPerMaand(rekening));
        }

        Options options = new Options();
        options.setChartOptions(new ChartOptions()
                .setType(SeriesType.COLUMN));

        options.setTitle(new Title("Historiek"));


        LocalDate startDate = new LocalDate(2013, 10, 1);
        LocalDate now = new LocalDate();
        int aantalMaanden = Months.monthsBetween(startDate, now).getMonths();
        List<String> categories = new ArrayList<>();
        for (int i = 0; i <= aantalMaanden; i++) {
            categories.add(String.format("%d/%d", startDate.getYear(), startDate.getMonthOfYear()));
            startDate = startDate.plusMonths(1);
        }

        options.setxAxis(new Axis()
                .setCategories(categories));

        options.setyAxis(new Axis()
                .setMin(0)
                .setTitle(new Title("Money"))
                .setStackLabels(new StackLabels()
                        .setEnabled(Boolean.FALSE)));


        options.setLegend(new Legend()
                .setAlign(HorizontalAlignment.RIGHT)
                .setX(-100)
                .setVerticalAlign(VerticalAlignment.TOP)
                .setY(20)
                .setFloating(Boolean.TRUE)
                .setBackgroundColor(new HexColor("#FFFFFF"))
                .setBorderColor(new HexColor("#CCCCCC"))
                .setBorderWidth(1)
                .setShadow(Boolean.FALSE));


        options.setTooltip(new Tooltip()
                .setFormatter(new StackTotalFormatter()));

        options.setPlotOptions(new PlotOptionsChoice()
                .setColumn(new PlotOptions()
                        .setStacking(Stacking.NORMAL)
                        .setDataLabels(new DataLabels()
                                .setEnabled(Boolean.FALSE)
                                .setColor(new HexColor("#FFFFFF")))));

        for (Rekening rekening : rekeningen) {
            Map<String, Double> doubles = statementService.getTotalenPerMaand(rekening);
            List<Number> waarden = new ArrayList<>();
            for (String categorie : categories) {
                System.out.println(categorie);
                waarden.add(doubles.get(categorie));
            }
            options.addSeries(new SimpleSeries()
                    .setName(rekening.getNummer())
                    .setData(waarden));
        }


        return options;

    }
}
