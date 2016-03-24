package be.ghostwritertje.budgetting.services;

import be.ghostwritertje.budgetting.dao.api.RekeningDao;
import be.ghostwritertje.budgetting.domain.Rekening;
import be.ghostwritertje.budgetting.domain.Statement;
import be.ghostwritertje.budgetting.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jorandeboever
 * on 18/03/16.
 */
@Service
public class CsvService {

    @Autowired
    private RekeningService rekeningService;


    public void uploadCSVFile(String fileUrl,final Rekening rekening) {

        BufferedReader br = null;
        String line;
        String cvsSplitBy = ";";

        try {

            br = new BufferedReader(new FileReader(fileUrl));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] country = line.split(cvsSplitBy);



                if (country.length > 0 && country[0].startsWith("BE")) {
                    Statement statement = new Statement();
                    statement.setBedrag(Math.abs(Double.parseDouble(country[10].replace(",", "."))));
                    Date date = new Date();
                    try {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        date = formatter.parse(country[1]);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    statement.setDatum(date);

                    if(statement.getBedrag() < 0){
                        statement.setVertrekRekening(rekening);
                        statement.setAankomstRekening(new Rekening("", country[4], new User()));
                    }else {
                        statement.setVertrekRekening(new Rekening("",  country[4], new User()));
                        statement.setAankomstRekening(rekening);
                    }
                    rekeningService.createStatement(statement.getVertrekRekening().getNummer(), statement.getAankomstRekening().getNummer(), statement.getBedrag(), statement.getDatum());
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

    }
}
