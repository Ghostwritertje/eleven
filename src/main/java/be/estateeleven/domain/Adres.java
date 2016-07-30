package be.estateeleven.domain;

import javax.persistence.Embeddable;

/**
 * Created by Jorandeboever
 * Date: 30-Jul-16.
 */
@Embeddable
public class Adres {
    private String straatNaam;
    private Integer postCode;
    private String stad;
}
