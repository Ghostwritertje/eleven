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
@DiscriminatorValue("HUURDER")
@SecondaryTable(
        name = "HUURDER",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "Id")
)
@Table(appliesTo = "HUURDER", fetch = FetchMode.SELECT, optional = false)
public class Huurder extends User {

    @OneToMany(mappedBy = "huurder")
    private List<Concept> concepten;
}
