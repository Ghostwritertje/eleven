package be.estateeleven.resources.js;

import org.apache.wicket.request.resource.JavaScriptResourceReference;

public final class BannerJsReference extends JavaScriptResourceReference {
    private static final long serialVersionUID = -2308177295080737875L;

    private BannerJsReference() {
        super(BannerJsReference.class, "banner.js");
    }

    public static JavaScriptResourceReference get() {
        return new BannerJsReference();
    }
}