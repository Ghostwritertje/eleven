package be.ghostwritertje.budgetting.wicket.pages;

import be.ghostwritertje.budgetting.wicket.WicketSession;
import be.ghostwritertje.budgetting.wicket.pages.OverzichtPage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
public class WicketPage extends WebPage {


    public WicketPage() {

        add(new Link("homepage") {
            @Override
            public void onClick() {
                setResponsePage(OverzichtPage.class);
            }
        });
        add(new Link("Home") {
            @Override
            public void onClick() {
                setResponsePage(OverzichtPage.class);
            }
        });
        add(new Label("username", WicketSession.get().getLoggedInUser()));

   /*     add(new Label("host", System.getenv("OPENSHIFT_MYSQL_DB_HOST")));
        add(new Label("port", System.getenv("OPENSHIFT_MYSQL_DB_PORT")));*/
    }
}