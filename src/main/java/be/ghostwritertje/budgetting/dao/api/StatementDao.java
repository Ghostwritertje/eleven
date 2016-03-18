package be.ghostwritertje.budgetting.dao.api;

import be.ghostwritertje.budgetting.domain.Rekening;
import be.ghostwritertje.budgetting.domain.Statement;

import java.util.List;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
public interface StatementDao {
    List<Statement> getStatements(Rekening rekening);

    void createStatement(Statement statement);

    void deleteAllStatements();
}
