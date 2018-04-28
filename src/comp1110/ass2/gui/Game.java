package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Game extends Application {
    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;

    // FIXME Task 9: Implement a basic playable Warring States game in JavaFX

    // FIXME Task 11: Allow players of your Warring States game to play against your simple agent

    // FIXME Task 12: Integrate a more advanced opponent into your game

    private static final int VIEWER_WIDTH = 933;
    private static final int VIEWER_HEIGHT = 700;

    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group controls = new Group();
    private final StackPane board = new StackPane();
    private final FlowPane flow = new FlowPane(7,7);    //to organize every card in grid


    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Warring States");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
        
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
         */

    }
}

