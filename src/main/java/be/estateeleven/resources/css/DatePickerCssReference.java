package be.estateeleven.resources.css;

import org.apache.wicket.request.resource.CssResourceReference;

public final class DatePickerCssReference extends CssResourceReference {
    private static final long serialVersionUID = -2308177295080737875L;

    private DatePickerCssReference() {
        super(DatePickerCssReference.class, "bootstrap-datepicker3.min.css");
    }

    public static CssResourceReference get() {
        return new DatePickerCssReference();
    }
}