package be.ghostwritertje.budgetting.wicket;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
public class WicketBudgettingPage extends WebPage {


    public WicketBudgettingPage() {
        add(new Link("rekening") {
            @Override
            public void onClick() {
                getRequestCycle().setResponsePage(RekeningPage.class);
            }
        });

        add(new Label("host", System.getenv("OPENSHIFT_MYSQL_DB_HOST")));
        add(new Label("port", System.getenv("OPENSHIFT_MYSQL_DB_PORT")));
    }
}
