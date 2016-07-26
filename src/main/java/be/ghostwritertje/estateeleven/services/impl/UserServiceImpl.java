package be.ghostwritertje.estateeleven.services.impl;

import be.ghostwritertje.estateeleven.dao.api.UserDao;
import be.ghostwritertje.estateeleven.domain.User;
import be.ghostwritertje.estateeleven.services.api.UserService;
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
