package be.estateeleven.reference.img;

import org.apache.wicket.request.resource.PackageResourceReference;

public class HomeBannerImgReference extends PackageResourceReference {
    public HomeBannerImgReference() {
        super(HomeBannerImgReference.class, "homebanner.jpg");
    }

    public static PackageResourceReference get() {
        return new HomeBannerImgReference();
    }
}
