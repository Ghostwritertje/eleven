package be.ghostwritertje.budgetting.wicket.pages;

import be.ghostwritertje.budgetting.wicket.reference.SimpleSidebarCSSReference;
import be.ghostwritertje.budgetting.wicket.WicketSession;
import de.agilecoders.wicket.core.Bootstrap;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
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
        add(new Link("inkomsten") {
            @Override
            public void onClick() {
                setResponsePage(InkomstenPage.class);
            }
        });
        add(new Link("pensioen") {
            @Override
            public void onClick() {
                setResponsePage(PensioenPage.class);
            }
        });
        add(new Link("doorlopendStatement") {
            @Override
            public void onClick() {
                setResponsePage(DoorlopendStatementPage.class);
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

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);

        Bootstrap.renderHead(response);

        response.render(JavaScriptHeaderItem.forReference(this.getApplication().getJavaScriptLibrarySettings().getJQueryReference()));

        response.render(CssReferenceHeaderItem.forReference(SimpleSidebarCSSReference.get()));

    }
}
