package be.estateeleven.services.impl;

import be.estateeleven.dao.api.UserDao;
import be.estateeleven.domain.User;
import be.estateeleven.services.api.UserService;
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
