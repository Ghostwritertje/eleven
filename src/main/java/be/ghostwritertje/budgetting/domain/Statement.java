package be.ghostwritertje.budgetting.domain;

import javax.persistence.*;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
@Entity
@Table(name = "t_statement")
public class Statement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @OneToOne
    @JoinColumn(name = "vertrekRekeningId")
    private Rekening vertrekRekening;

    @OneToOne
    @JoinColumn(name = "aankomstRekeningId")
    private Rekening aankomstRekening;

    @Column
    private double bedrag;

    public Statement() {
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
}
