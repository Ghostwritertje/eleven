package be.ghostwritertje.budgetting.wicket.reference;

import org.apache.wicket.request.resource.CssResourceReference;

/**
 * Created by jorandeboever
 * on 6/05/16.
 */
public final class SimpleSidebarCSSReference extends CssResourceReference {
    private static final long serialVersionUID = -2308177295080737875L;

    private SimpleSidebarCSSReference() {
        super(SimpleSidebarCSSReference.class, "stylesheet.css");
    }

    public static CssResourceReference get() {
        return new SimpleSidebarCSSReference();
    }
}
