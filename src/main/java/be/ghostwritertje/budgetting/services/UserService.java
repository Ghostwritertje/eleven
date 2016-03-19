package be.ghostwritertje.budgetting.services;

import be.ghostwritertje.budgetting.domain.User;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
public interface UserService {
    User getUser(String username);
}
