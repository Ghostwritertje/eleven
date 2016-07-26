package be.ghostwritertje.estateeleven.wicket;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;

/**
 * Created by jorandeboever
 * on 19/03/16.
 */
public class WicketSession extends WebSession {

    private String loggedInUser = "Joran";
    private boolean authenticated = true;

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
