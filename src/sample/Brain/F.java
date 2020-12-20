package sample.Brain;

public class F {
    public static Double t(Double a, Double b) {
        return a < b ? a : b;
    }

    public static Double tk(Double a, Double b) {
        return a > b ? a : b;
    }

    public static Double round(Double x, int places) {
        if (places < 0) return null;

        long factor = (long) Math.pow(10, places);
        return (double) Math.round(x * factor) / factor;
    }
}

