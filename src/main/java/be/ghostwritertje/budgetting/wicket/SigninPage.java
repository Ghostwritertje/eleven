package be.ghostwritertje.budgetting.wicket;


import be.ghostwritertje.budgetting.services.UserService;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Created by De Boever Joran
 * on 22/02/2016.
 */
public class SigninPage extends WicketPage {
    public SigninPage() {
        add(new SignInForm("signInForm"));
    }

    private static class SignInForm extends StatelessForm {

        private String username;

        @SpringBean
        private UserService userService;

        public SignInForm(String id) {
            super(id);
            this.setModel(new CompoundPropertyModel(this));
            add(new TextField("username"));

        }

        @Override
        protected void onSubmit() {
            if (userService.signIn(username)) {
                continueToOriginalDestination();

            } else {
                error("Uknown username/ password");
            }
        }


    }


}
