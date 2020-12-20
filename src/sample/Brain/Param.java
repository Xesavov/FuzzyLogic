package sample.Brain;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Param {
    private final String name;
    private final List<Term> terms;

    public Param(String name, List<Term> terms) {
        this.name = name;
        this.terms = terms;
    }

    public Param(String name, Term...terms) {
        this.name = name;
        this.terms = new ArrayList<>(Arrays.asList(terms));
    }

    public List<Alt> getAlfa(Double x){
        List<Alt> result = new ArrayList<>();
        for(Term t:terms){
            Double alfa = t.getAlfa(x);
            if(alfa!=null){
                result.add(new Alt(name, t.getName(), alfa));
            }
        }
        Alt.dropFromListByParamName(name);
        Alt.getAltList().addAll(result);
        return result;
    }

    public String getName() {
        return name;
    }

    public List<Term> getTerms() {
        return terms;
    }
}

