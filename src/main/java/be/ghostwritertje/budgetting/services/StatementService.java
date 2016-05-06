package be.ghostwritertje.budgetting.services;

import be.ghostwritertje.budgetting.dao.api.RekeningDao;
import be.ghostwritertje.budgetting.dao.api.StatementDao;
import be.ghostwritertje.budgetting.domain.Rekening;
import be.ghostwritertje.budgetting.domain.Statement;
import be.ghostwritertje.budgetting.domain.User;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by jorandeboever
 * on 24/03/16.
 */
@Service
public class StatementService {
    @Autowired
    private StatementDao statementDao;

    @Autowired
    private RekeningDao rekeningDao;

    protected void deleteAllStatements() {
        statementDao.deleteAllStatements();
    }

    public void createStatement(Statement statement) {

        statementDao.createStatement(statement);
    }

    public List<Statement> getStatements(Rekening rekening) {
        return statementDao.getStatements(rekening);
    }

    public Map<String, Double> getTotalenPerMaand(Rekening rekening) {
        return statementDao.getTotalenPerMaand(rekening);
    }

    public List<Statement> getInkomendeStatementsOverAlleRekeningen(String username) {
        return statementDao.getInkomendeStatementsOverAlleRekeningen(username);
    }

    public Map<String, Double> getTotaalPerCategorie(String user) {
        return statementDao.getTotaalPerCategorie(user);
    }

    public void createMaandelijkseDoorlopendeTransactie(String vertrekRekeningNummer, String aankomstRekeningNummer, double bedrag, Date datum, String mededeling,  int aantal) {
        Rekening vertrekRekening = rekeningDao.getRekening(vertrekRekeningNummer);
        Rekening aankomstRekening = rekeningDao.getRekening(aankomstRekeningNummer);

        for (int i = 0; i < aantal; i++) {
            LocalDate localDate = LocalDate.fromDateFields(datum);
            Date nieuweDatum = localDate.plusMonths(i).toDate();
            statementDao.createStatement(new Statement(vertrekRekening, aankomstRekening, bedrag, nieuweDatum, mededeling));
        }
    }

}
