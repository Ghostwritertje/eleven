package be.ghostwritertje.budgetting.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
@Entity
@Table(name = "t_statement")
public class Statement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vertrekRekeningId")
    private Rekening vertrekRekening;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "aankomstRekeningId")
    private Rekening aankomstRekening;

    @Column(nullable = false)
    private Date datum;

    @Column
    private double bedrag;

    public Statement() {
    }

    public Statement(Rekening vertrekRekening, Rekening aankomstRekening, double bedrag, Date datum) {
        this.vertrekRekening = vertrekRekening;
        this.aankomstRekening = aankomstRekening;
        this.bedrag = bedrag;
        this.datum = datum;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public double getBedrag() {
        return bedrag;
    }

    public void setBedrag(double bedrag) {
        this.bedrag = bedrag;
    }

    public Rekening getVertrekRekening() {
        return vertrekRekening;
    }

    public void setVertrekRekening(Rekening vertrekRekening) {
        this.vertrekRekening = vertrekRekening;
    }

    public Rekening getAankomstRekening() {
        return aankomstRekening;
    }

    public void setAankomstRekening(Rekening aankomstRekening) {
        this.aankomstRekening = aankomstRekening;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date date) {
        this.datum = date;
    }

    @Override
    public String toString() {
        return "Statement: Vertrekrekening = " + vertrekRekening + ", aankomstrekening = " + aankomstRekening + ", bedrag = " + bedrag;
    }
}
