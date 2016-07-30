package be.estateeleven.pages;

import be.estateeleven.domain.Verhuurder;
import be.estateeleven.domain.User;
import be.estateeleven.services.api.UserService;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
public class OverzichtPage extends WicketPage {

    private static final long serialVersionUID = -2251001605357805365L;
    @SpringBean
    private UserService userService;

    @Override
    protected void onInitialize() {
        super.onInitialize();
        User user = new Verhuurder();
        user.setUsername("Joran");
        this.userService.create(user);



    }
}
