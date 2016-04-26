package be.ghostwritertje.budgetting.wicket;

import org.apache.wicket.Component;
import org.apache.wicket.authorization.Action;

/**
 * Created by jorandeboever
 * on 26/04/16.
 */
public interface IAuthorizationStrategy {
    boolean isInstantiationAuthorized(Class componentClass);

    boolean isActionAuthorized(Component component, Action action);

    public static final IAuthorizationStrategy ALLOW_ALL =
            new IAuthorizationStrategy() {
                public boolean isActionAuthorized(Component c, Action action) {
                    return true;
                }

                public boolean isInstantiationAuthorized(final Class c) {
                    return true;
                }
            };
}
