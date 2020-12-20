package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import sample.Brain.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Gui {

    public void start(GridPane pane) {
        // Считываем правила и точки для графиков
        Init.init();
        // Входные параметры
        List<Param> paramsIn = Init.getParamsIn();
        // Выходные параметры
        List<Param> paramsOut = Init.getParamsOut();
        // Для каждого входного парамаетра..
        for (int i = 0; i < paramsIn.size(); i++) {
            // ..рисуем график и плашку ввода значения
            addInGraphs(pane, paramsIn.get(i), i);
        }
        // Добавляем внизу кнопку рассчёта
        Button calculate = new Button();
        pane.add(calculate, 0, pane.getRowCount(), 2, 1);
        calculate.setMaxWidth(Double.MAX_VALUE);
        calculate.setText("Рассчитать");

        int row = pane.getRowCount()+1;
        // Поле для правил
        Label rulesLabel = new Label("Правила:");
        rulesLabel.setVisible(false);
        pane.add(rulesLabel, 0, row, 2, 1);

        List<Node> out = new ArrayList<>();

        // Добавляем действие при нажатии на кнопку
        calculate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // Получаем список активных правил
                List<Rule> rules = General.calculateRules();
                // Отрисовать список активных правил
                StringBuilder rulesText = new StringBuilder("Правила:\n");
                for(Rule r: rules){
                    rulesText.append(r.toString()).append("\n");
                }
                rulesLabel.setText(rulesText.toString());
                rulesLabel.setVisible(!rules.isEmpty());

                pane.getChildren().removeAll(out);

                int number = row+1;
                for(Param o: paramsOut) {
                    // Получаем список преобразованных (обрезанных) терм
                    List<Term> slicedTerms = General.calculateTermsForOutParam(o, rules);
                    // Отрисовать новые термы
                    out.add(addOutGraphs(pane, slicedTerms, o.getName(), number++));

                    // Получаем объединённую терму
                    Term resultTerm = Intersections.termsCombining(slicedTerms);
                    // Отрисовать итоговую терму
                    out.add(addOutGraphs(pane, Collections.singletonList(resultTerm), "", number++));

                    // Поиск центра масс итогового терма
                    Double S = Intersections.centroid(resultTerm);
                    Label integr = new Label("Итоговый результат: "+F.round(S,5).toString());
                    integr.setAlignment(Pos.BOTTOM_CENTER);
                    out.add(integr);
                    pane.add(integr, 0, number++);
                    System.out.println(S);

                }
            }
        });
    }

    private void addInGraphs(GridPane pane, Param param, int row) {
        NumberAxis X = new NumberAxis();
        X.setForceZeroInRange(false);
        NumberAxis Y = new NumberAxis();
        Y.setAutoRanging(false);
        Y.setTickUnit(0.05);
        Y.setUpperBound(1.1);
        AreaChart<Number, Number> chart = new AreaChart<>(X, Y);
        drawParam(param.getTerms(), param.getName(), chart, null, null);
        pane.add(chart, 0, row);

        GridPane gPane = new GridPane();
        pane.add(gPane, 1, row);

        Label invite = new Label(param.getName()+":");
        gPane.add(invite, 0,0);
        Label termsStat = new Label();
        gPane.add(termsStat, 0, 3);

        TextField field = new TextField();
        gPane.add(field, 0,1);
        gPane.setAlignment(Pos.CENTER);
        field.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                Double d;
                try {
                    d = Double.parseDouble(field.getText());
                    List<Alt> alts = param.getAlfa(d);
                    StringBuilder res = new StringBuilder();
                    for(Alt a: alts){
                        res.append(a.getTermName()).append(" ->").append(F.round(a.getAlfa(),5)).append("\n");
                    }
                    termsStat.setText(res.toString());
                }catch (Exception e){
                    d=null;
                    termsStat.setText("");
                }
                drawParam(param.getTerms(), param.getName(), chart, d, null);
            }
        });
    }

    private Node addOutGraphs(GridPane pane, List<Term> terms, String name, int row){
        NumberAxis X = new NumberAxis();
        X.setForceZeroInRange(false);
        NumberAxis Y = new NumberAxis();
        Y.setAutoRanging(false);
        Y.setTickUnit(0.05);
        Y.setUpperBound(1.1);
        AreaChart<Number, Number> chart = new AreaChart<>(X, Y);
        pane.add(chart, 0, row);

        drawParam(terms, name, chart,null, null);
        return chart;

    }
    private void drawParam(List<Term> terms, String name, AreaChart<Number, Number> chart, Double xLine, Double alfa) {
        chart.getData().clear();
        chart.setTitle(name);
        for (Term t : terms) {
            XYChart.Series series = new XYChart.Series();
            chart.getData().add(series);
            series.setName(t.getName());
            ObservableList datas = series.getData();
            for (Point p : t.getPoints()) {
                datas.add(new XYChart.Data(p.getX(), p.getY()));
            }
        }
        if(xLine!=null){
            XYChart.Series series = new XYChart.Series();
            chart.getData().add(series);
            series.setName("Выбранное значение");
            ObservableList datas = series.getData();
            datas.add(new XYChart.Data(xLine, 0));
            datas.add(new XYChart.Data(xLine, 1.1));
        }
        if(alfa!=null){
            Double xmin=terms.get(0).getMinX();
            Double xmax=terms.get(0).getMaxX();
            for(int i = 1; i< terms.size(); i++){
                Term t = terms.get(i);
                xmin = xmin<t.getMinX()?xmin:t.getMinX();
                xmax = xmax>t.getMaxX()?xmax:t.getMaxX();
            }
            XYChart.Series series = new XYChart.Series();
            series.setName("alfa");
            chart.getData().add(series);
            ObservableList datas = series.getData();
            datas.add(new XYChart.Data(xmin, alfa));
            datas.add(new XYChart.Data(xmax, alfa));
        }
    }
}
