package be.ghostwritertje.estateeleven.wicket.pages;

import be.ghostwritertje.estateeleven.domain.Eigenaar;
import be.ghostwritertje.estateeleven.domain.User;
import be.ghostwritertje.estateeleven.services.api.UserService;
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
        User user = new Eigenaar();
        user.setUsername("Joran");
        this.userService.create(user);

    }
}
