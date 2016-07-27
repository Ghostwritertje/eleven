package be.estateeleven.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ONDERNEMER")
public class Ondernemer extends User {

}
