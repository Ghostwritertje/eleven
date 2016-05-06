package be.ghostwritertje.budgetting.wicket.reference;

import org.apache.wicket.request.resource.CssResourceReference;

/**
 * Created by jorandeboever
 * on 6/05/16.
 */
public final class SideBarCssReference extends CssResourceReference {
    private static final long serialVersionUID = -2308177295080737875L;

    private SideBarCssReference() {
        super(SideBarCssReference.class, "Sidebar.css");
    }

    public static CssResourceReference get() {
        return new SideBarCssReference();
    }
}
