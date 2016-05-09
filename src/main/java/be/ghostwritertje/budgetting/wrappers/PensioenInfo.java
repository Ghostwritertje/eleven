package be.ghostwritertje.budgetting.wrappers;

/**
 * Created by jorandeboever
 * on 9/05/16.
 */
public class PensioenInfo {
    private int leeftijd = 23;
    private double rendement = 5;
    private double verwachteIndex = 0;


    public int getLeeftijd() {
        return leeftijd;
    }

    public double getEchtRendement() {
        return rendement/100;
    }

    public double getVerwachteIndex() {
        return verwachteIndex/100;
    }

}
