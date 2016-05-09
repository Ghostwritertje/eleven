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
        List<Number> taxReductieBedragen = new ArrayList<>();
        List<Number> intrestBedragen = new ArrayList<>();
        List<Number> taksBedragen = new ArrayList<>();
        List<Number> absoluutBedragen = new ArrayList<>();

        Double totaalInleg = 0.00;
        Double totaalIntrest = 0.00;
        Double totaalTaks = 0.00;

        for (int i = leeftijd, j = LocalDate.now().getYear(), k = 0; i < 65; i++, j++, k++) {

            Double intrest = (totaalInleg + totaalIntrest) * geschatteIntrest;
            totaalIntrest += intrest;
            intrestBedragen.add(Math.round(totaalIntrest));

            Double inleg = j > pensioenIndexVastTotEnMet ? pensioenInleg2016 * Math.pow(1 + geschatteIndex, k) : pensioenInleg2016;
            totaalInleg += inleg;
            inlegBedragen.add(Math.round(totaalInleg*0.7));
            taxReductieBedragen.add(Math.round(totaalInleg*0.3));

            Double taks = i == 60 ? (totaalInleg + totaalIntrest) * 0.08 : 0;
            totaalTaks -= taks;
            taksBedragen.add(Math.round(totaalTaks));

            absoluutBedragen.add(Math.round(totaalIntrest + totaalInleg + totaalTaks));
            leeftijden.add(i * 1.00);
        }

        map.put("leeftijden", leeftijden);
        map.put("inlegBedragen", inlegBedragen);
        map.put("taxReductieBedragen", taxReductieBedragen);

        map.put("intrestBedragen", intrestBedragen);
        map.put("taksBedragen", taksBedragen);
        map.put("absoluutBedragen", absoluutBedragen);
        return map;
    }

    public double berekenGeannualiseerdRendement(double geschatteIntrest, int leeftijd){
        Map<String, List<Number>> map = this.berekenPensioenFonds(geschatteIntrest, leeftijd, 0);
        List<Number> absoluteBedragen = map.get("absoluutBedragen");
        Number eindBedrag = absoluteBedragen.get(absoluteBedragen.size() - 1);
        return this.rate((65-leeftijd), -658, 0, Double.parseDouble(eindBedrag.toString()), 0)*100;
    }


    private double rate(double npr, double pmt, double pv, double fv, int type){
        double rate = 0.1;
        double y;
        double f = 0.0;
        double FINANCIAL_PRECISION = 0.00000001;
        if (Math.abs(rate) < FINANCIAL_PRECISION) {
            y = pv * (1 + npr * rate) + pmt * (1 + rate  * type) * npr + fv;
        } else {
            f = Math.exp(npr * Math.log(1 + rate));
            y = pv * f + pmt * (1 / rate + type) * (f - 1) + fv;
        }

        double y0 = pv + pmt * npr + fv;
        double y1 = pv * f + pmt * (1 / rate + type) * (f - 1) + fv;

        // find root by secant method
        int i = 0;
        double x0 = 0.0;
        double x1 = rate;
        double FINANCIAL_MAX_ITERATIONS = 128;
        while ((Math.abs(y0 - y1) > FINANCIAL_PRECISION) && (i < FINANCIAL_MAX_ITERATIONS)) {
            rate = (y1 * x0 - y0 * x1) / (y1 - y0);
            x0 = x1;
            x1 = rate;

            if (Math.abs(rate) < FINANCIAL_PRECISION) {
                y = pv * (1 + npr * rate) + pmt * (1 + rate  * type) * npr + fv;
            } else {
                f = Math.exp(npr * Math.log(1 + rate));
                y = pv * f + pmt * (1 / rate + type) * (f - 1) + fv;
            }

            y0 = y1;
            y1 = y;
            i++;
        }
        return rate;
    }
}
