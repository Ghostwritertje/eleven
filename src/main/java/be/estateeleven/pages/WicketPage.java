package be.estateeleven.pages;

import be.estateeleven.panels.FooterPanel;
import be.estateeleven.reference.css.EstateElevenCssReference;
import be.estateeleven.reference.img.HomeBannerImgReference;
import be.estateeleven.reference.img.LogoImgReference;
import be.estateeleven.resources.css.DatePickerCssReference;
import be.estateeleven.resources.css.FlexSliderCssReference;
import be.estateeleven.resources.css.FontAwesomeCssReference;
import be.estateeleven.resources.css.RangeSliderCssReference;
import be.estateeleven.resources.css.StepsCssReference;
import be.estateeleven.resources.js.BannerJsReference;
import de.agilecoders.wicket.core.Bootstrap;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.image.Image;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
public class WicketPage extends WebPage {


    public WicketPage() {

    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.add(new Image("homeBanner", HomeBannerImgReference.get()));
        this.add(new Image("logo", LogoImgReference.get()));
        this.add(new FooterPanel("footer"));
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);

        Bootstrap.renderHead(response);

        response.render(JavaScriptHeaderItem.forReference(this.getApplication().getJavaScriptLibrarySettings().getJQueryReference()));

        response.render(CssReferenceHeaderItem.forReference(EstateElevenCssReference.get()));
        response.render(CssReferenceHeaderItem.forReference(FontAwesomeCssReference.get()));
        response.render(CssReferenceHeaderItem.forReference(BannerJsReference.get()));
        response.render(CssReferenceHeaderItem.forReference(FlexSliderCssReference.get()));
        response.render(CssReferenceHeaderItem.forReference(RangeSliderCssReference.get()));
        response.render(CssReferenceHeaderItem.forReference(StepsCssReference.get()));
        response.render(CssReferenceHeaderItem.forReference(DatePickerCssReference.get()));


        response.render(JavaScriptHeaderItem.forReference(BannerJsReference.get()));
    }
}
