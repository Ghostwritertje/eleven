package be.ghostwritertje.budgetting.dao.api;

import be.ghostwritertje.budgetting.domain.Rekening;
import be.ghostwritertje.budgetting.domain.Statement;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
public interface StatementDao {
    List<Statement> getStatements(Rekening rekening);

    Map<String, Double> getTotalenPerMaand(Rekening rekening);

    void createStatement(Statement statement);

    void deleteAllStatements();

    void createStatement(String vertrekRekening, String aankomstRekening, double bedrag, Date datum);

}
