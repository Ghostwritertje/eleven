package be.ghostwritertje.budgetting.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
@Entity
@Table(name = "t_rekening")
public class Rekening implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column
    private String naam;

    //TODO_JORAN: inverse relatie:  uitgaande statements + inkomende statements

    @Column(unique = true, updatable = false)
    private String nummer;

    @Column
    @Enumerated(EnumType.STRING)
    private Categorie categorie;

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

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        return nummer;
    }
}
