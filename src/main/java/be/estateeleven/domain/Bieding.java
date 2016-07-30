package be.estateeleven.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by Jorandeboever
 * Date: 30-Jul-16.
 */
@Entity
@Table(name = "BIEDING")
public class Bieding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @ManyToOne
    @JoinColumn(name = "CONCEPT_ID", nullable = false)
    private Concept concept;

    @ManyToOne
    @JoinColumn(name = "PAND_ID", nullable = false)
    private Pand pand;

    private Integer aantalDagenGeldig;

    public enum BiedingStatus{
        OPEN, GOEDGEKEURD, AFGEWEZEN
    }
}
