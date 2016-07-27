package be.estateeleven.resources.css;

import org.apache.wicket.request.resource.CssResourceReference;

public final class RangeSliderCssReference extends CssResourceReference {
    private static final long serialVersionUID = -2308177295080737875L;

    private RangeSliderCssReference() {
        super(RangeSliderCssReference.class, "rangeslider.css");
    }

    public static CssResourceReference get() {
        return new RangeSliderCssReference();
    }
}