package sample.Brain;

import java.util.ArrayList;
import java.util.List;

public class Init {
    private static List<Param> paramsIn;
    private static List<Param> paramsOut;
    private static List<Rule> rules;

    public static void init() {
        //initParamsTriangle();
        initParamsTrapetz();
        initRules();
        boolean f = false;
        if (f) {
            for (Param p : paramsIn) {
                paramToT(p);
            }
            for (Param p : paramsOut) {
                paramToT(p);
            }
        }
    }

    public static void initParamsTriangle() {
    paramsIn = new ArrayList<>();
    paramsOut = new ArrayList<>();
    Param i1 = new Param("Текущая ЗП (тыс.руб)",
            new Term("Низкая", 0d, 0d, 20d, 1d, 50d, 0d),
            new Term("Средняя", 20d, 0d, 50d, 1d, 80d, 0d),
            new Term("Высокая", 50d, 0d, 100d, 1d, 150d, 0d)
    );
        paramsIn.add(i1);
    Param i2 = new Param("Стаж работы (лет)",
            new Term("Малый", 0d, 0d, 1.5d, 1d, 3d, 0d),
            new Term("Средний", 1d, 0d, 4d, 1d, 5d, 0d),
            new Term("Большой", 3d, 0d, 6d, 1d, 10d, 0d)
    );
        paramsIn.add(i2);
    Param i3 = new Param("Платеж по кредиту (тыс.руб / мес)",
            new Term("Низкий", 0d, 0d, 10d, 1d, 20d, 0d),
            new Term("Средний", 15d, 0d, 30d, 1d, 45d, 0d),
            new Term("Высокий", 40d, 0d, 60d, 1d, 80d, 0d)
    );
        paramsIn.add(i3);
    Param i4 = new Param("Срок кредита (лет)",
            new Term("Краткосрочный", 0d, 0d, 1d, 1d, 3d, 0d),
            new Term("Средний", 1d, 0d, 4d, 1d, 5d, 0d),
            new Term("Длительный", 3d, 0d, 6d, 1d, 10d, 0d)
    );
        paramsIn.add(i4);
    Param o1 = new Param("Результат кредитного скоринга(рейтинг заемщика)",
            new Term("Низкий - КРЕДИТ НЕ ДАВАТЬ", 0d, 0d, 3d, 1d, 5d, 0d),
            new Term("Средний - Можно дать денег", 3d, 0d, 5d, 1d, 7d, 0d),
            new Term("Высокий - Точно вернет!", 5d, 0d, 7d, 1d, 10d, 0d)
    );
        paramsOut.add(o1);
    }

    public static void paramToT(Param param) {
        for (int j = 0; j < param.getTerms().size(); j++) {
            Term t = param.getTerms().get(j);
            List<Point> points = new ArrayList<>(t.getPoints());
            for (int i = points.size() - 3; i > 0; i--) {
                Point s = points.get(i);
                Point e = points.get(i + 1);
                if (s.getY().equals(e.getY())) {
                    Point n = new Point((s.getX() + e.getX()) / 2, s.getY());
                    points.set(i, n);
                    points.remove(i + 1);
                }
            }
            if (points.get(0).getY().equals(points.get(1).getY())) {
                points.remove(1);
            }
            int last = points.size() - 1;
            if (points.get(last).getY().equals(points.get(last - 1).getY())) {
                points.remove(last - 1);
            }
            param.getTerms().set(j, new Term(t.getName(), points));
        }
    }

    public static void initParamsTrapetz() {
        paramsIn = new ArrayList<>();
        paramsOut = new ArrayList<>();
        Param i1 = new Param("Текуща ЗП",
                new Term("Низкая", 0d, 1d, 0.3d, 1d, 0.5d, 0d),
                new Term("Средняя", 0.3d, 0d, 0.6d, 1d, 1.2d, 1d, 1.5d, 0d),
                new Term("Высокая", 1d, 0d, 1.5d, 1d, 2d, 1d, 3d, 0d)
        );
        paramsIn.add(i1);
        Param i2 = new Param("Стаж работы",
                new Term("Малый", 10d, 1d, 19d, 1d, 22d, 0d),
                new Term("Средний", 18d, 0d, 20d, 1d, 22d, 1d, 24d, 0d),
                new Term("Большой", 22d, 0d, 24d, 1d, 30d, 1d)
        );
        paramsIn.add(i2);
        Param i3 = new Param("Платеж по кредиту",
                new Term("Низкий", 0d, 1d, 1d, 1d, 3d, 0d),
                new Term("Средний", 1d, 0d, 3d, 1d, 8d, 1d, 10d, 0d),
                new Term("Высокий", 8d, 0d, 10d, 1d, 14d, 1d, 16d, 0d)
        );
        paramsIn.add(i3);
        Param i4 = new Param("Срок кредита",
                new Term("Краткосрочный", 0d, 1d, 3d, 1d, 5d, 0d),
                new Term("Средний", 4d, 0d, 5d, 1d, 8d, 1d, 10d, 0d),
                new Term("Длительный", 8d, 0d, 10d, 1d, 14d, 1d, 20d, 0d)
        );
        paramsIn.add(i4);
        Param o1 = new Param("Рейтинг заемщика",
                new Term("Низкий - КРЕДИТ НЕ ДАВАТЬ", 20d, 1d, 30d, 1d, 40d, 0d),
                new Term("Средний - Можно дать денег", 30d, 0d, 35d, 1d, 45d, 1d, 50d, 0d),
                new Term("Высокий - Точно вернет!", 45d, 0d, 50d, 1d, 60d, 1d, 70d, 0d)
        );
        paramsOut.add(o1);
    }

    public static void initRules() {
        rules = new ArrayList<>();
        rules.add(new Rule(
                new Alt("Теплозащита", "Отсутствует"),
                new Alt("Посещаемость", "Почти отсутствует"),
                new Alt("Бюджет", "Скромный")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Отсутствует"),
                new Alt("Отапливаемость", "Сильная"),
                new Alt("Посещаемость", "Редкая"),
                new Alt("Бюджет", "Скромный")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Отсутствует"),
                new Alt("Отапливаемость", "Умеренная"),
                new Alt("Посещаемость", "Редкая"),
                new Alt("Бюджет", "Скромный")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Слабая"),
                new Alt("Отапливаемость", "Слабая"),
                new Alt("Посещаемость", "Редкая"),
                new Alt("Бюджет", "Скромный")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Отсутствует"),
                new Alt("Отапливаемость", "Сильная"),
                new Alt("Посещаемость", "Умеренная"),
                new Alt("Бюджет", "Скромный")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Слабая"),
                new Alt("Отапливаемость", "Умеренная"),
                new Alt("Посещаемость", "Умеренная"),
                new Alt("Бюджет", "Скромный")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Слабая"),
                new Alt("Отапливаемость", "Слабая"),
                new Alt("Посещаемость", "Умеренная"),
                new Alt("Бюджет", "Скромный")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Слабая"),
                new Alt("Посещаемость", "Частая"),
                new Alt("Бюджет", "Скромный")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Отсутствует"),
                new Alt("Посещаемость", "Почти отсутствует"),
                new Alt("Бюджет", "Умеренный")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Отсутствует"),
                new Alt("Размер проёма", "Очень маленький"),
                new Alt("Отапливаемость", "Умеренная"),
                new Alt("Посещаемость", "Редкая"),
                new Alt("Бюджет", "Умеренный")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Отсутствует"),
                new Alt("Размер проёма", "Маленький"),
                new Alt("Отапливаемость", "Умеренная"),
                new Alt("Посещаемость", "Редкая"),
                new Alt("Бюджет", "Умеренный")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Отсутствует"),
                new Alt("Размер проёма", "Средний"),
                new Alt("Отапливаемость", "Умеренная"),
                new Alt("Посещаемость", "Редкая"),
                new Alt("Бюджет", "Умеренный")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Слабая"),
                new Alt("Размер проёма", "Большой"),
                new Alt("Отапливаемость", "Умеренная"),
                new Alt("Посещаемость", "Редкая"),
                new Alt("Бюджет", "Умеренный")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Слабая"),
                new Alt("Размер проёма", "Огромный"),
                new Alt("Отапливаемость", "Умеренная"),
                new Alt("Посещаемость", "Редкая"),
                new Alt("Бюджет", "Умеренный")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Слабая"),
                new Alt("Отапливаемость", "Сильная"),
                new Alt("Посещаемость", "Умеренная"),
                new Alt("Бюджет", "Умеренный")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Слабая"),
                new Alt("Отапливаемость", "Умеренная"),
                new Alt("Посещаемость", "Умеренная"),
                new Alt("Бюджет", "Умеренный")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Сильная"),
                new Alt("Отапливаемость", "Слабая"),
                new Alt("Посещаемость", "Умеренная"),
                new Alt("Бюджет", "Умеренный")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Слабая"),
                new Alt("Отапливаемость", "Сильная"),
                new Alt("Посещаемость", "Частая"),
                new Alt("Бюджет", "Умеренный")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Слабая"),
                new Alt("Отапливаемость", "Умеренная"),
                new Alt("Посещаемость", "Частая"),
                new Alt("Бюджет", "Умеренный")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Сильная"),
                new Alt("Отапливаемость", "Слабая"),
                new Alt("Посещаемость", "Частая"),
                new Alt("Бюджет", "Умеренный")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Слабая"),
                new Alt("Посещаемость", "Почти отсутствует"),
                new Alt("Бюджет", "Широкий")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Слабая"),
                new Alt("Посещаемость", "Редкая"),
                new Alt("Бюджет", "Широкий")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Слабая"),
                new Alt("Отапливаемость", "Сильная"),
                new Alt("Посещаемость", "Умеренная"),
                new Alt("Бюджет", "Широкий")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Слабая"),
                new Alt("Отапливаемость", "Умеренная"),
                new Alt("Посещаемость", "Умеренная"),
                new Alt("Бюджет", "Широкий")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Сильная"),
                new Alt("Отапливаемость", "Слабая"),
                new Alt("Посещаемость", "Умеренная"),
                new Alt("Бюджет", "Широкий")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Слабая"),
                new Alt("Размер проёма", "Очень маленький"),
                new Alt("Отапливаемость", "Сильная"),
                new Alt("Посещаемость", "Частая"),
                new Alt("Бюджет", "Широкий")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Слабая"),
                new Alt("Размер проёма", "Маленький"),
                new Alt("Отапливаемость", "Сильная"),
                new Alt("Посещаемость", "Частая"),
                new Alt("Бюджет", "Широкий")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Слабая"),
                new Alt("Размер проёма", "Средний"),
                new Alt("Отапливаемость", "Сильная"),
                new Alt("Посещаемость", "Частая"),
                new Alt("Бюджет", "Широкий")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Сильная"),
                new Alt("Размер проёма", "Большой"),
                new Alt("Отапливаемость", "Сильная"),
                new Alt("Посещаемость", "Частая"),
                new Alt("Бюджет", "Широкий")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Сильная"),
                new Alt("Размер проёма", "Огромный"),
                new Alt("Отапливаемость", "Сильная"),
                new Alt("Посещаемость", "Частая"),
                new Alt("Бюджет", "Широкий")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Слабая"),
                new Alt("Размер проёма", "Очень маленький"),
                new Alt("Отапливаемость", "Умеренная"),
                new Alt("Посещаемость", "Частая"),
                new Alt("Бюджет", "Широкий")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Слабая"),
                new Alt("Размер проёма", "Маленький"),
                new Alt("Отапливаемость", "Умеренная"),
                new Alt("Посещаемость", "Частая"),
                new Alt("Бюджет", "Широкий")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Сильная"),
                new Alt("Размер проёма", "Средний"),
                new Alt("Отапливаемость", "Умеренная"),
                new Alt("Посещаемость", "Частая"),
                new Alt("Бюджет", "Широкий")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Сильная"),
                new Alt("Размер проёма", "Большой"),
                new Alt("Отапливаемость", "Умеренная"),
                new Alt("Посещаемость", "Частая"),
                new Alt("Бюджет", "Широкий")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Сильная"),
                new Alt("Размер проёма", "Огромный"),
                new Alt("Отапливаемость", "Умеренная"),
                new Alt("Посещаемость", "Частая"),
                new Alt("Бюджет", "Широкий")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Сильная"),
                new Alt("Отапливаемость", "Слабая"),
                new Alt("Посещаемость", "Частая"),
                new Alt("Бюджет", "Широкий")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Слабая"),
                new Alt("Посещаемость", "Почти отсутствует"),
                new Alt("Бюджет", "Неограниченный")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Слабая"),
                new Alt("Посещаемость", "Редкая"),
                new Alt("Бюджет", "Неограниченный")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Сильная"),
                new Alt("Посещаемость", "Умеренная"),
                new Alt("Бюджет", "Неограниченный")
        ));

        rules.add(new Rule(
                new Alt("Теплозащита", "Сильная"),
                new Alt("Посещаемость", "Частая"),
                new Alt("Бюджет", "Неограниченный")
        ));
        System.out.println("Все правила:");
        for (Rule r : rules) {
            System.out.println(r.toString());
        }
    }

    public static List<Param> getParamsIn() {
        return paramsIn;
    }

    public static List<Param> getParamsOut() {
        return paramsOut;
    }

    public static List<Rule> getRules() {
        return rules;
    }
}
