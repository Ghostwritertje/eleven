package be.estateeleven.domain;

import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Table;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import java.util.List;


@Entity
@DiscriminatorValue("VERHUURDER")
@SecondaryTable(
        name = "VERHUURDER",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "Id")
)
@Table(appliesTo = "VERHUURDER", fetch = FetchMode.SELECT, optional = false)
public class Verhuurder extends User {

    @OneToMany(mappedBy = "verhuurder")
    private List<Pand> panden;
}
