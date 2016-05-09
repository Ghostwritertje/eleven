package be.ghostwritertje.budgetting.wicket.panels;

import be.ghostwritertje.budgetting.domain.Rekening;
import be.ghostwritertje.budgetting.domain.Statement;
import be.ghostwritertje.budgetting.services.RekeningService;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Created by jorandeboever
 * on 27/04/16.
 */
public class StatementForm extends Form<Statement> {

    @SpringBean
    private RekeningService rekeningService;

    private Statement statement = new Statement();

    public StatementForm(String id, Rekening rekening) {
        super(id);
        init();
        this.statement.setVertrekRekening(rekening);
    }

    public void init(){
        this.statement.setVertrekRekening(new Rekening());
        this.statement.setAankomstRekening(new Rekening());
        this.setModel(new CompoundPropertyModel<>(statement));
        this.add(new TextField("aankomstRekening.nummer"));
        this.add(new TextField("mededeling"));
        this.add(new DateTextField("datum", "dd/MM/yyyy"));
        this.add(new NumberTextField("bedrag"));
    }

    public StatementForm(String id) {
        super(id);
        init();
        this.add(new TextField("vertrekRekening.nummer"));

    }

    @Override
    protected void onSubmit() {
        berekenStatement();
        rekeningService.createStatement(statement);
    }

    protected void berekenStatement() {
        if(statement.getBedrag() < 0){
            Rekening rekeningAankomstTemp = statement.getAankomstRekening();
            Rekening rekeningVertrekTemp = statement.getVertrekRekening();

            statement.setVertrekRekening(rekeningAankomstTemp);
            statement.setAankomstRekening(rekeningVertrekTemp);
            statement.setBedrag(Math.abs(statement.getBedrag()));
        }
    }

    protected Statement getStatement() {
        return statement;
    }
}