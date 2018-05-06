package comp1110.ass2.gui;

import comp1110.ass2.WarringStatesGame;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

import java.util.Random;

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
    private final StackPane board = new StackPane();
    private final FlowPane flow = new FlowPane(7,7);    //to organize every card in grid

    private String setup = "g0Aa0Bf1Ca1Dc5Ee1Fa4Ge3He2Ia2Jc2Kd0Lf0Mb4Nd4Oa6Pc3Qe0Ra5Sc1Td1Uc4Vb5Wb0Xa7Yf2Zb10a31z92b33b64d35g16b27d28c09";

    class FXpiece extends ImageView{
        String id;

        FXpiece (String placement){
            this.id = placement.substring(0,2);
            setImage(new Image (Game.class.getResource(URI_BASE+this.id+".png").toString()));
            //add where placement?

            //determines location of the card based on the location char
            int loc = normaliseLoc(placement.charAt(2));
            int w = 100;
            int h= 100;
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
            setOnMousePressed(event -> {      // mouse press indicates begin of drag
                System.out.println(this.id);
                if(WarringStatesGame.isMoveLegal(setup, placement.charAt(2))){
                    System.out.println("yes"); //call make move method
                };
            });

            //width and height is currently 100

        }
    }

    public void makeMove(String placement, char location){
        //move ZY to the location
        //take card from location and any on way off board/placement
        //add those cards to the players set
        //check if any valid moves left (use HelperMethods)
    }

    public void endGame(){
        
    }


    public void startGame(){
        //creates a random setup
        //random card from the available, goes into A...Z....9

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //start game
        //      create setup
        //number of players

        primaryStage.setTitle("Warring States");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        FXpiece[] board = new FXpiece[setup.length()/3];
        for(int j=0;j<setup.length()/3; j++){
            board[j] = new FXpiece(setup.substring(j*3, j*3+3));
            root.getChildren().add(board[j]);
        }


        primaryStage.setScene(scene);
        primaryStage.show();
        //task 9
        /** create board that shows current game state
         * choose number of players
         * display what cards have collected and kingdom flags
         * allow to click on a card to attempt move
         *      if move accepted, make move and take card/cards (make sure to take other cards as relevant)
         *      if not accepted, error message
         *  after move next player (indicate on screen which player's turn it is)
         *      check for any moves available (iHelper)
         *  if no moves left, check win condition, display winner
         */

        //makemove
        /**
         *
         */

    }
}

