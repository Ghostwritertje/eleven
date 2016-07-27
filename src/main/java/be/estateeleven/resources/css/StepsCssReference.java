package be.estateeleven.resources.css;

import org.apache.wicket.request.resource.CssResourceReference;

public final class StepsCssReference extends CssResourceReference {
    private static final long serialVersionUID = -2308177295080737875L;

    private StepsCssReference() {
        super(StepsCssReference.class, "jquery.steps.css");
    }

    public static CssResourceReference get() {
        return new StepsCssReference();
    }
}