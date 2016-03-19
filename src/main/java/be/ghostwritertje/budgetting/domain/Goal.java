package be.ghostwritertje.budgetting.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jorandeboever
 * on 19/03/16.
 */
@Entity
@Table(name = "t_goal")
public class Goal {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    public Goal() {
    }

    private String naam;

    private double bedrag;

    private int volgorde;

    @ManyToMany
    @JoinTable(
            name="t_goal_statement",
            joinColumns={@JoinColumn(name="statementId")},
            inverseJoinColumns={@JoinColumn(name="goalId")}
    )
    private List<Statement> statements = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "rekeningId", nullable = false)
    private Rekening rekening;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public double getBedrag() {
        return bedrag;
    }

    public void setBedrag(double bedrag) {
        this.bedrag = bedrag;
    }

    public int getVolgorde() {
        return volgorde;
    }

    public void setVolgorde(int volgorde) {
        this.volgorde = volgorde;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public Rekening getRekening() {
        return rekening;
    }

    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }

    public void setRekening(Rekening rekening) {
        this.rekening = rekening;
    }
}
