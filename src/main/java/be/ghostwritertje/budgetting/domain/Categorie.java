package be.ghostwritertje.budgetting.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jorandeboever
 * on 18/03/16.
 */
public enum Categorie {
    HUISHOUDEN, KINDEREN, BANKKOSTEN, VERZEKERINGEN, WONEN, AUTO, KLEDIJ, MEDISCH, TELECOM, VRIJETIJD, INKOMEN, INTREST, OVERIG;


    public static List<Categorie> getInkomenCategorieen() {
        List<Categorie> lijst = new ArrayList<>();
        lijst.add(INKOMEN);
        lijst.add(INTREST);
        return lijst;
    }

    public static List<Categorie> getUitgavenCategorieen() {
        List<Categorie> lijst = new ArrayList<>();
        lijst.add(HUISHOUDEN);
        lijst.add(KINDEREN);
        lijst.add(BANKKOSTEN);
        lijst.add(VERZEKERINGEN);
        lijst.add(WONEN);
        lijst.add(AUTO);
        lijst.add(KLEDIJ);
        lijst.add(MEDISCH);
        lijst.add(TELECOM);
        lijst.add(VRIJETIJD);
        lijst.add(OVERIG);

        return lijst;
    }
}
