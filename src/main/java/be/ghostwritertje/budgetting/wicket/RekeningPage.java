package be.ghostwritertje.budgetting.wicket;

import be.ghostwritertje.budgetting.domain.Goal;
import be.ghostwritertje.budgetting.services.CsvService;
import be.ghostwritertje.budgetting.domain.Rekening;
import be.ghostwritertje.budgetting.domain.Statement;
import be.ghostwritertje.budgetting.services.GoalService;
import be.ghostwritertje.budgetting.services.RekeningService;
import be.ghostwritertje.budgetting.services.UserService;
import be.ghostwritertje.budgetting.wicket.panels.GoalsPanel;
import de.agilecoders.wicket.core.markup.html.bootstrap.components.progress.ProgressBar;
import de.agilecoders.wicket.core.markup.html.bootstrap.navigation.BootstrapPagingNavigator;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.form.select.Select;
import org.apache.wicket.extensions.markup.html.form.select.SelectOption;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.file.File;
import org.apache.wicket.util.lang.Bytes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
public class RekeningPage extends WicketPage {
    @SpringBean
    private UserService userServiceImpl;

    @SpringBean
    private RekeningService rekeningService;

    @SpringBean
    private CsvService csvService;

    @SpringBean
    private GoalService goalService;


    private FileUploadField fileUpload;
    private String UPLOAD_FOLDER = "csvFiles";

    public RekeningPage(final PageParameters parameters) {
        super();

        Rekening rekening = rekeningService.getRekening(parameters.get("rekeningNummer").toString());

        init(rekening);

    }


    public void init(final Rekening rekening) {

        add(new Label("rekeningNaam", rekening.getNaam()));
        add(new Label("balans", rekeningService.getBalans(rekening)));
        PageableListView<Statement> listView = new PageableListView<Statement>("statements", rekeningService.getStatements(rekening), 10) {

            @Override
            protected void populateItem(ListItem<Statement> statementListItem) {
                Statement statement = statementListItem.getModelObject();
                statementListItem.add(new Label("datum", statement.getDatumString()));
                statementListItem.add(new Label("categorie", statement.getCategorie()));
                statementListItem.add(new Label("rekening", rekening.getNummer()));
                statementListItem.add(new Label("andereRekening", ""));
                statementListItem.add(new GoalOptionForm("goalOptionForm", statement, rekening));
                statementListItem.addOrReplace(new Label("bedrag", ""));

                if (statement.getAankomstRekening() != null) {
                    if (statement.getAankomstRekening().getNummer().equals(rekening.getNummer())) {
                        statementListItem.addOrReplace(new Label("bedrag", statement.getBedrag()));

                    } else {
                        statementListItem.addOrReplace(new Label("andereRekening", statement.getAankomstRekening().getNummer()));
                    }

                }
                if (statement.getVertrekRekening() != null) {
                    if (statement.getVertrekRekening().getNummer().equals(rekening.getNummer())) {
                        statementListItem.addOrReplace(new Label("bedrag", -statement.getBedrag()));
                    } else {
                        statementListItem.addOrReplace(new Label("andereRekening", statement.getVertrekRekening().getNummer()));
                    }


                }
            }
        };
        this.add(listView);
        add(new BootstrapPagingNavigator("navigator", listView));

        add(new Label("totaal", rekeningService.getBalans(rekening)));


        add(new FeedbackPanel("feedback"));



/*
        // Enable multipart mode (need for uploads file)
        form.setMultiPart(true);

        // max upload size, 10k
        form.setMaxSize(Bytes.megabytes(10));

        form.add(fileUpload = new FileUploadField("fileUpload"));
*/

//      add(form);

        addFileUpload(rekening);
        add(new GoalsPanel("goalsPanel", rekening));
    }

    private void addFileUpload(final Rekening rekening) {
        Form<Void> fileUploadForm = new Form<Void>("fileUploadForm"){
            @Override
            protected void onSubmit() {

                final FileUpload uploadedFile = fileUpload.getFileUpload();
                if (uploadedFile != null) {

                    // write to a new file
                    File newFile = new File(UPLOAD_FOLDER
                            + uploadedFile.getClientFileName());

                    if (newFile.exists()) {
                        newFile.delete();
                    }

                    try {
                        newFile.createNewFile();
                        uploadedFile.writeTo(newFile);

                        info("saved file: " + uploadedFile.getClientFileName());
                        csvService.uploadCSVFile(newFile.getAbsolutePath(), rekening);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        };

        add(fileUploadForm);
        fileUploadForm.setMultiPart(true);
        fileUpload = new FileUploadField("fileUpload");
        fileUploadForm.add(fileUpload);
        fileUploadForm.setMaxSize(Bytes.megabytes(10));
        ProgressBar progressBar = new ProgressBar("progressBar", Model.of(0));

        progressBar.striped(true).active(true);
        fileUploadForm.add(progressBar);
        fileUploadForm.add(new AjaxButton("submit") {
        });
    }

    private class GoalOptionForm extends Form {

        private IModel<Goal> gekozenGoal;
        private Statement statement;

        public GoalOptionForm(String id, Statement statement, Rekening rekening) {
            super(id);
            this.statement = statement;
            this.init(rekening);
        }

        private class GoalChoiceRenderer<Goal> extends ChoiceRenderer<Goal> {

            @Override
            public Object getDisplayValue(Goal goal) {
                return goal.toString();
            }
        }

        private void init(Rekening rekening) {
            List<Goal> goals = goalService.getGoals(rekening);
            //public DropDownChoice(final String id, IModel<T> model, final List<? extends T> choices,
            gekozenGoal = new Model<>(null);
            DropDownChoice<Goal> goalDropDownChoice = new DropDownChoice<Goal>("goal", gekozenGoal, goals){
                @Override
                protected boolean wantOnSelectionChangedNotifications() {
                    return true;
                }

                @Override
                protected void onSelectionChanged(Goal newSelection) {
                    super.onSelectionChanged(newSelection);
                    goalService.setGoal(statement, newSelection);
                }

            };

            if(statement.getGoal() != null){
                goalDropDownChoice.setDefaultModelObject(statement.getGoal());
            }
           // goalDropDownChoice.setChoiceRenderer(new GoalChoiceRenderer<Goal>());

            this.add(goalDropDownChoice);

        }

        @Override
        protected void onSubmit() {

//            goalService.setGoal(statement, gekozenGoal);
        }


    }


}
