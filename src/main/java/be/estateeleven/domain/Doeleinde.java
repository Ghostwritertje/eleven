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
@Table(name = "DOELEINDE")
public class Doeleinde {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private DoeleindeType doeleindeType;

    public enum DoeleindeType{
        HORECA, EXPOSITIE, RETAIL
    }
}
