package sample.Brain;

import java.util.ArrayList;
import java.util.List;

public class General {
    /**
     * Заполнение правил на основе заведённых вариантов
     *
     * @return Список правил, где результирующая alfa>0
     */
    public static List<Rule> calculateRules() {
        // Получение всех правил
        List<Rule> rules = Init.getRules();
        // Новый список правил
        List<Rule> newList = new ArrayList<>();
        // Для каждого правила..
        for (Rule r : rules) {
            // Инициируем минимальную alfa
            Double alfa = 1d;
            // Для каждого варианта термы в правиле..
            for (int i = 0; i < r.getIn().size(); i++) {
                // Ищем этот вариант в списке заполненных
                Alt a = r.getIn().get(i).findInList();
                // Если найден..
                if (a != null) {
                    // Устанавливаем значение в правило
                    r.getIn().set(i, a);
                    // Берём т-норму(минимум) из минимальной alfa и alfa варианта
                    alfa = F.t(alfa, a.getAlfa());
                } else {
                    // иначе устанавливаем правилу alfa = 0
                    r.getIn().get(i).setAlfa(0d);
                    alfa = 0d;
                }
            }
            // Устанавливаем выходному параметру alfa минимальную
            r.getOut().setAlfa(alfa);
            // Если alfa>0, то добавляем это правило в итоговый лист
            if (alfa > 0) {
                newList.add(r);
            }
        }
        return newList;
    }

    /**
     * Вычисление терм для выходного параметра
     *
     * @param o выходной параметр
     * @return список терм параметра
     */
    public static List<Term> calculateTermsForOutParam(Param o, List<Rule> rules) {
        // Получение списка правил
        if (rules == null) {
            rules = Init.getRules();
        }
        // Новый список для итоговых терм
        List<Term> terms = new ArrayList<>();
        // Обрабатываем каждую терму входного параметра
        for (Term t : o.getTerms()) {
            // Устанавливаем максимальное alfa в минимум
            Double alfa = 0d;
            // Для каждого правила из списка..
            for (Rule r : rules) {
                // Получаем вариант выходного параметра
                Alt a = r.getOut();
                // Если вариант подходит для этого параметра и этой термы..
                if (a.getParamName().equals(o.getName()) && a.getTermName().equals(t.getName())) {
                    // Берём т-конорму (максимум) для максимального alfa и alfa из правила
                    alfa = F.tk(alfa, a.getAlfa());
                }
            }
            // Добавляем срезанную по alfa терму в итоговый список
            terms.add(new Term(t.getName(), Intersections.slice(t, alfa)));
        }
        return terms;
    }

}
