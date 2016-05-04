package be.ghostwritertje.budgetting.services;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jorandeboever
 * on 4/05/16.
 */
@Service
public class SpeculatieService {

    public Map<String, List<Number>> berekenPensioenFonds(double geschatteIntrest, int leeftijd, double geschatteIndex) {
        int pensioenIndexVastTotEnMet = 2018;
        Double pensioenInleg2016 = 940.00;

        Map<String, List<Number>> map = new HashMap<>();

        List<Number> leeftijden = new ArrayList<>();
        List<Number> inlegBedragen = new ArrayList<>();
        List<Number> intrestBedragen = new ArrayList<>();
        List<Number> taksBedragen = new ArrayList<>();
        List<Number> absoluutBedragen = new ArrayList<>();

        Double totaalInleg = 0.00;
        Double totaalIntrest = 0.00;
        Double totaalTaks = 0.00;

        for (int i = leeftijd, j = LocalDate.now().getYear(), k = 0; i < 65; i++, j++, k++) {

            Double inleg = j > pensioenIndexVastTotEnMet ? pensioenInleg2016 * Math.pow(1 + geschatteIndex, k) : pensioenIndexVastTotEnMet;
            totaalInleg += inleg;
            inlegBedragen.add(Math.round(totaalInleg));

            Double intrest = (totaalInleg + totaalIntrest) * geschatteIntrest;
            totaalIntrest += intrest;
            intrestBedragen.add(Math.round(totaalIntrest));

            Double taks = i == 60 ? (totaalInleg + totaalIntrest) * 0.08 : 0;
            totaalTaks -= taks;
            taksBedragen.add(Math.round(totaalTaks));

            absoluutBedragen.add(Math.round(totaalIntrest + totaalInleg + totaalTaks));
            leeftijden.add(i * 1.00);
        }

        map.put("leeftijden", leeftijden);
        map.put("inlegBedragen", inlegBedragen);
        map.put("intrestBedragen", intrestBedragen);
        map.put("taksBedragen", taksBedragen);
        map.put("absoluutBedragen", absoluutBedragen);
        return map;
    }
}
