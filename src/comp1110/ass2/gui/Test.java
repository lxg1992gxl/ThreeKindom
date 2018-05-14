package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Group g = new Group();
        Scene s = new Scene(g);

        Button b = new Button("Yes");
        b.setLayoutX(100);
        b.setLayoutY(100);

        g.getChildren().add(b);

        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.hide();
                Test.launch();


            }
        });

        primaryStage.setScene(s);
        primaryStage.show();
    }
}
