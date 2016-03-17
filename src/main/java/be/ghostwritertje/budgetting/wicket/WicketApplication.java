package be.ghostwritertje.budgetting.wicket;

import de.agilecoders.wicket.core.Bootstrap;
import org.apache.log4j.Logger;
import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
public class WicketApplication extends WebApplication {
    private static Logger logger = Logger.getLogger(WicketApplication.class);

    @Override
    public Class<? extends Page> getHomePage() {
        logger.info("Homepage wordt opgevraagd!");
        return OverzichtPage.class;
    }

    @Override
    protected void init() {
        super.init();
        super.getComponentInstantiationListeners().add(new SpringComponentInjector(this));

        Bootstrap.install(this);
    }
}
