package be.ghostwritertje.budgetting.services;

import be.ghostwritertje.budgetting.dao.api.RekeningDao;
import be.ghostwritertje.budgetting.dao.api.StatementDao;
import be.ghostwritertje.budgetting.dao.api.UserDao;
import be.ghostwritertje.budgetting.domain.Rekening;
import be.ghostwritertje.budgetting.domain.Statement;
import be.ghostwritertje.budgetting.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jorandeboever
 * on 18/03/16.
 */
@Service
public class RekeningService {

    @Autowired
    private RekeningDao rekeningDao;

    @Autowired
    private StatementService statementService;

    public List<Statement> getStatements(Rekening rekening) {
        return statementService.getStatements(rekening);
    }

    public List<Statement> getStatements(String username) {
        List<Statement> statements = new ArrayList<>();
        for(Rekening rekening: this.getRekeningen(username)){
            statements.addAll(statementService.getStatements(rekening));
        }

        return statements;
    }


    public void createStatement(String vertrekRekeningNummer, String aankomstRekeningNummer, double bedrag, Date datum) {
        Rekening aankomstRekening = rekeningDao.getRekening(aankomstRekeningNummer);
        Rekening vertrekRekening = rekeningDao.getRekening(vertrekRekeningNummer);

        Statement statement = new Statement(vertrekRekening, aankomstRekening, bedrag, datum);
        statementService.createStatement(statement);
    }

    public void createStatement(Statement statement) {
        statement.setVertrekRekening(rekeningDao.getRekening(statement.getVertrekRekening().getNummer()));
        statement.setAankomstRekening(rekeningDao.getRekening(statement.getAankomstRekening().getNummer()));

        statementService.createStatement(statement);
    }

    public double getTotaal(Rekening rekening) {
        return rekeningDao.getBalans(rekening);
    }

    public double getBalans(final Rekening rekening) {
        return rekeningDao.getBalans(rekening);
    }

    public double getBalans(User user) {
        double totaal = 0;
        for (Rekening rekening : rekeningDao.getRekeningen(user.getUsername())) {
            totaal += getBalans(rekening);
        }

        return totaal;
    }

    public List<Rekening> getRekeningen(String userNaam) {
        return rekeningDao.getRekeningen(userNaam);
    }

    public void createRekening(String rekeningNaam, String rekeningNummer, User user) {
        Rekening rekening = new Rekening(rekeningNaam, rekeningNummer, user);
        rekeningDao.create(rekening);
    }

    public Rekening getRekening(String rekeningNummer) {
        return rekeningDao.getRekening(rekeningNummer);
    }

    protected void deleteAllRekeningen() {
        rekeningDao.deleteAllRekeningen();
    }

    public void create(Rekening rekening) {
        rekeningDao.create(rekening);
    }
}
