package be.ghostwritertje.budgetting.wicket.pages;

import de.agilecoders.wicket.core.Bootstrap;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
public class WicketPage extends WebPage {


    public WicketPage() {

    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);

        Bootstrap.renderHead(response);

        response.render(JavaScriptHeaderItem.forReference(this.getApplication().getJavaScriptLibrarySettings().getJQueryReference()));


    }
}
