package sample.Brain;

import java.util.ArrayList;
import java.util.List;

public class Term {
    private final String name;
    private final List<Point> points;
    private final List<Double> aKoef = new ArrayList<>();
    private final List<Double> bKoef = new ArrayList<>();

    public Term(String name, List<Point> points) {
        this.name = name;
        this.points = points;
        for (int i = 0; i < points.size() - 1; i++) {
            Double x1 = points.get(i).getX();
            Double x2 = points.get(i + 1).getX();
            Double y1 = points.get(i).getY();
            Double y2 = points.get(i + 1).getY();
            aKoef.add((y2 - y1) / (x2 - x1));
            bKoef.add(y1 - x1 / (x2 - x1) * (y2 - y1));
        }
    }

    public Term(String name, Double... coords) {
        this.name = name;
        points = new ArrayList<>();
        for (int i = 0; i < coords.length - 3; i += 2) {
            Double x1 = coords[i];
            Double y1 = coords[i + 1];
            Double x2 = coords[i + 2];
            Double y2 = coords[i + 3];
            points.add(new Point(x1, y1));
            aKoef.add((y2 - y1) / (x2 - x1));
            bKoef.add(y1 - x1 / (x2 - x1) * (y2 - y1));
        }
        points.add(new Point(coords[coords.length - 2], coords[coords.length - 1]));
    }

    public String getName() {
        return name;
    }

    public Double getAlfa(Double x) {
        for (int i = 0; i < aKoef.size(); i++) {
            if (x < points.get(i).getX() || x > points.get(i + 1).getX()) {
                continue;
            }
            return x * aKoef.get(i) + bKoef.get(i);
        }
        return null;
    }

    public List<Point> getPoints() {
        return points;
    }

    public List<Double> getaKoef() {
        return aKoef;
    }

    public List<Double> getbKoef() {
        return bKoef;
    }

    public Double getMaxX() {
        Double max = points.get(0).getX();
        for (int i = 1; i < points.size(); i++) {
            max = max > points.get(i).getX() ? max : points.get(i).getX();
        }
        return max;
    }

    public Double getMinX() {
        Double min = points.get(0).getX();
        for (int i = 1; i < points.size(); i++) {
            min = min < points.get(i).getX() ? min : points.get(i).getX();
        }
        return min;
    }

    @Override
    public String toString() {
        String res = name + ": ";
        for (Point p : points) {
            res += "(" + p.toString() + "), ";
        }
        return res;
    }
}
