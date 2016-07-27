package be.estateeleven.resources.css;

import org.apache.wicket.request.resource.CssResourceReference;

public final class FlexSliderCssReference extends CssResourceReference {
    private static final long serialVersionUID = -2308177295080737875L;

    private FlexSliderCssReference() {
        super(FlexSliderCssReference.class, "flexslider.css");
    }

    public static CssResourceReference get() {
        return new FlexSliderCssReference();
    }
}