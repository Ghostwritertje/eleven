package be.ghostwritertje.budgetting.wicket.pages;

import be.ghostwritertje.budgetting.domain.Rekening;
import be.ghostwritertje.budgetting.domain.Statement;
import be.ghostwritertje.budgetting.services.CsvService;
import be.ghostwritertje.budgetting.services.GoalService;
import be.ghostwritertje.budgetting.services.RekeningService;
import be.ghostwritertje.budgetting.services.StatementService;
import be.ghostwritertje.budgetting.services.UserService;
import be.ghostwritertje.budgetting.wicket.panels.GoalsPanel;
import be.ghostwritertje.budgetting.wicket.panels.StatementTablePanel;
import de.agilecoders.wicket.core.markup.html.bootstrap.components.progress.ProgressBar;
import de.agilecoders.wicket.core.markup.html.bootstrap.navigation.BootstrapPagingNavigator;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.file.File;
import org.apache.wicket.util.lang.Bytes;

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
    private StatementService statementService;

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


    private void init(final Rekening rekening) {

        add(new Label("rekeningNaam", rekening.getNaam()));
        add(new Label("balans", rekeningService.getBalans(rekening)));
        List<Rekening> rekeningList = new ArrayList<>();
        rekeningList.add(rekening);
        add(new FeedbackPanel("feedback"));

        this.add(new StatementTablePanel("statementTablePanel", rekeningService.getStatements(rekening), rekeningList));


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
                        setResponsePage(getPage());
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




}
