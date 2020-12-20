package sample.Brain;

import java.util.ArrayList;
import java.util.List;

/**
 * Наборы параметр-терма-значение
 */
public class Alt {
    private final String paramName;
    private final String termName;
    private Double alfa;
    public static final List<Alt> altList = new ArrayList<>();

    /**
     * Создание пустого варианта
     *
     * @param paramName название параметра
     * @param termName  название термы
     */
    public Alt(String paramName, String termName) {
        this.paramName = paramName;
        this.termName = termName;
        this.alfa=0d;
    }

    /**
     * Создание заполненного варианта
     *
     * @param paramName название параметра
     * @param termName  название термы
     * @param alfa      значение, уровень среза, горизонтальной прямой на графике
     */
    public Alt(String paramName, String termName, Double alfa) {
        this.paramName = paramName;
        this.termName = termName;
        this.alfa = alfa;
    }

    /**
     * Удалить из списка все варианты для данного параметра
     * @param paramName имя параметра
     */
    public static void dropFromListByParamName(String paramName){
        for(int i=altList.size()-1; i>=0; i--){
            Alt a = altList.get(i);
            if(a.getParamName().equals(paramName)){
                altList.remove(a);
            }
        }
    }

    /**
     * Поиск значения для пары параметр-терма
     *
     * @param alt пустой вариант, содержащий нужный параметр и терму
     * @return вариант с нужными параметром, термой и значением либо null
     */
    public static Alt findInList(Alt alt) {
        for (Alt r : altList) {
            if (r.getParamName().equals(alt.getParamName()) && r.getTermName().equals(alt.getTermName())) {
                return r;
            }
        }
        return null;
    }

    /**
     * Поиск значения
     *
     * @return вариант, содержащий значение, либо null
     */
    public Alt findInList() {
        for (Alt r : altList) {
            if (r.getParamName().equals(paramName) && r.getTermName().equals(termName)) {
                return r;
            }
        }
        return null;
    }

    public String getParamName() {
        return paramName;
    }

    public String getTermName() {
        return termName;
    }

    public Double getAlfa() {
        return alfa;
    }

    public void setAlfa(Double alfa) {
        this.alfa = alfa;
    }

    public static List<Alt> getAltList() {
        return altList;
    }

    @Override
    public String toString() {
        return paramName + " -> " + termName + " = " + alfa;
    }
}
