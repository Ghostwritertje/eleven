package be.estateeleven.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
}
