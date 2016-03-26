package be.ghostwritertje.budgetting.services;

import be.ghostwritertje.budgetting.dao.api.GoalDao;
import be.ghostwritertje.budgetting.dao.api.RekeningDao;
import be.ghostwritertje.budgetting.dao.api.StatementDao;
import be.ghostwritertje.budgetting.dao.api.UserDao;
import be.ghostwritertje.budgetting.domain.Categorie;
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
public class UserService {
    @Autowired
    private UserDao userDaoImpl;

    @Autowired
    private RekeningService rekeningService;

    @Autowired
    private GoalDao goalDaoImpl;

    @Autowired
    private StatementService statementService;

    @PostConstruct
    public void init() {
      /* statementService.deleteAllStatements();
        goalDaoImpl.deleteAllGoals();

        rekeningService.deleteAllRekeningen();
        userDaoImpl.deleteAllUsers();


        User joran = new User("Joran");
        userDaoImpl.create(joran);*/
/*
        Rekening rekening = new Rekening("Zichtrekening", "BE57 0634 1777 9035", joran);
        rekeningService.create(rekening);
        statementService.createStatement(new Statement(null, rekening, 200, new Date()));
        statementService.createStatement(new Statement(null, rekening, 450, new Date()));
        statementService.createStatement(new Statement(rekening, null, 150, new Date()));


        Rekening langeTermijnRekening = new Rekening("Lange Termijn", "BE32 0836 9311 3402", joran);
        langeTermijnRekening.setCategorie(Categorie.SPAREN);
        rekeningService.create(langeTermijnRekening);

        statementService.createStatement(new Statement(null, langeTermijnRekening, 1000, new Date()));
        statementService.createStatement(new Statement(rekening, langeTermijnRekening, 1000, new Date()));
        statementService.createStatement(new Statement(langeTermijnRekening, null, 250, new Date()));

        Rekening zichtRekening = new Rekening("Spaarrekening", "BE08 0834 0249 2813", joran);
        zichtRekening.setCategorie(Categorie.SPAREN);
        rekeningService.create(zichtRekening);
        statementService.createStatement(new Statement(null, zichtRekening, 25, new Date()));
        statementService.createStatement(new Statement(null, zichtRekening, 50, new Date()));
        statementService.createStatement(new Statement(null, zichtRekening, 30, new Date()));
        statementService.createStatement(new Statement(null, zichtRekening, 80, new Date()));
        statementService.createStatement(new Statement(null, zichtRekening, 10, new Date()));
        statementService.createStatement(new Statement(zichtRekening, null, 75, new Date()));
*/
    }

    public User getUser(String username) {
        return userDaoImpl.findUser(username);
    }


}
