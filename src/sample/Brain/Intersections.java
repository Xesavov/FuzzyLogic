package sample.Brain;

import java.util.ArrayList;
import java.util.List;

public class Intersections {
    /**
     * Поиск точки пересечения прямых
     *
     * @param s1 точка начала первого отрезка
     * @param e1 точка конца первого отрезка
     * @param a1 параметр a для первой линии
     * @param b1 параметр b для первой линии
     * @param s2 точка начала второго отрезка
     * @param e2 точка окончания второго отрезка
     * @param a2 коэффициент a второй прямой
     * @param b2 коэффициент b второй прямой
     * @return объект класса Point
     */
    public static Point lines(Point s1, Point e1,
                              Double a1, Double b1,
                              Point s2, Point e2,
                              Double a2, Double b2) {
        if (a2.equals(a1)) {
            if (b1.equals(b2) && s1.getX() < s2.getX() && e1.getX() < e2.getX() && e1.getX() > s2.getX()) {
                return s2;
            }
            return null;
        }
        Double eps = 0.0001;
        Double x0 = (b2 - b1) / (a1 - a2);
        if (x0 + eps < s1.getX() || x0 - eps > e1.getX() || x0 + eps < s2.getX() || x0 - eps > e2.getX()) {
            return null;
        }
        Double y0 = a1 * x0 + b1;
        return new Point(x0, y0);
    }

    /**
     * Поиск точек пересечения термы и горизонтальной прямой
     *
     * @param t    - терма (набор связанных отрезков)
     * @param alfa - уровень горизонтальной прямой
     * @return список точек пересечения
     */
    public static List<Point> slice(Term t, Double alfa) {
        List<Point> slice = new ArrayList<>();
        for (int i = 0; i < t.getaKoef().size(); i++) {
            Point s = t.getPoints().get(i);
            Point e = t.getPoints().get(i + 1);
            slice.add(new Point(s.getX(), s.getY() < alfa ? s.getY() : alfa));
            Point p = lines(s, e, 0d, alfa, s, e, t.getaKoef().get(i), t.getbKoef().get(i));
            if (p != null) {
                slice.add(p);
            }
        }
        Point lastPoint = t.getPoints().get(t.getPoints().size() - 1);
        slice.add(new Point(lastPoint.getX(), lastPoint.getY() < alfa ? lastPoint.getY() : alfa));
        for (int i = slice.size() - 2; i > 0; i--) {
            if (slice.get(i).getY().equals(slice.get(i - 1).getY()) &&
                    slice.get(i).getY().equals(slice.get(i + 1).getY())) {
                slice.remove(i);
            }
        }
        return slice;
    }

    /**
     * Поиск точки пересечения терм
     *
     * @param t1 левая терма
     * @param t2 правая терма
     * @return набор точек для построения общей термы
     */
    public static Point termsIntersection(Term t1, Term t2) {
        for (int i = 0; i < t1.getaKoef().size(); i++) {
            for (int j = 0; j < t2.getaKoef().size(); j++) {
                Point p = lines(t1.getPoints().get(i), t1.getPoints().get(i + 1),
                        t1.getaKoef().get(i), t1.getbKoef().get(i),
                        t2.getPoints().get(j), t2.getPoints().get(j + 1),
                        t2.getaKoef().get(j), t2.getbKoef().get(j));
                if (p != null) {
                    return p;
                }
            }
        }
        return null;
    }

    /**
     * Объединние двух терм
     *
     * @param t1 левая терма
     * @param t2 правая терма
     * @return общая терма
     */
    public static Term termsCombining(Term t1, Term t2) {
        if (t1.getMinX() > t2.getMinX()) {
            Term temp = t1;
            t1 = t2;
            t2 = temp;
        }
        Point p0 = termsIntersection(t1, t2);
        List<Point> points = new ArrayList<>();
        if (p0 == null) {
            points.addAll(t1.getPoints());
            points.addAll(t2.getPoints());
        } else {
            for (int i = 0; /*i<t1.getPoints().size() && */t1.getPoints().get(i).getX() < F.round(p0.getX(),5); i++) {
                points.add(t1.getPoints().get(i));
            }
            points.add(p0);
            for (int i = 0; i < t2.getPoints().size(); i++) {
                if (t2.getPoints().get(i).getX() <= F.round(p0.getX(),5)) {
                    continue;
                }
                points.add(t2.getPoints().get(i));
            }
        }
        return new Term("Объединение", points);
    }

    /**
     * Объединение множества терм
     *
     * @param terms список терм
     * @return результирующая терма
     */
    public static Term termsCombining(List<Term> terms) {
        if (terms == null || terms.size() == 0)
            return null;
        Term t = terms.get(0);
        for (int i = 1; i < terms.size(); i++) {
            t = termsCombining(t, terms.get(i));
        }
        return t;
    }

    /**
     * Поиск площади термы
     *
     * @param t терма
     * @return интеграл
     */
    public static Double centroid(Term t) {
        // Находим шаг dx
        final double dx = (t.getMaxX() - t.getMinX()) / 1000;
        // Сумма для числителя
        double sum1 = 0d;
        // Сумма для знаменателя
        double sum2 = 0d;
        // Для каждой линии в списке
        for (int i = 0; i < t.getaKoef().size(); i++) {
            // Получаем коэффициент a линии
            Double a = t.getaKoef().get(i);
            // Получаем коэффициент b линии
            Double b = t.getbKoef().get(i);
            // Проходимся по линии с шагом dx
            for (Double x = t.getPoints().get(i).getX(); x < t.getPoints().get(i + 1).getX(); x += dx) {
                // Добавляем в сумму значение функции в точке, умноженную на x
                sum1 += (a * x + b) * x;
                // Добавляем в сумму значение функции в точке
                sum2 += a * x + b;
            }
        }
        // Делим числитель на знаменатель
        return sum1 / sum2;
    }
}
