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
import java.util.Date;

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
        statementDao.deleteAllStatements();
        rekeningDao.deleteAllRekeningen();
        userDaoImpl.deleteAllUsers();


        User joran = new User("Joran");
        userDaoImpl.create(joran);

        Rekening rekening = new Rekening("Zichtrekening", "BE57 0634 1777 9035", joran);
        rekeningDao.create(rekening);
        statementDao.createStatement(new Statement(new Rekening(), rekening, 200, new Date()));
        statementDao.createStatement(new Statement(new Rekening(), rekening, 450, new Date()));
        statementDao.createStatement(new Statement(rekening, new Rekening(), 150, new Date()));


        Rekening langeTermijnRekening = new Rekening("Lange Termijn", "804575", joran);
        rekeningDao.create(langeTermijnRekening);
        statementDao.createStatement(new Statement(new Rekening(), langeTermijnRekening, 1000, new Date()));
        statementDao.createStatement(new Statement(new Rekening(), langeTermijnRekening, 1000, new Date()));
        statementDao.createStatement(new Statement(langeTermijnRekening, new Rekening(), 250, new Date()));

        Rekening zichtRekening = new Rekening("Spaarrekening", "6084576", joran);
        rekeningDao.create(zichtRekening);
        statementDao.createStatement(new Statement(new Rekening(), zichtRekening, 25, new Date()));
        statementDao.createStatement(new Statement(new Rekening(), zichtRekening, 50, new Date()));
        statementDao.createStatement(new Statement(new Rekening(), zichtRekening, 30, new Date()));
        statementDao.createStatement(new Statement(new Rekening(), zichtRekening, 80, new Date()));
        statementDao.createStatement(new Statement(new Rekening(), zichtRekening, 10, new Date()));
        statementDao.createStatement(new Statement(zichtRekening, new Rekening(), 75, new Date()));

    }

    public String getUsername() {
        return userDaoImpl.findUser("Joran").getUsername();
    }


}
