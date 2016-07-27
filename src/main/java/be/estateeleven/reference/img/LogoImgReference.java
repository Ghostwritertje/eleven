package be.estateeleven.reference.img;

import org.apache.wicket.request.resource.PackageResourceReference;

public class LogoImgReference extends PackageResourceReference {
    public LogoImgReference() {
        super(LogoImgReference.class, "logo.png");
    }

    public static PackageResourceReference get() {
        return new LogoImgReference();
    }
}
