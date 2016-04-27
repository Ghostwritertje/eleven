package be.ghostwritertje.budgetting.wicket.pages;

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
        this.statement.setVertrekRekening(rekening);
        this.statement.setAankomstRekening(new Rekening());
        this.setModel(new CompoundPropertyModel<>(statement));
        this.add(new TextField("aankomstRekening.nummer"));
        this.add(new TextField("mededeling"));
        this.add(new DateTextField("datum"));
        this.add(new NumberTextField("bedrag"));
    }

    @Override
    protected void onSubmit() {
        if(statement.getBedrag() < 0){
            Rekening rekeningAankomstTemp = statement.getAankomstRekening();
            Rekening rekeningVertrekTemp = statement.getVertrekRekening();

            statement.setVertrekRekening(rekeningAankomstTemp);
            statement.setAankomstRekening(rekeningVertrekTemp);
            statement.setBedrag(Math.abs(statement.getBedrag()));
        }
        rekeningService.createStatement(statement);
    }
}