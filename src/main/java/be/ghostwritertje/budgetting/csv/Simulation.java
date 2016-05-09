//package be.ghostwritertje.budgetting.csv;
//
///**
// * Created by jorandeboever
// * on 9/05/16.
// */
//public class Simulation {
//
//    /**
//     *
//     * Simulation fields
//     */
//    public enum SimulationField{
//        INITIAL_CAPITAL,
//        MONTHLY_SAVINGS,
//        OFFERING_PERIOD,
//        AVERAGE_ANNUAL_RATE,
//        FINAL_CAPITAL,
//        UNDEFINED
//    }
//
//    private static final int MONTHS_PER_YEAR = 12;
//    private static final double FINANCIAL_PRECISION = 0.00000001; //1.0e-08
//    private static final double FINANCIAL_MAX_ITERATIONS = 128;
//    /**
//     * PV  Initial Capital
//     */
//    public double _pv;
//    /**
//     * PMT  Monthly Savings
//     */
//    public double _pmt;
//    /**
//     * NPR   Offering Period
//     */
//    public float _npr;
//    /**
//     * Rate
//     */
//    public double _rate;
//    /**
//     * FV Final Capital
//     */
//    public double _fv;
//
//    /**
//     * Type (0 or 1)
//     */
//    public int _type;
//
//    public Simulation() {
//
//        _type = 0;
//    }
//
//    /**
//     * Based on http://stackoverflow.com/questions/7064753/problem-with-rate-function
//     * @param npr  NPR
//     * @param pmt  PMT
//     * @param pv    PV
//     * @param fv    FV
//     * @param type  Type
//     * @return the rate
//     */
//    public static double rate(double npr, double pmt, double pv, double fv, int type){
//        double rate = 0.1;
//        double y;
//        double f = 0.0;
//        if (Math.abs(rate) < FINANCIAL_PRECISION) {
//            y = pv * (1 + npr * rate) + pmt * (1 + rate  * type) * npr + fv;
//        } else {
//            f = Math.exp(npr * Math.log(1 + rate));
//            y = pv * f + pmt * (1 / rate + type) * (f - 1) + fv;
//        }
//
//        double y0 = pv + pmt * npr + fv;
//        double y1 = pv * f + pmt * (1 / rate + type) * (f - 1) + fv;
//
//        // find root by secant method
//        int i = 0;
//        double x0 = 0.0;
//        double x1 = rate;
//        while ((Math.abs(y0 - y1) > FINANCIAL_PRECISION) && (i < FINANCIAL_MAX_ITERATIONS)) {
//            rate = (y1 * x0 - y0 * x1) / (y1 - y0);
//            x0 = x1;
//            x1 = rate;
//
//            if (Math.abs(rate) < FINANCIAL_PRECISION) {
//                y = pv * (1 + npr * rate) + pmt * (1 + rate  * type) * npr + fv;
//            } else {
//                f = Math.exp(npr * Math.log(1 + rate));
//                y = pv * f + pmt * (1 / rate + type) * (f - 1) + fv;
//            }
//
//            y0 = y1;
//            y1 = y;
//            i++;
//        }
//        return rate;
//    }
//
//    private double getEffectiveRate() {
//        double rate = _rate + 1;
//        double power = (double) 1/MONTHS_PER_YEAR;
//        double res =( Math.pow(rate, power) - 1 );
//        return res;
//    }
//
//}