package be.ghostwritertje.budgetting.wicket.pages;

import be.ghostwritertje.budgetting.domain.User;
import be.ghostwritertje.budgetting.services.api.UserService;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
public class OverzichtPage extends WicketPage {

    @SpringBean
    private UserService userService;

    @Override
    protected void onInitialize() {
        super.onInitialize();
        User user = new User();
        user.setUsername("Joran");
        this.userService.create(user);

    }
}
