package be.ghostwritertje.budgetting.services;

import be.ghostwritertje.budgetting.dao.api.StatementDao;
import be.ghostwritertje.budgetting.domain.Rekening;
import be.ghostwritertje.budgetting.domain.Statement;
import be.ghostwritertje.budgetting.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Map<String, Double> getTotaalPerCategorie(String user){
        return statementDao.getTotaalPerCategorie(user);
    }

}
