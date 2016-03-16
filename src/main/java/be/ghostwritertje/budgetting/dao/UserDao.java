package be.ghostwritertje.budgetting.dao;

import be.ghostwritertje.budgetting.domain.User;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
public interface UserDao {
    void create(User user);

    User findUser(String username);
}
