package be.estateeleven.resources.css;

import org.apache.wicket.request.resource.CssResourceReference;

public final class CalendarCssReference extends CssResourceReference {
    private static final long serialVersionUID = -2308177295080737875L;

    private CalendarCssReference() {
        super(CalendarCssReference.class, "calendar.min.css");
    }

    public static CssResourceReference get() {
        return new CalendarCssReference();
    }
}