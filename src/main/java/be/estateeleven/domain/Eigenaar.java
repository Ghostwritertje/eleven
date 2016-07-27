package be.estateeleven.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("EIGENAAR")
public class Eigenaar extends User {
}
