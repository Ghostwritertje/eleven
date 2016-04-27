package be.ghostwritertje.budgetting.wicket.charts;

import be.ghostwritertje.budgetting.domain.Rekening;
import be.ghostwritertje.budgetting.services.RekeningService;
import be.ghostwritertje.budgetting.services.StatementService;
import com.googlecode.wickedcharts.highcharts.options.Axis;
import com.googlecode.wickedcharts.highcharts.options.ChartOptions;
import com.googlecode.wickedcharts.highcharts.options.Cursor;
import com.googlecode.wickedcharts.highcharts.options.DataLabels;
import com.googlecode.wickedcharts.highcharts.options.HorizontalAlignment;
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
import com.googlecode.wickedcharts.highcharts.options.functions.PercentageFormatter;
import com.googlecode.wickedcharts.highcharts.options.functions.StackTotalFormatter;
import com.googlecode.wickedcharts.highcharts.options.series.Point;
import com.googlecode.wickedcharts.highcharts.options.series.PointSeries;
import com.googlecode.wickedcharts.highcharts.options.series.Series;
import com.googlecode.wickedcharts.highcharts.options.series.SimpleSeries;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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

        Options options = new Options();
        options.setChartOptions(new ChartOptions()
                .setType(SeriesType.COLUMN));

        options.setTitle(new Title("Historiek"));


        LocalDate startDate = new LocalDate(2011, 10, 1);
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


        options.setTooltip(new Tooltip()
                .setFormatter(new StackTotalFormatter()));

        options.setPlotOptions(new PlotOptionsChoice()
                .setColumn(new PlotOptions()
                        .setStacking(Stacking.NORMAL)
                        .setDataLabels(new DataLabels()
                                .setEnabled(Boolean.FALSE)
                                .setColor(new HexColor("#FFFFFF")))));

        for (Rekening rekening : rekeningen) {
            Double tijdelijkeWaarde = 0.00;
            Map<String, Double> doubles = statementService.getTotalenPerMaand(rekening);
            List<Number> waarden = new ArrayList<>();
            for (String categorie : categories) {
                Double categorieWaarde = doubles.get(categorie);
                if (categorieWaarde != null) {
                    waarden.add(Math.round(categorieWaarde * 100) / 100);
                    tijdelijkeWaarde = categorieWaarde;
                } else {
                    waarden.add(Math.round(tijdelijkeWaarde * 100) / 100);
                }
            }
            options.addSeries(new SimpleSeries()
                    .setName(rekening.getNaam() != null ? rekening.getNaam() : rekening.getNummer())
                    .setData(waarden));

        }


        return options;

    }

    public Options buildPensioenChartOptions(double geschatteIntrest, int leeftijd, double geschatteIndex) {

        Options options = new Options();
        options.setChartOptions(new ChartOptions()
                .setType(SeriesType.COLUMN));

        options.setTitle(new Title("Historiek"));

        options.setyAxis(new Axis()
                .setMin(-5000)
                .setTitle(new Title("Money"))
                .setStackLabels(new StackLabels()
                        .setEnabled(Boolean.FALSE)));

        options.setTooltip(new Tooltip()
                .setShared(true));


        options.setPlotOptions(new PlotOptionsChoice()
                .setColumn(new PlotOptions()
                        .setStacking(Stacking.NORMAL)
                        .setDataLabels(new DataLabels()
                                .setEnabled(Boolean.FALSE)
                                .setColor(new HexColor("#FFFFFF")))));

        double spaarBedrag = 940;
        double totaalGespaard = 0;
        double totaalIntrest = 0;
        double totaalTaks = 0;
        double totaalMinTaks = 0;
        List<Number> spaarBedragen = new ArrayList<>();
        List<Number> interestBedragen = new ArrayList<>();
        List<Number> totaalMinTaksBedragen = new ArrayList<>();
        List<Number> totaalTaksBedragen = new ArrayList<>();

        List<String> categories = new ArrayList<>();

        double aantalJarenStopzettingIndex = 4;

        for (int i = leeftijd; i < 65; i++) {
            if (aantalJarenStopzettingIndex-- <= 0) {
                spaarBedrag *= (1 + geschatteIndex);
            }
            totaalGespaard += spaarBedrag;
            totaalIntrest += (totaalGespaard + totaalIntrest - totaalTaks) * geschatteIntrest;
            if(i >= 60) {
                if(i == 60) {
                    totaalTaks = (totaalGespaard + totaalIntrest) * 0.08;
                }
                totaalTaksBedragen.add(-Math.round(totaalTaks*100)/100);
            }else  {
                totaalTaksBedragen.add(0);
            }

            totaalMinTaks = (totaalGespaard + totaalIntrest) - totaalTaks;


            categories.add(String.format("%d",  i));
            spaarBedragen.add(Math.round(totaalGespaard * 100) / 100);
            interestBedragen.add(Math.round(totaalIntrest * 100) / 100);
            totaalMinTaksBedragen.add(Math.round(totaalMinTaks * 100) / 100);


        }

        options.addSeries(new SimpleSeries()
                .setName("Interest")
                .setData(interestBedragen));

        options.addSeries(new SimpleSeries()
                .setName("Gespaard")
                .setData(spaarBedragen));

        options.addSeries(new SimpleSeries()
                .setName("Belastingen")
                .setData(totaalTaksBedragen));

        options.setxAxis(new Axis()
                .setCategories(categories));

        Series<Number> series4 = new SimpleSeries();
        series4
                .setType(SeriesType.LINE);
        series4
                .setName("Totaal na belastingen");
        series4
                .setData(totaalMinTaksBedragen);

       // options.addSeries(series4);

        return options;

    }


    public Options buildPieChartByCategorie(Map<String, Double> categorieWaardenMap) {

        Options options = new Options();
        options.setChartOptions(new ChartOptions()
                .setType(SeriesType.PIE));

        options.setTitle(new Title("Categorieën"));


        options.setPlotOptions(new PlotOptionsChoice()
                .setPie(new PlotOptions()
                        .setAllowPointSelect(Boolean.TRUE)
                        .setCursor(Cursor.POINTER)
                        .setDataLabels(new DataLabels()
                                .setEnabled(Boolean.TRUE)
                                .setColor(new HexColor("#000000"))
                                .setConnectorColor(new HexColor("#000000"))
                                .setFormatter(new PercentageFormatter()))));

        PointSeries pointSeries = new PointSeries();

        for (Map.Entry categorieWaarde : categorieWaardenMap.entrySet()) {
            pointSeries.addPoint(new Point((String) categorieWaarde.getKey(), (Double) categorieWaarde.getValue()));
        }
        options.addSeries(pointSeries);


        return options;

    }
}
