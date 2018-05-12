package comp1110.ass2.gui;

import comp1110.ass2.WarringStatesGame;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

import java.util.Random;

import static comp1110.ass2.WarringStatesGame.getSupporters;
import static comp1110.ass2.WarringStatesGame.normaliseLoc;

public class Game extends Application {
    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;

    // FIXME Task 9: Implement a basic playable Warring States game in JavaFX

    // FIXME Task 11: Allow players of your Warring States game to play against your simple agent
    //randomly generate move (task 10), make it


    // FIXME Task 12: Integrate a more advanced opponent into your game
    //check who winning after move?
    //recursive check for who winning after that up to AI's next move?
    //other ideas

    private static final int VIEWER_WIDTH = 933;
    private static final int VIEWER_HEIGHT = 700;

    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group controls = new Group();
    private final StackPane scores = new StackPane();
    private final FlowPane flow = new FlowPane(7,7);    //to organize every card in grid

    private String setup = "g0Aa0Bf1Ca1Dc5Ee1Fa4Ge3He2Ia2Jc2Kd0Lf0Mb4Nd4Oa6Pc3Qe0Ra5Sc1Td1Uc4Vb5Wb0Xa7Yf2Zb10a31z92b33b64d35g16b27d28c09";
    private int players = 2;
    private int AIs = 1;
    private String history = "";
    private int currentPlayer = 0;
    private FXpiece[][] cards = new FXpiece[players][35]; //number of players, number of character cards

    class FXpiece extends ImageView{
        String id;
        char loc;

        FXpiece (String placement){
            this.id = placement.substring(0,2);
            this.loc = placement.charAt(2);
            setImage(new Image (Game.class.getResource(URI_BASE+this.id+".png").toString()));
            //add where placement?

            //determines location of the card based on the location char
            int loc = normaliseLoc(placement.charAt(2));
            int w = 100;
            int h = 100;
            int gap = 10;
            setLayoutX(BOARD_WIDTH-((loc/6+1)*w +(loc/6+1)*gap)); //inverse because starts on right
            setLayoutY(loc%6*h+loc%6*gap);

            /*
            4 Y S M G A                             30 24 18 12 6 0
            5 Z T N H B                             31 25 19 13 7 1
            6 0 U O I C   --> after normalisation   32 26 20 14 8 2
            7 1 V P J D                             33 27 21 15 9 3
            8 2 W Q K E                             34 28 22 16 10 4
            9 3 X R L F                             35 29 23 17 11 5

             */

            //on mouse click, hover over acceptable
//
//            this.setScaleX(1.1);
//            this.setScaleY(1.1);
//            this.toFront();

            setOnMousePressed(event -> {
                System.out.println("current card: "+ this.id+this.loc);
                if(WarringStatesGame.isMoveLegal(setup, this.loc)){
                    history=history+this.loc+"";
                    System.out.println("current history: " +history);
                    showCollectedCards();
                };
            });
            //width and height is currently 100
        }
    }


    private void showCollectedCards(){
        //clear current cards collected to avoid double up
        //get moves so far

        //stringbuilder?

        //System.out.println("yes"); //call make move method
//        history = history+"hi";
//        System.out.println(history); //Not adding to history appropriately

        //move the supporters to side
        String support = getSupporters(setup, history, players, currentPlayer);

        System.out.println("supporters: "+ support);
        for(int j=0;j<support.length()/2; j++){
            cards[currentPlayer][j] = new FXpiece (support.substring(j*2, j*2+2)+'?');

            //supporters[j] = new FXpiece(support.substring(j*2, j*2+2)+"A"); //FIXMEtake only relevant cards
            System.out.println("current substring: "+cards[currentPlayer][j]);

//            supporters[j].setLayoutX(20);
//            supporters[j].setLayoutY(20*j);
              root.getChildren().add(cards[currentPlayer][j]);
        }
        //give new setup string to show only remaining cards on the board
        //collect flags

    }


    private void makeControls(){
        Button start = new Button ("Restart game");
        Slider players = new Slider (2, 4, 2);

        //difficulty

        controls.getChildren().add(start);
        controls.getChildren().add(players);
    }


    private void makeScores(){
        Label l [] = new Label[players];
        for (int i= 0; i<players; i++){
            l[i] = new Label ("Player "+ i+1);
            l[i].setLayoutX(200); //doesn't seem to be working?
            l[i].setLayoutY(((BOARD_HEIGHT/players)*i)+10);
            scores.getChildren().add(l[i]);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Seven Kingdoms Game");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        FXpiece[] board = new FXpiece[setup.length()/3];
        for(int j=0;j<setup.length()/3; j++){
            board[j] = new FXpiece(setup.substring(j*3, j*3+3));
            root.getChildren().add(board[j]);
        }

        makeScores();
        showCollectedCards();
        root.getChildren().add(scores);
        primaryStage.setScene(scene);
        primaryStage.show();


    }
}

