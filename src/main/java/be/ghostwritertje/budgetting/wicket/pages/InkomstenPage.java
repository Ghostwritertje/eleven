package be.ghostwritertje.budgetting.wicket.pages;

import be.ghostwritertje.budgetting.services.CsvService;
import be.ghostwritertje.budgetting.services.GoalService;
import be.ghostwritertje.budgetting.services.RekeningService;
import be.ghostwritertje.budgetting.services.StatementService;
import be.ghostwritertje.budgetting.services.UserService;
import be.ghostwritertje.budgetting.wicket.WicketSession;
import be.ghostwritertje.budgetting.wicket.charts.ChartService;
import be.ghostwritertje.budgetting.wicket.panels.StatementTablePanel;
import com.googlecode.wickedcharts.wicket7.highcharts.Chart;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Created by jorandeboever
 * on 27/04/16.
 */
public class InkomstenPage extends WicketPage {
    @SpringBean
    private UserService userServiceImpl;

    @SpringBean
    private RekeningService rekeningService;

    @SpringBean
    private StatementService statementService;

    @SpringBean
    private CsvService csvService;

    @SpringBean
    private GoalService goalService;

    @SpringBean
    private ChartService chartService;

    public InkomstenPage() {
        String loggedInuser = WicketSession.get().getLoggedInUser();
        add(new Chart("chart", chartService.buildPieChartByCategorie(statementService.getTotaalPerCategorie(loggedInuser))));
        this.add(new StatementTablePanel("statementTablePanel", statementService.getInkomendeStatementsOverAlleRekeningen(loggedInuser), rekeningService.getRekeningen(loggedInuser)));
    }
}
