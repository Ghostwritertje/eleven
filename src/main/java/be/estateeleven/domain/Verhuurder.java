package be.estateeleven.domain;

import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Table;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;


@Entity
@DiscriminatorValue("VERHUURDER")
@SecondaryTable(
        name = "VERHUURDER",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "Id")
)
@Table(appliesTo = "VERHUURDER", fetch = FetchMode.SELECT, optional = false)
public class Verhuurder extends User {

}
