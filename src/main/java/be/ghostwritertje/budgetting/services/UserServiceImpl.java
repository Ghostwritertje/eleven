package be.ghostwritertje.budgetting.services;

import be.ghostwritertje.budgetting.dao.api.RekeningDao;
import be.ghostwritertje.budgetting.dao.api.StatementDao;
import be.ghostwritertje.budgetting.dao.api.UserDao;
import be.ghostwritertje.budgetting.domain.Rekening;
import be.ghostwritertje.budgetting.domain.Statement;
import be.ghostwritertje.budgetting.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDaoImpl;

    @Autowired
    private StatementDao statementDao;

    @Autowired
    private RekeningDao rekeningDao;

    @PostConstruct
    public void init() {
        User joran = new User("Joran");
        userDaoImpl.create(joran);

        Rekening rekening = new Rekening("spaarrekening", "4058486845", joran);
        rekeningDao.create(rekening);
        statementDao.createStatement(new Statement(new Rekening(), rekening, 200));
        statementDao.createStatement(new Statement(new Rekening(), rekening, 450));
        statementDao.createStatement(new Statement(rekening, new Rekening(), 150));


        Rekening langeTermijnRekening = new Rekening("Lange Termijn", "804575", joran);
        rekeningDao.create(langeTermijnRekening);
        statementDao.createStatement(new Statement(new Rekening(), langeTermijnRekening, 1000));
        statementDao.createStatement(new Statement(new Rekening(), langeTermijnRekening, 1000));
        statementDao.createStatement(new Statement(langeTermijnRekening, new Rekening(), 250));

        Rekening zichtRekening = new Rekening("Zichtrekening", "6084576", joran);
        rekeningDao.create(zichtRekening);
        statementDao.createStatement(new Statement(new Rekening(), zichtRekening, 25));
        statementDao.createStatement(new Statement(new Rekening(), zichtRekening, 50));
        statementDao.createStatement(new Statement(new Rekening(), zichtRekening, 30));
        statementDao.createStatement(new Statement(new Rekening(), zichtRekening, 80));
        statementDao.createStatement(new Statement(new Rekening(), zichtRekening, 10));
        statementDao.createStatement(new Statement(zichtRekening, new Rekening(), 75));

    }

    public String getUsername() {
        return userDaoImpl.findUser("Joran").getUsername();
    }


}
