package be.estateeleven.domain;

import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Table;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;

@Entity
@DiscriminatorValue("HUURDER")
@SecondaryTable(
        name = "HUURDER",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "Id")
)
@Table(appliesTo = "HUURDER", fetch = FetchMode.SELECT, optional = false)
public class Huurder extends User {

}
