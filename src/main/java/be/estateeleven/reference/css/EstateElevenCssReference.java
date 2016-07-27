package be.estateeleven.reference.css;

import org.apache.wicket.request.resource.CssResourceReference;

public final class EstateElevenCssReference extends CssResourceReference {
    private static final long serialVersionUID = -2308177295080737875L;

    private EstateElevenCssReference() {
        super(EstateElevenCssReference.class, "estateeleven.css");
    }

    public static CssResourceReference get() {
        return new EstateElevenCssReference();
    }
}