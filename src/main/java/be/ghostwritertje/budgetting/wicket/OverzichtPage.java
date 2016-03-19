package be.ghostwritertje.budgetting.wicket;

import be.ghostwritertje.budgetting.dao.api.RekeningDao;
import be.ghostwritertje.budgetting.dao.api.StatementDao;
import be.ghostwritertje.budgetting.domain.Rekening;
import be.ghostwritertje.budgetting.domain.User;
import be.ghostwritertje.budgetting.services.UserService;
import com.googlecode.wickedcharts.highcharts.options.*;
import com.googlecode.wickedcharts.highcharts.options.series.SimpleSeries;
import com.googlecode.wickedcharts.wicket7.highcharts.Chart;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Arrays;

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

        Options options = new Options();

        options
                .setChartOptions(new ChartOptions()
                        .setType(SeriesType.LINE));

        options
                .setTitle(new Title("My very own chart."));

        options
                .setxAxis(new Axis()
                        .setCategories(Arrays
                                .asList(new String[] { "Jan", "Feb", "Mar", "Apr", "May",
                                        "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" })));

        options
                .setyAxis(new Axis()
                        .setTitle(new Title("Temperature (C)")));

        options
                .setLegend(new Legend()
                        .setLayout(LegendLayout.VERTICAL)
                        .setAlign(HorizontalAlignment.RIGHT)
                        .setVerticalAlign(VerticalAlignment.TOP)
                        .setX(-10)
                        .setY(100)
                        .setBorderWidth(0));

        options
                .addSeries(new SimpleSeries()
                        .setName("Tokyo")
                        .setData(
                                Arrays
                                        .asList(new Number[] { 7.0, 6.9, 9.5, 14.5, 18.2, 21.5,
                                                25.2, 26.5, 23.3, 18.3, 13.9, 9.6 })));

        options
                .addSeries(new SimpleSeries()
                        .setName("New York")
                        .setData(
                                Arrays
                                        .asList(new Number[] { -0.2, 0.8, 5.7, 11.3, 17.0, 22.0,
                                                24.8, 24.1, 20.1, 14.1, 8.6, 2.5 })));
        options.setTitle(new Title("My Chart"));
        // add any chart configuration you like, see above
        add(new Chart("chart", options));
    }
}
