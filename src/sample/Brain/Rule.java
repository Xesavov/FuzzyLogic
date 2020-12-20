package sample.Brain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Правило для формирования выходного параметра
 */
public class Rule {
    private final List<Alt> in;
    private final Alt out;

    /**
     * Создание правила
     *
     * @param out выходной вариант
     * @param in  список входных вариантов
     */
    public Rule(Alt out, List<Alt> in) {
        this.out = out;
        this.in = in;
    }

    /**
     * Создание правила
     *
     * @param out   выходной параметр
     * @param input перечисление входных параметров
     */
    public Rule(Alt out, Alt... input) {
        this.out = out;
        in = new ArrayList<>(Arrays.asList(input));
    }

    public List<Alt> getIn() {
        return in;
    }

    public Alt getOut() {
        return out;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        boolean f = false;
        for (Alt a : in) {
            if (f)
                res.append(" + ");
            else
                f = true;
            res.append("(").append(a.getParamName()).append(": ").append(a.getTermName()).append(")");
        }
        res.append(" = ").append("(").append(out.getParamName()).append(": ").append(out.getTermName()).append(")");
        res.append(" = ").append(F.round(out.getAlfa(), 5).toString());
        return res.toString();
    }
}
