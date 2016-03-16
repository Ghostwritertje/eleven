package be.ghostwritertje.budgetting.services;

import be.ghostwritertje.budgetting.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDaoImpl;

    public String getUsername() {
        return userDaoImpl.findUser("Joran").getUsername();
    }
}
