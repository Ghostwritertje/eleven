package be.estateeleven.resources.css;

import org.apache.wicket.request.resource.CssResourceReference;

public final class FontAwesomeCssReference extends CssResourceReference {
    private static final long serialVersionUID = -2308177295080737875L;

    private FontAwesomeCssReference() {
        super(FontAwesomeCssReference.class, "font-awesome.min.css");
    }

    public static CssResourceReference get() {
        return new FontAwesomeCssReference();
    }
}