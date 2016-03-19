package be.ghostwritertje.budgetting.wicket;

import be.ghostwritertje.budgetting.services.CsvService;
import be.ghostwritertje.budgetting.dao.api.RekeningDao;
import be.ghostwritertje.budgetting.dao.api.StatementDao;
import be.ghostwritertje.budgetting.domain.Rekening;
import be.ghostwritertje.budgetting.domain.Statement;
import be.ghostwritertje.budgetting.services.UserService;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.file.File;
import org.apache.wicket.util.lang.Bytes;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
public class RekeningPage extends WicketPage {
    @SpringBean
    private UserService userServiceImpl;

    @SpringBean
    private RekeningDao rekeningDao;

    @SpringBean
    private StatementDao statementDao;

    @SpringBean
    private CsvService csvService;

    private FileUploadField fileUpload;
    private String UPLOAD_FOLDER = "csvFiles";

    public RekeningPage(final PageParameters parameters)
    {
        super();

        Rekening rekening = rekeningDao.getRekening(parameters.get("rekeningNummer").toString());

        init(rekening);

    }


    public void init(final Rekening rekening) {
        //TODO_JORAN:  Rekening rekening

        add(new Label("rekeningNaam", rekening.getNaam()));
        add(new ListView<Statement>("statements", statementDao.getStatements(rekening)) {
            @Override
            protected void populateItem(ListItem<Statement> statementListItem) {
                statementListItem.add(new Label("nummer", statementListItem.getModelObject().getId()));
                statementListItem.add(new Label("datum", statementListItem.getModelObject().getDatumString()));
                statementListItem.add(new Label("categorie", statementListItem.getModelObject().getCategorie()));
                if (statementListItem.getModelObject().getVertrekRekening() != null) {
                    statementListItem.add(new Label("vertrekRekening", statementListItem.getModelObject().getVertrekRekening().getNummer()));
                } else {
                    statementListItem.add(new Label("vertrekRekening", ""));

                }
                if (statementListItem.getModelObject().getAankomstRekening() != null) {
                    statementListItem.add(new Label("aankomstRekening", statementListItem.getModelObject().getAankomstRekening().getNummer()));
                } else {
                    statementListItem.add(new Label("aankomstRekening", ""));

                }

                statementListItem.add(new Label("bedrag", statementListItem.getModelObject().getBedrag()));
            }
        });

        add(new Label("totaal", rekeningDao.getBalans(rekening)));


        add(new FeedbackPanel("feedback"));

        Form<?> form = new Form<Void>("form") {
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

        // Enable multipart mode (need for uploads file)
        form.setMultiPart(true);

        // max upload size, 10k
        form.setMaxSize(Bytes.megabytes(10));

        form.add(fileUpload = new FileUploadField("fileUpload"));

        add(form);

        add(new GoalsPanel("goalsPanel", rekening));
    }
}
