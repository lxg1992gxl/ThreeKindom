package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class T1 extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Choose the number of players!");
        //build scene
        Pane playerNum = new Pane();
        Scene scene = new Scene(playerNum,350,300);

        //create buttons
        Button exitBtn = new Button("Exit");
        exitBtn.setLayoutX(50);
        exitBtn.setLayoutY(258);
        Button nextBtn = new Button("Next");
        nextBtn.setLayoutX(220);
        nextBtn.setLayoutY(258);
        playerNum.getChildren().addAll(exitBtn,nextBtn);

        //event of buttons
        exitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Event.fireEvent(primaryStage, new WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST ));
            }
        });

        nextBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                primaryStage.hide();
                Stage AIorNot = new Stage();
                Pane AI = new Pane();
                Scene scene = new Scene(AI,350,300);
                AIorNot.setTitle("Add Robot");
                AIorNot.setScene(scene);
                //create headline
                Text headline = new Text("How many robot you wang to add?");
                headline.setLayoutX(100);
                headline.setLayoutY(120);
                AI.getChildren().add(headline);

//                String array = "01234";
//                array.substring(0, numOfPlayer);
                //choicebox
                ChoiceBox choiceBox = new ChoiceBox(FXCollections.observableArrayList("0","1","2","3"));
                choiceBox.setLayoutX(100);
                choiceBox.setLayoutY(150);
                AI.getChildren().add(choiceBox);

                //checkbox
                CheckBox checkBox = new CheckBox("Advanced AI");
                checkBox.setLayoutX(200);
                checkBox.setLayoutY(150);
                AI.getChildren().add(checkBox);

                //create buttons
                Button backBtn = new Button("Back");
                backBtn.setLayoutX(50);
                backBtn.setLayoutY(258);
                Button gameStartBtn = new Button("Game Start!");
                gameStartBtn.setLayoutX(220);
                gameStartBtn.setLayoutY(258);
                AI.getChildren().addAll(backBtn,gameStartBtn);

                AIorNot.show();

                //set back button event
                backBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        AIorNot.hide();
                        primaryStage.show();
                    }
                });
            }
        });



        //create radio buttons group
        ToggleGroup rb = new ToggleGroup();
        RadioButton twoPlayers = new RadioButton("2-Players");
        twoPlayers.setLayoutX(115);
        twoPlayers.setLayoutY(145);
        twoPlayers.setSelected(true);
        twoPlayers.setToggleGroup(rb);
        RadioButton threePlayers = new RadioButton("3-Players");
        threePlayers.setLayoutX(115);
        threePlayers.setLayoutY(180);
        threePlayers.setToggleGroup(rb);
        RadioButton fourPlayers = new RadioButton("4-Players");
        fourPlayers.setLayoutX(115);
        fourPlayers.setLayoutY(215);
        fourPlayers.setToggleGroup(rb);
        playerNum.getChildren().addAll(twoPlayers,threePlayers,fourPlayers);

        //create an int for store players number
        twoPlayers.setUserData(2);
        threePlayers.setUserData(3);
        fourPlayers.setUserData(4);
        rb.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (rb.getSelectedToggle() != null) {
                    if (rb.equals(twoPlayers)){
                        int numOfPlayer = 2;
                    }else if (rb.equals(threePlayers)){
                        int numOfPlayer = 3;
                    }else if (rb.equals(fourPlayers)){
                        int numOfPlayer = 4;
                    }
                }
            }
        });

        //create headline
        Text headline = new Text("Game of Seven Kingdoms");
        headline.setLayoutX(100);
        headline.setLayoutY(70);
        playerNum.getChildren().add(headline);

        //show scene
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}
