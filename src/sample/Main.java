package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("НЛ Захаров");

        GridPane gridPane = new GridPane();
        Gui gui = new Gui();
        gui.start(gridPane);
        ScrollPane scrollPane = new ScrollPane(gridPane);

        scrollPane.setMaxHeight(800);
        Scene scene = new Scene(scrollPane/*, 800, 600*/);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
