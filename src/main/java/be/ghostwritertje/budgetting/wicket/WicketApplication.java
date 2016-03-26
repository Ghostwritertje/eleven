package be.ghostwritertje.budgetting.wicket;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.settings.BootstrapSettings;
import de.agilecoders.wicket.core.settings.CookieThemeProvider;
import de.agilecoders.wicket.core.settings.IBootstrapSettings;
import de.agilecoders.wicket.core.settings.ThemeProvider;
import de.agilecoders.wicket.themes.markup.html.bootswatch.BootswatchTheme;
import de.agilecoders.wicket.themes.markup.html.bootswatch.BootswatchThemeProvider;
import org.apache.log4j.Logger;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
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
        this.configureBootstrap();
    }
       @Override
    public Session newSession(Request request, Response response) {
        return new WicketSession(request);
    }

    private void configureBootstrap() {
        final IBootstrapSettings settings = new BootstrapSettings();
 /*       final ThemeProvider themeProvider = new BootswatchThemeProvider(BootswatchTheme.Superhero);

        settings.setJsResourceFilterName("footer-container")
                .setThemeProvider(themeProvider);
*/
        Bootstrap.install(this, settings);

    }
}
