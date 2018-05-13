package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
        // Task 4: implement the simple placement viewer

        //remove previously on borad
        flow.getChildren().removeAll(flow.getChildren());
        //split placement string into small group and put it in a[], each String in a[] is like "a1A"
        String a [] = new String[placement.length()/3];
        for(int j=0; j<placement.length()/3;j++){
            a[j] = placement.substring(3*j,3*j+3);
        }

        //print rec
        for (int i = 0; i<placement.length()/3; i++){
            drawCard(a[i].substring(1,2),a[i].substring(2,3),a[i].substring(0,1));
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
         Group oneCard = new Group();
         Pane textBox = new Pane();

         textBox.setPrefWidth(90);
         textBox.setPrefHeight(90);
         textBox.setPadding(new Insets(5));
//         textBox.setBottom(new Text(name));
//         textBox.setCenter(new Text(kingdom));
//         textBox.setRight(new Text(position));

          //decide which card image should be placed  *begin*
          //by using image directly, I leave codes that drawing rectangle in comments down here
          if (kingdom.equals("a")){
              if (name.equals("0")) {
                  Image a0 = new Image("img/a0.png");
                  ImageView imga0 = new ImageView(a0);
                  textBox.getChildren().add(imga0);
              }else if (name.equals("1")) {
                  Image a1 = new Image("img/a1.png");
                  ImageView imga1 = new ImageView(a1);
                  textBox.getChildren().add(imga1);
              }else if (name.equals("2")) {
                  Image a2 = new Image("img/a2.png");
                  ImageView imga2 = new ImageView(a2);
                  textBox.getChildren().add(imga2);
              }else if (name.equals("3")) {
                  Image a3 = new Image("img/a3.png");
                  ImageView imga3 = new ImageView(a3);
                  textBox.getChildren().add(imga3);
              }else if (name.equals("4")) {
                  Image a4 = new Image("img/a4.png");
                  ImageView imga4 = new ImageView(a4);
                  textBox.getChildren().add(imga4);
              }else if (name.equals("5")) {
                  Image a5 = new Image("img/a5.png");
                  ImageView imga5 = new ImageView(a5);
                  textBox.getChildren().add(imga5);
              }else if (name.equals("6")) {
                  Image a6 = new Image("img/a6.png");
                  ImageView imga6 = new ImageView(a6);
                  textBox.getChildren().add(imga6);
              }else if (name.equals("7")) {
                  Image a7 = new Image("img/a7.png");
                  ImageView imga7 = new ImageView(a7);
                  textBox.getChildren().add(imga7);
              }else;
          }else if (kingdom.equals("b")){
              if (name.equals("0")) {
                  Image b0 = new Image("img/b0.png");
                  ImageView imgb0 = new ImageView(b0);
                  textBox.getChildren().add(imgb0);
              }else if (name.equals("1")) {
                  Image b1 = new Image("img/b1.png");
                  ImageView imgb1 = new ImageView(b1);
                  textBox.getChildren().add(imgb1);
              }else if (name.equals("2")) {
                  Image b2 = new Image("img/b2.png");
                  ImageView imgb2 = new ImageView(b2);
                  textBox.getChildren().add(imgb2);
              }else if (name.equals("3")) {
                  Image b3 = new Image("img/b3.png");
                  ImageView imgb3 = new ImageView(b3);
                  textBox.getChildren().add(imgb3);
              }else if (name.equals("4")) {
                  Image b4 = new Image("img/b4.png");
                  ImageView imgb4 = new ImageView(b4);
                  textBox.getChildren().add(imgb4);
              }else if (name.equals("5")) {
                  Image b5 = new Image("img/b5.png");
                  ImageView imgb5 = new ImageView(b5);
                  textBox.getChildren().add(imgb5);
              }else if (name.equals("6")) {
                  Image b6 = new Image("img/b6.png");
                  ImageView imgb6 = new ImageView(b6);
                  textBox.getChildren().add(imgb6);
              }else;
          }else if (kingdom.equals("c")){
              if (name.equals("0")) {
                  Image c0 = new Image("img/c0.png");
                  ImageView imgc0 = new ImageView(c0);
                  textBox.getChildren().add(imgc0);
              }else if (name.equals("1")) {
                  Image c1 = new Image("img/c1.png");
                  ImageView imgc1 = new ImageView(c1);
                  textBox.getChildren().add(imgc1);
              }else if (name.equals("2")) {
                  Image c2 = new Image("img/c2.png");
                  ImageView imgc2 = new ImageView(c2);
                  textBox.getChildren().add(imgc2);
              }else if (name.equals("3")) {
                  Image c3 = new Image("img/c3.png");
                  ImageView imgc3 = new ImageView(c3);
                  textBox.getChildren().add(imgc3);
              }else if (name.equals("4")) {
                  Image c4 = new Image("img/c4.png");
                  ImageView imgc4 = new ImageView(c4);
                  textBox.getChildren().add(imgc4);
              }else if (name.equals("5")) {
                  Image c5 = new Image("img/c5.png");
                  ImageView imgc5 = new ImageView(c5);
                  textBox.getChildren().add(imgc5);
              }else;
          }else if (kingdom.equals("d")){
              if (name.equals("0")) {
                  Image d0 = new Image("img/d0.png");
                  ImageView imgd0 = new ImageView(d0);
                  textBox.getChildren().add(imgd0);
              }else if (name.equals("1")) {
                  Image d1 = new Image("img/d1.png");
                  ImageView imgd1 = new ImageView(d1);
                  textBox.getChildren().add(imgd1);
              }else if (name.equals("2")) {
                  Image d2 = new Image("img/d2.png");
                  ImageView imgd2 = new ImageView(d2);
                  textBox.getChildren().add(imgd2);
              }else if (name.equals("3")) {
                  Image d3 = new Image("img/d3.png");
                  ImageView imgd3 = new ImageView(d3);
                  textBox.getChildren().add(imgd3);
              }else if (name.equals("4")) {
                  Image d4 = new Image("img/d4.png");
                  ImageView imgd4 = new ImageView(d4);
                  textBox.getChildren().add(imgd4);
              }else;
          }else if (kingdom.equals("e")){
              if (name.equals("0")) {
                  Image e0 = new Image("img/e0.png");
                  ImageView imge0 = new ImageView(e0);
                  textBox.getChildren().add(imge0);
              }else if (name.equals("1")) {
                  Image e1 = new Image("img/e1.png");
                  ImageView imge1 = new ImageView(e1);
                  textBox.getChildren().add(imge1);
              }else if (name.equals("2")) {
                  Image e2 = new Image("img/e2.png");
                  ImageView imge2 = new ImageView(e2);
                  textBox.getChildren().add(imge2);
              }else if (name.equals("3")) {
                  Image e3 = new Image("img/e3.png");
                  ImageView imge3 = new ImageView(e3);
                  textBox.getChildren().add(imge3);
              }else;

          }else if (kingdom.equals("f")){
              if (name.equals("0")) {
                  Image f0 = new Image("img/f0.png");
                  ImageView imgf0 = new ImageView(f0);
                  textBox.getChildren().add(imgf0);
              }else if (name.equals("1")) {
                  Image f1 = new Image("img/f1.png");
                  ImageView imgf1 = new ImageView(f1);
                  textBox.getChildren().add(imgf1);
              }else if (name.equals("2")) {
                  Image f2 = new Image("img/f2.png");
                  ImageView imgf2 = new ImageView(f2);
                  textBox.getChildren().add(imgf2);
              }else;

          }else if (kingdom.equals("g")){
              if (name.equals("0")) {
                  Image g0 = new Image("img/g0.png");
                  ImageView imgg0 = new ImageView(g0);
                  textBox.getChildren().add(imgg0);
              }else if (name.equals("1")) {
                  Image g1 = new Image("img/g1.png");
                  ImageView imgg1 = new ImageView(g1);
                  textBox.getChildren().add(imgg1);
              }else;
          }else if (kingdom.equals("z")){
              if (name.equals("9")) {
                  Image z9 = new Image("img/z9.png");
                  ImageView imgz9 = new ImageView(z9);
                  textBox.getChildren().add(imgz9);
              }else;
          }
          //decide which card image should be placed   *end*

/*      //draw rectangle and fill in color  *abandoned codes*

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
*/
           oneCard.getChildren().add(textBox);
           flow.getChildren().add(oneCard);  // (rec + textBox)[oneCard] sent to flow

          //judge if card is removed
         if (position.equals("x")){   //change "a1A" to "a1x" in placement means this card was taken
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
        makePlacement("g0Aa0Bf1Ca1Dc5Ee1Fa4Ge3He2Ia2Jc2Kd0Lf0Mb4Nd4Oa6Pc3Qe0Ra5Sc1Td1Uc4Vb5Wb0Xa7Yf2Zb10a31z92b33b64d35g16b27d28c09");
        //mimic a parameter


        primaryStage.setScene(scene);
        primaryStage.show();
    }
}


//note re characters- - Qu Yuan 屈原 appears as King Ai, based on the pictures in the setup.png example