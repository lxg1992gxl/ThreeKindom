package comp1110.ass2.gui;

import comp1110.ass2.WarringStatesGame;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.util.ArrayList;

import static comp1110.ass2.WarringStatesGame.*;
import static comp1110.ass2.WarringStatesGame.getWinnerID;
import static comp1110.ass2.WarringStatesGame.noMoreValidMove;

public class Game extends Application {
    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;

    // Task 9: Implement a basic playable Warring States game in JavaFX
    // Task 11: Allow players of your Warring States game to play against your simple agent
    // Task 12: Integrate a more advanced opponent into your game
    // -- -- generating brilliant move (calling from AIstrategies Class) & using slider for difficulty selection

    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group notion = new Group(); //notions at the bottom of the board
    private final Group end = new Group(); //notions when it comes to the game end
    private final Group player = new Group(); //notions of playerID in the flag shown area (that will never change in the whole game)

    private final Group board = new Group();
    private final Group highlight = new Group();
    private final Group flags = new Group();
    private final FlowPane cardCollectBoard = new FlowPane(0, 10);
    private final Group restart = new Group();

    //groups for showing collected cards
    private final Group scrBD0 = new Group();
    private final Group scrBD1 = new Group();
    private final Group scrBD2 = new Group();
    private final Group scrBD3 = new Group();


    private int numberOfPlayers;
    private int numberOfAI;
    private int numberOfHumans;
    //private boolean advAI;
    private double difficulty = 5.0;

    private String setup = WarringStatesGame.randomSetup();
    //    private String setup = "g0Aa0Bf1Ca1Dc5Ee1Fa4Ge3He2Ia2Jc2Kd0Lf0Mb4Nd4Oa6Pc3Qe0Ra5Sc1Td1Uc4Vb5Wb0Xa7Yf2Zb10a31z92b33b64d35g16b27d28c09";
    private String currentBoard = setup;
    private boolean[] AI = new boolean[4]; //maximum number of players
    private String history = "";
    private int currentPlayer = 0;
    private FXpiece[][] cards = new FXpiece[4][35]; //max number of players, number of character cards

    class FXpiece extends ImageView {
        String id;
        char loc;

        FXpiece(String placement) {
            this.id = placement.substring(0, 2);
            this.loc = placement.charAt(2);
            setImage(new Image(Game.class.getResource(URI_BASE + this.id + ".png").toString()));

            //determines location of the card based on the location char
            int loc = normaliseLoc(placement.charAt(2));
            int w = 100;
            int h = 100;
            int gap = 4;
            setLayoutX(BOARD_WIDTH - ((loc / 6 + 1) * w + (loc / 6 + 1) * gap) - 4); //inverse because starts on right
            setLayoutY(10 + loc % 6 * h + loc % 6 * gap);

            /*
            4 Y S M G A                             30 24 18 12 6 0
            5 Z T N H B                             31 25 19 13 7 1
            6 0 U O I C   --> after normalisation   32 26 20 14 8 2
            7 1 V P J D                             33 27 21 15 9 3
            8 2 W Q K E                             34 28 22 16 10 4
            9 3 X R L F                             35 29 23 17 11 5

             */

            setOnMouseEntered(event -> {
                highlight.getChildren().clear();
                highLightMouse(this.loc, loc);
            });


            setOnMousePressed(event -> {
                highlight.getChildren().clear();
                //System.out.println("current card: " + this.id + this.loc);
                if (this.id.equals("z9") || !WarringStatesGame.isMoveLegal(currentBoard, this.loc)) {
                    notion.getChildren().removeAll(notion.getChildren());
                    Text invalid = new Text("Invalid move! Please choose a new position!");
                    invalid.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 24));
                    invalid.setFill(Color.BLACK);
                    invalid.setLayoutX(410);
                    invalid.setLayoutY(680);
                    notion.getChildren().add(invalid);

                    //invalid move notion sound
                    MediaPlayer invalidSound = new MediaPlayer(new Media(Game.class.getResource(URI_BASE + "invalid.wav").toString()));
                    invalidSound.play();
//                    System.out.println("error");
                } else {
                    history = history + this.loc + "";
                    //  System.out.println("current history: " +history);
                    showCollectedCards();
                    currentBoard = newBoard(setup, history);
                    //System.out.println(currentBoard);
                    makeBoard(); //problem?
                    currentPlayer = (currentPlayer + 1) % numberOfPlayers;
                    showFlags();

                    if (AI[currentPlayer] && !noMoreValidMove(currentBoard)) {
                        autoMove(currentBoard);
                    }

                    //if difficulty 0, call task 10
                    //if difficult >0, task 12
                    //time delay

                    //System.out.println(currentPlayer);
                    if (noMoreValidMove(currentBoard)) {
                        notion.getChildren().removeAll(notion.getChildren());
                        Text end = new Text("No more valid move for Player " + (currentPlayer + 1) + ". Game Ending!");
                        end.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 24));
                        end.setFill(Color.BLACK);
                        end.setLayoutX(405);
                        end.setLayoutY(680);
                        notion.getChildren().add(end);

                        endGame();
                    } else {
                        notion.getChildren().removeAll(notion.getChildren());
                        Text valid = new Text("Valid move. Next comes to Player " + (currentPlayer + 1) + "'s turn!");
                        valid.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 24));
                        valid.setFill(Color.BLACK);
                        valid.setLayoutX(410);
                        valid.setLayoutY(680);
                        notion.getChildren().add(valid);

                        //valid move notion sound for human player
                        MediaPlayer validSound = new MediaPlayer(new Media(Game.class.getResource(URI_BASE + "valid.wav").toString()));
                        validSound.play();
                    }
                }
            });

        }

        @Override
        public String toString() {
            return this.id + this.loc;
        }
    }

    private void updateNotions() {
        notion.getChildren().removeAll(notion.getChildren());
        Text valid = new Text("Valid move. Next comes to Player " + (currentPlayer + 1) + "'s turn!");
        valid.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 24));
        valid.setFill(Color.BLACK);
        valid.setLayoutX(410);
        valid.setLayoutY(680);
        notion.getChildren().add(valid);
    }


    //Generates an automatic move based on a placement string
    private void autoMove(String placement) {
        if (difficulty == 1.0) {
            AIMove(placement);
        } else {
            AdvAIMove(placement);
        }
    }

    //generates a random move
    private void AIMove(String placement) {
        char loc = generateMove(placement);
        history = history + loc + "";
        showCollectedCards();
        currentBoard = newBoard(setup, history);
        makeBoard();
        currentPlayer = (currentPlayer + 1) % numberOfPlayers;
        showFlags();
        updateNotions();
        if (AI[currentPlayer]) { //check whether next player is still computer
            autoMove(currentBoard);
        }

    }

    //Generates a move based on the modified minimax tree
    private void AdvAIMove(String placement) {
        char loc = AIstrategies.bestMove((int) difficulty, currentBoard, currentPlayer, numberOfPlayers, setup, history); //is current player the right parameter here?
        history = history + loc + "";

        showCollectedCards();
        currentBoard = newBoard(setup, history);
        makeBoard();
        currentPlayer = (currentPlayer + 1) % numberOfPlayers;
        showFlags();
        updateNotions();
        if (AI[currentPlayer] && !noMoreValidMove(currentBoard)) {
            autoMove(currentBoard);
        }
    }

    //populates the array to show which players are AI
    private void AIPlayer(int numberOfPlayers, int numberOfHumans) {
        for (int i = 0; i < numberOfPlayers; i++) {
            if (i < numberOfHumans) {
                AI[i] = false;

            } else {
                AI[i] = true;
            }

        }

        //extension --> turn this array into an int array and allow AIs to have differnt difficulty levels

    }


    class Flag extends ImageView {

        Flag(String kingdom, int playerID) {
            Image f = new Image(Game.class.getResource(URI_BASE + kingdom + "flag.png").toString());

            int k = 0; // initialize to represent kingdom "a" (Qin)
            if (kingdom.equals("b")) {
                k = 1;
            } else if (kingdom.equals("c")) {
                k = 2;
            } else if (kingdom.equals("d")) {
                k = 3;
            } else if (kingdom.equals("e")) {
                k = 4;
            } else if (kingdom.equals("f")) {
                k = 5;
            } else if (kingdom.equals("g")) {
                k = 6;
            }

            if (playerID != -1) {
                setImage(f);
                setFitWidth(25);
                setFitHeight(25);
                setLayoutX(80 + 25 * k);
                setLayoutY(574 + 30 * playerID);
            }
        }

    }

    //shows the flags currently controled by each player
    private void showFlags() {
        //clear current flags
        flags.getChildren().removeAll(flags.getChildren());

        //show the flags won by each player

        int[] flag = WarringStatesGame.getFlags(setup, history, numberOfPlayers);

        Flag a = new Flag("a", flag[0]);
        Flag b = new Flag("b", flag[1]);
        Flag c = new Flag("c", flag[2]);
        Flag d = new Flag("d", flag[3]);
        Flag e = new Flag("e", flag[4]);
        Flag f = new Flag("f", flag[5]);
        Flag g = new Flag("g", flag[6]);

        flags.getChildren().add(a);
        flags.getChildren().add(b);
        flags.getChildren().add(c);
        flags.getChildren().add(d);
        flags.getChildren().add(e);
        flags.getChildren().add(f);
        flags.getChildren().add(g);
    }


    private void endGame() {
        highlight.getChildren().clear();

        //notion sound for winner
        MediaPlayer winSound = new MediaPlayer(new Media(Game.class.getResource(URI_BASE + "gameEnd.mp3").toString()));
        winSound.play();

        //indicate the winner at the end of the game
        int winner = getWinnerID(getFlags(setup, history, numberOfPlayers));
        Text win = new Text("Player " + (winner + 1) + " is the winner!!! ");
        win.setFont(Font.font("American Typewriter", FontWeight.EXTRA_BOLD, 68));
        win.setFill(Color.RED);
        win.setLayoutX(BOARD_WIDTH / 2 - 360);
        win.setLayoutY(BOARD_HEIGHT / 2 - 80);
        end.getChildren().add(win);



        Text congratulation = new Text("Congratulations! Y(^o^)Y");
        congratulation.setFont(Font.font("American Typewriter", FontWeight.EXTRA_BOLD, 56));
        congratulation.setFill(Color.MEDIUMVIOLETRED);
        congratulation.setLayoutX(BOARD_WIDTH / 2 - 330);
        congratulation.setLayoutY(BOARD_HEIGHT / 2 + 20);
        end.getChildren().add(congratulation);
        end.toFront();
        root.getChildren().remove(highlight);

    }

    //clears cards from the collected cards pane
    private void clearCards() {
        scrBD0.getChildren().removeAll(scrBD0.getChildren());
        scrBD1.getChildren().removeAll(scrBD1.getChildren());
        scrBD2.getChildren().removeAll(scrBD2.getChildren());
        scrBD3.getChildren().removeAll(scrBD3.getChildren());
    }

    //shows which cards have been collected in the game so far, organised by player
    private void showCollectedCards() {
        //move the supporters to side
        String support = getSupporters(setup, history, numberOfPlayers, currentPlayer);

        //loop through supporter cards to add to collected cards board
        for (int j = 0; j < support.length() / 2; j++) {
            cards[currentPlayer][j] = new FXpiece(support.substring(j * 2, j * 2 + 2) + '/');

            cards[currentPlayer][j].setLayoutX(105 * currentPlayer);
            cards[currentPlayer][j].setLayoutY(20 * j);

            //shrink the cards so will fit in the board
            cards[currentPlayer][j].setFitHeight(70);
            cards[currentPlayer][j].setFitWidth(70);
            switch (currentPlayer) {
                case (0):
                    scrBD0.getChildren().add(cards[currentPlayer][j]);
                    break;
                case (1):
                    scrBD1.getChildren().add(cards[currentPlayer][j]);
                    break;
                case (2):
                    scrBD2.getChildren().add(cards[currentPlayer][j]);
                    break;
                case (3):
                    scrBD3.getChildren().add(cards[currentPlayer][j]);
                    break;
            }
        }
    }

    //creates the pieces of the currentboard
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

    private void highLightMouse(char loc, int normalLoc) {
        //System.out.println(id+loc);
        int w = 100;
        int h = 100;
        int gap = 4;
        if (isMoveLegal(currentBoard, loc)) {
            ImageView greenRec = new ImageView(new Image(Game.class.getResource(URI_BASE + "right.png").toString()));
            greenRec.setLayoutX(BOARD_WIDTH - ((normalLoc / 6 + 1) * w + (normalLoc / 6 + 1) * gap) - 4);
            greenRec.setLayoutY(10 + normalLoc % 6 * h + normalLoc % 6 * gap);
            greenRec.setScaleX(1.05);
            greenRec.setScaleY(1.05);
            highlight.getChildren().add(greenRec);
        } else {
            ImageView redRec = new ImageView(new Image(Game.class.getResource(URI_BASE + "wrong.png").toString()));
            redRec.setLayoutX(BOARD_WIDTH - ((normalLoc / 6 + 1) * w + (normalLoc / 6 + 1) * gap) - 4);
            redRec.setLayoutY(10 + normalLoc % 6 * h + normalLoc % 6 * gap);
            redRec.setScaleX(1.05);
            redRec.setScaleY(1.05);
            highlight.getChildren().add(redRec);
        }
        if (!noMoreValidMove(currentBoard)) {
            board.toFront();
        }
    }

    private void makeRec(int x, int y, int width, int height, int arcw, int arch, Color fill, Color stroke) {
        // draw a rec and add it into root
        Rectangle rectangle = new Rectangle(x, y, width, height);
        rectangle.setArcWidth(arcw);
        rectangle.setArcHeight(arch);
        rectangle.setFill(fill);
        rectangle.setStroke(stroke);
        root.getChildren().add(rectangle);
    }


    //resets all relevant variables and goes back to start screen
    public void restartGame() {
        //new setup
        setup = WarringStatesGame.randomSetup();

        history = "";

        currentBoard = newBoard(setup, history);
        currentPlayer = 0;

        makeBoard(); //clears old board and creates new one
        clearCards();
        showCollectedCards();
        showFlags();

        notion.getChildren().removeAll(notion.getChildren());

        //reset difficulty
        difficulty = 5.0;

        //lapsed code for AI check box
//        //reset ai assignment when restart
//        for(boolean b: AI){
//            b= false;
//        }
//        AIPlayer(numberOfPlayers, numberOfHumans);


    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //setting window starts form here
        Stage page1 = new Stage();
        page1.setTitle("Warring States");

        //build scene
        Pane pane1 = new Pane();
        Scene settingScene = new Scene(pane1, 400, 300);

        //pane1 background
        ImageView start = new ImageView();
        Image st = new Image(Game.class.getResource(URI_BASE + "stBackground.JPG").toString());
        start.setImage(st);
        start.setFitHeight(300);
        start.setFitWidth(400);
        start.setLayoutX(0);
        start.setLayoutY(0);
        pane1.getChildren().add(start);

        //add buttons
        Button exitBtn = new Button("Exit");
        exitBtn.setLayoutX(50);
        exitBtn.setLayoutY(258);
        Button instrBtn = new Button("Instructions");
        instrBtn.setLayoutX(150);
        instrBtn.setLayoutY(258);
        Button gameStartBtn = new Button("Game Start!");
        gameStartBtn.setLayoutX(280);
        gameStartBtn.setLayoutY(258);

        pane1.getChildren().addAll(exitBtn, instrBtn, gameStartBtn);


        //create radio buttons group
        ToggleGroup rb = new ToggleGroup();
        RadioButton twoPlayers = new RadioButton("2-Players game");
        twoPlayers.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 14));
        twoPlayers.setLayoutX(48);
        twoPlayers.setLayoutY(150);
        twoPlayers.setSelected(true);
        twoPlayers.setToggleGroup(rb);
        numberOfPlayers = 2;

        RadioButton threePlayers = new RadioButton("3-Players game");
        threePlayers.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 14));
        threePlayers.setLayoutX(48);
        threePlayers.setLayoutY(185);
        threePlayers.setToggleGroup(rb);

        RadioButton fourPlayers = new RadioButton("4-Players game");
        fourPlayers.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 14));
        fourPlayers.setLayoutX(48);
        fourPlayers.setLayoutY(220);
        fourPlayers.setToggleGroup(rb);

        pane1.getChildren().addAll(twoPlayers, threePlayers, fourPlayers);

        //create an int for store players number
        twoPlayers.setUserData(2);
        threePlayers.setUserData(3);
        fourPlayers.setUserData(4);

        //listener to update and change number of possible robots when number of players changed
        rb.selectedToggleProperty().addListener(
                (ObservableValue<? extends Toggle> ot, Toggle old_toggle, Toggle new_toggle) -> {
                    if (rb.getSelectedToggle() != null) {
                        numberOfPlayers = Integer.parseInt(rb.getSelectedToggle().getUserData().toString());

                        //After change the number of player, renew the list of choice box
                        ArrayList chsb = new ArrayList();
                        String list = "012345";
                        numberOfAI = 0;
                        int i = 0;
                        while (i < numberOfPlayers) {
                            chsb.add(list.substring(i, i + 1));
                            i++;
                        }
                        ChoiceBox choiceBox = new ChoiceBox();
                        choiceBox.setItems(FXCollections.observableArrayList(chsb));
                        choiceBox.setLayoutX(330);
                        choiceBox.setLayoutY(130);
                        choiceBox.getSelectionModel().select(0);
                        pane1.getChildren().add(choiceBox);

                        choiceBox.getSelectionModel().selectedIndexProperty().addListener(((ov, oldv, newv) -> {
                            numberOfAI = newv.intValue();
                        }));
                    }

                });

        //create headline
        Text headline = new Text("Warring States");
        headline.setLayoutX(58);
        headline.setLayoutY(72);
        headline.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, 38));
        headline.setFill(Color.RED);
        InnerShadow is = new InnerShadow();
        is.setOffsetX(4f);
        is.setOffsetY(4f);
        headline.setEffect(is);
        pane1.getChildren().add(headline);


        //create "How many robots \ndo you want to add?"
        Text tAddBot = new Text("How many robots \ndo you want to add?");
        tAddBot.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 14));
        tAddBot.setLayoutX(190);
        tAddBot.setLayoutY(135);
        pane1.getChildren().add(tAddBot);
        Text robotDifficulty = new Text("Robot Difficulty: ");
        robotDifficulty.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 13));
        robotDifficulty.setLayoutX(210);
        robotDifficulty.setLayoutY(190);
        pane1.getChildren().add(robotDifficulty);

        //add choicebox
        ChoiceBox choiceBox = new ChoiceBox();
        choiceBox.setItems(FXCollections.observableArrayList("0", "1"));
        choiceBox.setLayoutX(330);
        choiceBox.setLayoutY(130);
        choiceBox.getSelectionModel().select(0);
        pane1.getChildren().add(choiceBox);

        choiceBox.getSelectionModel().selectedIndexProperty().addListener(((ov, oldv, newv) -> {
            numberOfAI = newv.intValue();
        }));

        //add checkbox
//        CheckBox checkBox = new CheckBox("Advanced AI");
//        checkBox.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 14));
//        checkBox.setLayoutX(200);
//        checkBox.setLayoutY(200);
//        pane1.getChildren().add(checkBox);
//        checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
//            public void changed(ObservableValue<? extends Boolean> ov,
//                                Boolean old_val, Boolean new_val) {
//                if (checkBox.isSelected()) {
//                    advAI = true;
//                } else {
//                    advAI = false;
//                }
//            }
//        });

        //add difficulty slider
        Slider slider = new Slider(1.0, 9.0, 5.0);

        slider.setLayoutX(200);
        slider.setLayoutY(200);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(1f);
        slider.setMinorTickCount(0);
        slider.setSnapToTicks(true);
        //max 9, min 1, snap to whole values

        pane1.getChildren().add(slider);

        difficulty = slider.getValue();
        System.out.println(difficulty);
        // Adding Listener to value property.
        slider.valueProperty().addListener(new ChangeListener<Number>() {

            public void changed(ObservableValue<? extends Number> observable, //
                                Number oldValue, Number newValue) {
                difficulty = (double) newValue;
            }
        });
        //listener code from: https://o7planning.org/en/11083/javafx-slider-tutorial


        //event of buttons
        exitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Event.fireEvent(page1, new WindowEvent(page1, WindowEvent.WINDOW_CLOSE_REQUEST));
            }
        });

        instrBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                page1.hide();
                Stage page2 = new Stage();
                Pane pane2 = new Pane();
                Scene scene = new Scene(pane2, 400, 500);
                page2.setTitle("How to play");
                page2.initStyle(StageStyle.UNDECORATED);
                page2.setScene(scene);

                //pane2 background
                ImageView ins = new ImageView();
                Image insBack = new Image(Game.class.getResource(URI_BASE + "insBackground.JPG").toString());
                ins.setImage(insBack);
                ins.setFitHeight(500);
                ins.setFitWidth(400);
                ins.setLayoutX(0);
                ins.setLayoutY(0);
                pane2.getChildren().add(ins);

                //
                Label instruction = new Label(
                        "Players take turns to collect characters to their side, by clicking the board and " +
                                "moving Zhang Yi around the grid. On their turn, a player chooses a direction " +
                                "(North, East, South, or West) and a kingdom (Qin, Qi, Chu, Zhao, Han, Wei, or Yan)." +
                                "Zhang Yi then moves in the chosen direction to the location of the furthest away character" +
                                " from that kingdom, and collects that character card. If Zhang Yi passes other characters" +
                                " from the same kingdom while moving, he collects those characters as well. Each player may" +
                                " move Zhang Yi only once per turn. \n\nAt the end of their turn, if the player holds an equal or" +
                                " greater number of characters from a kingdom than any of their opponents, the player takes the" +
                                " flag of that kingdom. (If another player already holds the flag, it will change the belonging " +
                                "of this flag). The game ends when Zhang Yi cannot move, that is, when there are no cards in any" +
                                " direction (North, East, South, or West) from Zhang Yi. The player who holds the greatest number " +
                                "of flags at the end of the game wins. If two or more players hold the same number of flags, " +
                                "the player who holds the flag of the kingdom with the greatest number of characters wins.\n\nEnjoy it!"
                );
                instruction.setFont(Font.font("calibri", 14));
                instruction.setWrapText(true);
                instruction.setMaxWidth(scene.getWidth() - 40);
                instruction.setLayoutX(20);
                instruction.setLayoutY(20);

                pane2.getChildren().add(instruction);

                //create buttons
                Button backBtn = new Button("Back");
                backBtn.setLayoutX(305);
                backBtn.setLayoutY(455);
                pane2.getChildren().add(backBtn);
                page2.show();

                //set back button event
                backBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        page2.hide();
                        page1.show();
                    }
                });


            }
        });

        //set start button event
        gameStartBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                numberOfHumans = numberOfPlayers - numberOfAI;

                page1.hide();
                primaryStage.show();
//                        System.out.println(numberOfAI);
//                        System.out.println(advAI);
//                        System.out.println(numberOfPlayers);
//                        System.out.println(currentPlayer);

                //set up the AI players
                AIPlayer(numberOfPlayers, numberOfHumans);

                //add playerID notions for flag area
                Text p0 = new Text("Player 1:");
                p0.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                p0.setFill(Color.BLACK);
                p0.setLayoutX(25);
                p0.setLayoutY(590);
                player.getChildren().add(p0);

                Text p1 = new Text("Player 2:");
                p1.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                p1.setFill(Color.BLACK);
                p1.setLayoutX(25);
                p1.setLayoutY(620);
                player.getChildren().add(p1);

                //set and show card collect board
                if (numberOfPlayers == 2) {
                    cardCollectBoard.getChildren().add(scrBD0);
                    cardCollectBoard.getChildren().add(new ImageView(Game.class.getResource(URI_BASE + "p1.png").toString()));
                    cardCollectBoard.getChildren().add(scrBD1);
                    cardCollectBoard.getChildren().add(new ImageView(Game.class.getResource(URI_BASE + "p2.png").toString()));

                } else if (numberOfPlayers == 3) {
                    cardCollectBoard.getChildren().add(scrBD0);
                    cardCollectBoard.getChildren().add(new ImageView(Game.class.getResource(URI_BASE + "p1.png").toString()));
                    cardCollectBoard.getChildren().add(scrBD1);
                    cardCollectBoard.getChildren().add(new ImageView(Game.class.getResource(URI_BASE + "p2.png").toString()));
                    cardCollectBoard.getChildren().add(scrBD2);
                    cardCollectBoard.getChildren().add(new ImageView(Game.class.getResource(URI_BASE + "p3.png").toString()));

                    Text p2 = new Text("Player 3:");
                    p2.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                    p2.setFill(Color.BLACK);
                    p2.setLayoutX(25);
                    p2.setLayoutY(650);
                    player.getChildren().add(p2);

                } else if (numberOfPlayers == 4) {
                    cardCollectBoard.getChildren().add(scrBD0);
                    cardCollectBoard.getChildren().add(new ImageView(Game.class.getResource(URI_BASE + "p1.png").toString()));
                    cardCollectBoard.getChildren().add(scrBD1);
                    cardCollectBoard.getChildren().add(new ImageView(Game.class.getResource(URI_BASE + "p2.png").toString()));
                    cardCollectBoard.getChildren().add(scrBD2);
                    cardCollectBoard.getChildren().add(new ImageView(Game.class.getResource(URI_BASE + "p3.png").toString()));
                    cardCollectBoard.getChildren().add(scrBD3);
                    cardCollectBoard.getChildren().add(new ImageView(Game.class.getResource(URI_BASE + "p4.png").toString()));

                    Text p2 = new Text("Player 3:");
                    p2.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                    p2.setFill(Color.BLACK);
                    p2.setLayoutX(25);
                    p2.setLayoutY(650);
                    player.getChildren().add(p2);

                    Text p3 = new Text("Player 4:");
                    p3.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                    p3.setFill(Color.BLACK);
                    p3.setLayoutX(25);
                    p3.setLayoutY(680);
                    player.getChildren().add(p3);

                }
            }
        });

        //show scene
        page1.setScene(settingScene);
        page1.show();

        primaryStage.setTitle("Warring States");
        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);
        //main window background
        ImageView gameBack = new ImageView();
        Image mainBackground = new Image(Game.class.getResource(URI_BASE + "gameBackground.JPG").toString());
        gameBack.setImage(mainBackground);
        gameBack.setFitHeight(BOARD_HEIGHT);
        gameBack.setFitWidth(BOARD_WIDTH);
        gameBack.setLayoutX(0);
        gameBack.setLayoutY(0);
        root.getChildren().add(gameBack);

        //
        //setting window ends here

        //draw rectangle
        makeRec(5, 5, 285, 550, 10, 10, null, Color.BLACK); //card collect
        makeRec(5, 570, 285, BOARD_HEIGHT - 575, 10, 10, null, Color.BLACK); //flag
        makeRec(300, 5, 633, 633, 10, 10, null, Color.BLACK); // board
        makeRec(300, 650, 633, BOARD_HEIGHT - 655, 10, 10, null, Color.BLACK); // notion

        makeBoard();
        showFlags();
        showCollectedCards();

        cardCollectBoard.setMaxWidth(270);
        cardCollectBoard.setLayoutX(35);
        cardCollectBoard.setLayoutY(5);
        cardCollectBoard.setPadding(new Insets(5));

        //set reset button
        Button resetBtn = new Button("New game");
        resetBtn.setLayoutX(310);
        resetBtn.setLayoutY(660);

        restart.getChildren().add(resetBtn);

        //add reset event
        resetBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                primaryStage.close();
                page1.show();
                cardCollectBoard.getChildren().clear();
                player.getChildren().clear();
                end.getChildren().clear();
                restartGame();
                root.getChildren().add(highlight);
            }
        });


        root.getChildren().addAll(board, highlight, cardCollectBoard, flags, end, notion, player, restart);

        primaryStage.setScene(scene);
        //move "primaryStage.show" to setting window "GameStart Btn"

    }



}

