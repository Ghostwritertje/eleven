package be.estateeleven.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
}
