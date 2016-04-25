package be.ghostwritertje.budgetting.services;

import be.ghostwritertje.budgetting.dao.api.StatementDao;
import be.ghostwritertje.budgetting.domain.Rekening;
import be.ghostwritertje.budgetting.domain.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void createStatement(Statement statement)
    {

        statementDao.createStatement(statement);
    }

    public List<Statement> getStatements(Rekening rekening) {
        return statementDao.getStatements(rekening);
    }
}
