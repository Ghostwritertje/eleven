package be.ghostwritertje.budgetting.wicket;

import be.ghostwritertje.budgetting.domain.User;
import be.ghostwritertje.budgetting.services.UserService;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by jorandeboever
 * on 19/03/16.
 */
public class WicketSession extends WebSession {


    private String loggedInUser = "Joran";
    private boolean authenticated = false;

    /**
     * Constructor. Note that {@link RequestCycle} is not available until this constructor returns.
     *
     * @param request
     *            The current request
     */
    public WicketSession(Request request) {
        super(request);
    }

    public static WicketSession get(){
        return (WicketSession) Session.get();
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
        this.authenticated = true;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }
}
