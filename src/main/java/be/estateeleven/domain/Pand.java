package be.estateeleven.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
@Entity
@Table(name = "PAND")
public class Pand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @OneToMany(mappedBy = "pand")
    private List<Bieding> biedingen;

    @OneToMany(mappedBy = "pand")
    private List<Faciliteit> faciliteiten;

    @ManyToOne
    @JoinColumn(name = "VERHUURDER_ID")
    private Verhuurder verhuurder;

    private String titel;

    @Embedded
    private Adres adres;

    @OneToMany
    @JoinColumn(name = "PAND_ID")
    private List<Doeleinde> doeleindeTypes;
}
