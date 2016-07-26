package be.ghostwritertje.budgetting.services.impl;

import be.ghostwritertje.budgetting.dao.api.UserDao;
import be.ghostwritertje.budgetting.domain.User;
import be.ghostwritertje.budgetting.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void create(User user) {
        this.userDao.create(user);
    }
}
