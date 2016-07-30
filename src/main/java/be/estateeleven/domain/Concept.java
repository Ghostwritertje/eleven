package be.estateeleven.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * Created by Jorandeboever
 * Date: 30-Jul-16.
 */
@Entity
@Table(name = "CONCEPT")
public class Concept {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @ManyToOne
    @JoinColumn(name = "HUUDER_ID", nullable = false)
    private Huurder huurder;

    @OneToMany( mappedBy = "concept")
    private List<Bieding> biedingen;

    private String naam;
    private String omschrijving;
    private String ruimteInvulling;
    private Integer plaatsbenodigdheid;
    private Date beginDatum;
    private Date eindDatum;

    @ManyToOne
    @JoinColumn(name = "DOELEINDE_ID")
    private Doeleinde doeleinde;
}
