package be.ghostwritertje.budgetting.wicket;

import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.authorization.IUnauthorizedComponentInstantiationListener;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource;

/**
 * Created by jorandeboever
 * on 26/04/16.
 */
public class WicketAuthorizationStrategy  implements IAuthorizationStrategy, IUnauthorizedComponentInstantiationListener {
    @Override
    public boolean isInstantiationAuthorized(Class aClass) {
        // logger.info("Checking if user is authorized");
        if(OverzichtPage.class.isAssignableFrom(aClass)){
            return WicketSession.get().isAuthenticated();
        }
        return true ;
    }

    @Override
    public boolean isActionAuthorized(Component component, Action action) {
        return true;
    }

    @Override
    public boolean isResourceAuthorized(IResource resource, PageParameters parameters) {
        return true;
    }

    @Override
    public void onUnauthorizedInstantiation(Component component) {
        throw new RestartResponseAtInterceptPageException(SigninPage.class);
    }
}
