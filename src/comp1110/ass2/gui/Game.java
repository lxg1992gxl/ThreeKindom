package comp1110.ass2.gui;

import comp1110.ass2.WarringStatesGame;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

import java.util.Random;

import static comp1110.ass2.WarringStatesGame.*;
import static comp1110.ass2.WarringStatesGame.getWinnerID;
import static comp1110.ass2.WarringStatesGame.noMoreValidMove;

public class Game extends Application {
    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;

    // FIXME Task 9: Implement a basic playable Warring States game in JavaFX

    private static final int VIEWER_WIDTH = 933;
    private static final int VIEWER_HEIGHT = 700;

    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group controls = new Group();
    private final StackPane scores = new StackPane();
    private final Group board = new Group();
    private final FlowPane cardCollectBoard = new FlowPane(10,10);
    private final Group scrBD0 = new Group();
    private final Group scrBD1 = new Group();
    private final Group scrBD2 = new Group();
    private final Group scrBD3 = new Group();


    private String setup = WarringStatesGame.randomSetup();
    private String currentBoard = setup;
    private int players = 4;
    private int AIs = 1;
    private String history = "";
    private int currentPlayer = 0;
    private FXpiece[][] cards = new FXpiece[players][35]; //number of players, number of character cards

    class FXpiece extends ImageView {
        String id;
        char loc;

        FXpiece(String placement) {
            this.id = placement.substring(0, 2);
            this.loc = placement.charAt(2);
            setImage(new Image(Game.class.getResource(URI_BASE + this.id + ".png").toString())); //problem?
            //add where placement?

            //determines location of the card based on the location char
            int loc = normaliseLoc(placement.charAt(2));
            int w = 100;
            int h = 100;
            int gap = 4;
            setLayoutX(BOARD_WIDTH - ((loc / 6 + 1) * w + (loc / 6 + 1) * gap)); //inverse because starts on right
            setLayoutY(20 + loc % 6 * h + loc % 6 * gap);

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
                System.out.println("current card: " + this.id + this.loc);
                if (this.id != "z9" & WarringStatesGame.isMoveLegal(currentBoard, this.loc)) {
                    history = history + this.loc + "";
                    //  System.out.println("current history: " +history);
                    showCollectedCards();
                    currentBoard = newBoard(setup, history);
                    //System.out.println(currentBoard);
                    makeBoard(); //problem?
                    currentPlayer = (currentPlayer + 1) % players;

                    //TODO if current player = AI, make next move based on AIstrategies here

                    //System.out.println(currentPlayer);
                    if (noMoreValidMove(currentBoard)) {
                        System.out.println("finished!"); //working
                        endGame();

                    }
                }
            });
            //width and height is currently 100

        }

        @Override
        public String toString() {
            return this.id + this.loc;
        }
    }

    //TODO create a method which will display the flags currently controlled by each player
    private void showFlags() {
        //show the flags won by each player
        getFlags(setup, history, players);

        //images for flags in assets folder?

        //where should flags be shown

    }

    //TODO create a method which will give instructions for when the game ends
    private void endGame() {

        System.out.println("end game");
        int winner = getWinnerID(getFlags(setup, history, players));
        //don't allow to continue playing when finished- boolean playable?
    }

    private void showCollectedCards() {
        //FIXME clear current cards collected to avoid double up

        //move the supporters to side
        String support = getSupporters(setup, history, players, currentPlayer);

        //System.out.println("supporters: "+ support);
        for (int j = 0; j < support.length() / 2; j++) {
            cards[currentPlayer][j] = new FXpiece(support.substring(j * 2, j * 2 + 2) + '/');
            //System.out.println("current substring: "+cards[currentPlayer][j]);

            cards[currentPlayer][j].setLayoutX(105 * currentPlayer); //if clearing at beginning of method, need to get supporters for all players
            //if more than 2 players, show below instead?
            cards[currentPlayer][j].setLayoutY(20 * j);
            switch (currentPlayer){
                case(0):scrBD0.getChildren().add(cards[currentPlayer][j]);
                    break;
                case(1):scrBD1.getChildren().add(cards[currentPlayer][j]);
                    break;
                case(2):scrBD2.getChildren().add(cards[currentPlayer][j]);
                    break;
                case(3):scrBD3.getChildren().add(cards[currentPlayer][j]);
                    break;
            }
        }

        //give new setup string to show only remaining cards on the board
        //collect flags

    }

    private void makeScores() {
        Label l[] = new Label[players];
        for (int i = 0; i < players; i++) {
            l[i] = new Label("Player " + i + 1);
            l[i].setLayoutX(200); //doesn't seem to be working?
            l[i].setLayoutY(((BOARD_HEIGHT / players) * i) + 10);
            scores.getChildren().add(l[i]);
        }
        root.getChildren().add(scores);
    }

    private void makeBoard() {
        //clear to avoid double up
        board.getChildren().removeAll(board.getChildren());

        //create all board pieces
        FXpiece[] b = new FXpiece[currentBoard.length() / 3];
        for (int j = 0; j < currentBoard.length() / 3; j++) {
            b[j] = new FXpiece(currentBoard.substring(j * 3, j * 3 + 3)); //problem?
            board.getChildren().add(b[j]);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Warring States");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        makeBoard();
        makeScores();
        showCollectedCards();

        cardCollectBoard.setMaxWidth(250);
        cardCollectBoard.setLayoutX(10);
        cardCollectBoard.setLayoutY(30);
        cardCollectBoard.getChildren().add(scrBD0);
        cardCollectBoard.getChildren().add(scrBD1);
        cardCollectBoard.getChildren().add(scrBD2);
        cardCollectBoard.getChildren().add(scrBD3);

        root.getChildren().addAll(board,cardCollectBoard);

        primaryStage.setScene(scene);
        primaryStage.show();

    }


    // FIXME Task 11: Allow players of your Warring States game to play against your simple agent
    //randomly generate move (task 10), make it


    // FIXME Task 12: Integrate a more advanced opponent into your game
    //check who winning after move?
    //recursive check for who winning after that up to AI's next move?
    //other ideas


}

