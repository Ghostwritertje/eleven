package be.ghostwritertje.budgetting.wicket.panels;

import be.ghostwritertje.budgetting.domain.Statement;
import be.ghostwritertje.budgetting.services.StatementService;
import be.ghostwritertje.budgetting.wicket.pages.OverzichtPage;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Created by jorandeboever
 * on 6/05/16.
 */
public class DoorlopendStatementForm extends StatementForm {
    @SpringBean
    private StatementService statementService;

    public DoorlopendStatementForm(String id) {
        super(id);
    }
    @Override
    protected void onSubmit() {
        this.berekenStatement();
        Statement statement = this.getStatement();
        System.out.println("Mededeling statement = " +statement.getMededeling());
        statementService.createMaandelijkseDoorlopendeTransactie(statement.getVertrekRekening().getNummer(), statement.getAankomstRekening().getNummer(), statement.getBedrag(), statement.getDatum(), statement.getMededeling(), 120);

            setResponsePage(OverzichtPage.class);
    }

}
