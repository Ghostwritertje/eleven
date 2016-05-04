package be.ghostwritertje.budgetting.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
@Entity
@Table(name = "t_rekening")
public class Rekening implements Serializable, Comparable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column
    private String bank;

    @Column
    private String naam;

    //TODO_JORAN: inverse relatie:  uitgaande statements + inkomende statements

    @Column(unique = true, updatable = false, nullable = false)
    private String nummer;

    @Column
    @Enumerated(EnumType.STRING)
    private RekeningType rekeningType;

    public Rekening() {
    }

    public Rekening(String naam, String nummer, User user) {
        this.naam = naam;
        this.nummer = nummer;
        this.user = user;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getNummer() {
        return nummer;
    }

    public void setNummer(String nummer) {
        this.nummer = nummer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public RekeningType getRekeningType() {
        return rekeningType;
    }

    public void setRekeningType(RekeningType rekeningType) {
        this.rekeningType = rekeningType;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    @Override
    public String toString() {
        return nummer;
    }


    @Override
    public int compareTo(Object o) {
        Rekening otherRekening = (Rekening) o;
        return this.getRekeningType().toString().compareTo(otherRekening.getRekeningType().toString());
    }
}
