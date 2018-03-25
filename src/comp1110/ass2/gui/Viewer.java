package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;



/**
 * A very simple viewer for card layouts in the Warring States game.
 * <p>
 * NOTE: This class is separate from your main game class.  This
 * class does not play a game, it just illustrates various card placements.
 */
public class Viewer extends Application {

    private static final int VIEWER_WIDTH = 933;
    private static final int VIEWER_HEIGHT = 700;

    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group controls = new Group();
    private final StackPane board = new StackPane();
    private final FlowPane flow = new FlowPane(7,7);    //to organize every card in grid


    TextField textField;

    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement A valid placement string
     */

    void makePlacement(String placement) {
        // FIXME Task 4: implement the simple placement viewer

        //remove previously on borad
        flow.getChildren().removeAll(flow.getChildren());
        //split placement string into small group and put it in a[], each String in a[] is like "a1A"
        String a [] = new String[placement.length()/3];
        for(int j=0; j<placement.length()/3;j++){
            a[j] = placement.substring(3*j,3*j+3);
        }

        //print rec
        for (int i = 0; i<placement.length()/3; i++){
            drawCard(a[i].substring(0,2),a[i].substring(2,3),a[i].substring(0,1));
        }


    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label label1 = new Label("Placement:");
        textField = new TextField();
        textField.setPrefWidth(300);
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                makePlacement(textField.getText());
                textField.clear();
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, button);
        hb.setSpacing(10);
        hb.setLayoutX(130);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }


      void drawCard(String name, String position, String kingdom){
        //draw a rectangle card
          Group group = new Group();
         BorderPane textBox = new BorderPane();

         textBox.setPrefWidth(90);
         textBox.setPrefHeight(90);
         textBox.setPadding(new Insets(5));
         textBox.setBottom(new Text(name));
         textBox.setCenter(new Text(kingdom));
         textBox.setRight(new Text(position));

         Rectangle rec = new Rectangle(90,90);
         rec.setArcHeight(5);
         rec.setArcWidth(5);
         rec.setStroke(Color.BLACK);

        //choose rec color by kingdom
        if (kingdom.equals("a")){
            rec.setFill(Color.RED); // color of "Qin"
        }else
        if (kingdom.equals("b")){
            rec.setFill(Color.BLUE); // color of "Qi"
        }else
        if (kingdom.equals("c")){
            rec.setFill(Color.ORANGE); // color of "Chu"
        }else
        if (kingdom.equals("d")){
            rec.setFill(Color.YELLOW); // color of "Zhao"
        }else
        if (kingdom.equals("e")){
            rec.setFill(Color.GREEN); // color of "Han"
        }else
        if (kingdom.equals("f")){
            rec.setFill(Color.GRAY); // color of "Wei"
        }else
        if (kingdom.equals("g")){
            rec.setFill(Color.PINK); // color of "Yan"
        }else
        if (kingdom.equals("z")){
            rec.setFill(Color.BLACK); // color of "Zhang yi" *could be some problem in text color
        }else;

         group.getChildren().addAll(rec,textBox);
         flow.getChildren().add(group);  // (rec + textBox)[group] sent to flow

         if (position.equals("x")){   //change "a1A" to "a1x" in placement means this card was taken
             group.setVisible(false);
         }else;

    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Warring States Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        // drawn a rectangle in background
        Rectangle background = new Rectangle(10,10,600,600);
        background.setFill(Color.WHITE);
        background.setStroke(Color.BLACK);

        board.setPadding(new Insets(10)); //what's on board: background+flow
        flow.setPadding(new Insets(10)); //what's on flow: group + group + group + ...

        board.getChildren().addAll(background,flow);

        //set properties of flowpane, vertical and right to left
        flow.setOrientation(Orientation.VERTICAL);
        flow.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);


        root.getChildren().add(controls); //what's on root: controls + board + ...functions
        root.getChildren().add(board);

        makeControls();
        makePlacement("d1Aa2Bc1De10a1Aa2Bc1xe10a1Af2Bc1De10a1Ag2Bc1De10a1xa2Bc1De10a1Af2Bc1De10a1Aa2xc1De10b1Aa2Bc1De10b1Aa2Ba1Az9T");
        //mimic a parameter


        primaryStage.setScene(scene);
        primaryStage.show();
    }
}


//note re characters- - Qu Yuan 屈原 appears as King Ai, based on the pictures in the setup.png example