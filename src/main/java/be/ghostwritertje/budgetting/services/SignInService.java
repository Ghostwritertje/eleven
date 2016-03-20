package be.ghostwritertje.budgetting.services;

import be.ghostwritertje.budgetting.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Created by jorandeboever
 * on 20/03/16.
 */
@Service
@Scope("session")
public class SignInService {
    @Autowired
    private UserService userService;

    private User user;

    public void signIn(String username) {
        this.user = userService.getUser(username);
    }

    public User getUser() {
        return user;
    }
}
