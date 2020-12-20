package sample.Brain;

/**
 * Точка в двумерном пространстве
 */
public class Point {
    private Double x;
    private Double y;

    public Point(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return x.toString() + " x " + y.toString();
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }
}
