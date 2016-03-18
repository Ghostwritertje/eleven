package be.ghostwritertje.budgetting.services;

import be.ghostwritertje.budgetting.dao.api.StatementDao;
import be.ghostwritertje.budgetting.domain.Rekening;
import be.ghostwritertje.budgetting.domain.Statement;
import be.ghostwritertje.budgetting.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by jorandeboever
 * on 18/03/16.
 */
@Service
public class CsvService {

    @Autowired
    private StatementDao statementDaoImpl;

    public void uploadCSVFile(String fileUrl, Rekening rekening) {

        String csvFile = "/home/jorandeboever/Downloads/BE57 0634 1777 9035 2016-03-18 20-23-30 1.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] country = line.split(cvsSplitBy);



                if (country.length > 0 && country[0].startsWith("BE")) {
                    Statement statement = new Statement();
                    statement.setBedrag(Double.parseDouble(country[10].replace(",", ".")));
                    if(statement.getBedrag() < 0){
                        statement.setVertrekRekening(rekening);
                        statement.setAankomstRekening(new Rekening("", country[4], new User()));
                    }else {
                        statement.setVertrekRekening(new Rekening("",  country[4], new User()));
                        statement.setAankomstRekening(rekening);
                    }
                    System.out.println(statement);
                    statementDaoImpl.createStatement(statement);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Done");
    }
}
