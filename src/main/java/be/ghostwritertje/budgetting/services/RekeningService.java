package be.ghostwritertje.budgetting.services;

import be.ghostwritertje.budgetting.dao.api.RekeningDao;
import be.ghostwritertje.budgetting.dao.api.StatementDao;
import be.ghostwritertje.budgetting.dao.api.UserDao;
import be.ghostwritertje.budgetting.domain.Rekening;
import be.ghostwritertje.budgetting.domain.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by jorandeboever
 * on 18/03/16.
 */
@Service
public class RekeningService {
    @Autowired
    private StatementDao statementDaoImpl;

    @Autowired
    private RekeningDao rekeningDao;

    @Autowired
    private UserDao userDaoImpl;

    public List<Statement> getStatements(Rekening rekening) {
        return statementDaoImpl.getStatements(rekening);
    }

    @Deprecated
    public void createStatement(Statement statement) {
        if (statement.getVertrekRekening() != null) {
            Rekening rekening = rekeningDao.getRekening(statement.getVertrekRekening().getNummer());
            if (rekening != null) {
                statement.setVertrekRekening(rekening);
            }
        }
        if (statement.getAankomstRekening() != null){
            Rekening rekening = rekeningDao.getRekening(statement.getAankomstRekening().getNummer());
            if (rekening != null) {
                statement.setAankomstRekening(rekening);
            }
        }

        statementDaoImpl.createStatement(statement);
    }

    public void createStatement(String vertrekRekeningNummer, String aankomstRekeningNummer, double bedrag, Date datum) {
        Rekening aankomstRekening = rekeningDao.getRekening(aankomstRekeningNummer);
        Rekening vertrekRekening = rekeningDao.getRekening(vertrekRekeningNummer);

        Statement statement = new Statement(vertrekRekening, aankomstRekening, bedrag, datum);
        statementDaoImpl.createStatement(statement);
    }


    public double getTotaal(Rekening rekening) {
        return rekeningDao.getBalans(rekening);
    }
}
