package be.ghostwritertje.budgetting.services;

import org.springframework.stereotype.Service;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
@Service
public class UserServiceSimpleImpl implements UserService {

    public String getUsername() {
        return "simple text";
    }
}
