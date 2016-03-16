package be.ghostwritertje.budgetting.wicket;

import be.ghostwritertje.budgetting.domain.Rekening;
import be.ghostwritertje.budgetting.services.UserServiceSimpleImpl;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
public class HomePage extends WebPage {
    @SpringBean
    private UserServiceSimpleImpl userService;

    private Rekening rekening;

    public HomePage() {
/*
        add(new Label("userName", userService.getUsername()));
*/
    }
}
