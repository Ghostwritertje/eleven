package be.ghostwritertje.budgetting.wicket.pages;

import be.ghostwritertje.budgetting.services.CsvService;
import be.ghostwritertje.budgetting.services.GoalService;
import be.ghostwritertje.budgetting.services.RekeningService;
import be.ghostwritertje.budgetting.services.StatementService;
import be.ghostwritertje.budgetting.services.UserService;
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

    public InkomstenPage() {

    }
}
