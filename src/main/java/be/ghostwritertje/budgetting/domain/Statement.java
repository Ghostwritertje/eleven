package be.ghostwritertje.budgetting.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vertrekRekeningId")
    private Rekening vertrekRekening;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "aankomstRekeningId")
    private Rekening aankomstRekening;

    @Column(nullable = false)
    private Date datum;

    @Column(unique = true)
    private String mededeling;

    @Column
    private double bedrag;

    @ManyToOne
    @JoinColumn(name = "goalId")
    private Goal goal;

    @Column
    @Enumerated(EnumType.STRING)
    private Categorie categorie = Categorie.OVERIG;

    public Statement() {
    }

    public Statement(Rekening vertrekRekening, Rekening aankomstRekening, double bedrag, Date datum) {
        this.vertrekRekening = vertrekRekening;
        this.aankomstRekening = aankomstRekening;
        this.bedrag = bedrag;
        this.datum = datum;
    }

    public Statement(double bedrag, Date datum) {
        this.bedrag = bedrag;
        this.datum = datum;
    }

    public String getMededeling() {
        return mededeling;
    }

    public String getKorteMededeling() {
        if(mededeling == null) return "";
        if(mededeling.length() > 20){
            return mededeling.substring(0,20);
        }
        return mededeling;
    }

    public void setMededeling(String mededeling) {
        this.mededeling = mededeling;
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

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public String getDatumString() {
        final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(datum);
    }

    public void setDatum(Date date) {
        this.datum = date;
    }

    @Override
    public String toString() {
        return "Statement: Vertrekrekening = " + vertrekRekening + ", aankomstrekening = " + aankomstRekening + ", bedrag = " + bedrag;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }
}
