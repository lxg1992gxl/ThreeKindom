package comp1110.ass2.gui;

import comp1110.ass2.WarringStatesGame;

import java.util.ArrayList;
import java.util.List;

import static comp1110.ass2.WarringStatesGame.*;

public class AIstrategies {
    // Task 12: Integrate a more advanced opponent into your game

    // collect all valid-move cards
    public static List<String> allValidMoves(String currentBoard) {
        List<String> allValidMoves = new ArrayList<>();

        char[] place = currentBoard.toCharArray();

        // find ZhangYi's location in the currentBoard
//        char zyloc = currentBoard.charAt(currentBoard.indexOf('z') + 2);
        char zyloc = ' '; //initialize zy location
        for (int p = 2; zyloc == ' ' && p < currentBoard.length(); p = p + 3) {
            if (place[p - 2] == 'z') {
                zyloc = place[p];
            }
        }

        // find all valid cards in the "currentBoard" sequence (collect a card in a 3-character form)
        for (int i = 2; i < currentBoard.length(); i = i + 3) {
            char loc = place[i];
            if (loc != zyloc && WarringStatesGame.isMoveLegal(currentBoard, loc)) {
                char[] a = new char[3];
                a[0] = place[i - 2];
                a[1] = place[i - 1];
                a[2] = loc;
                String mov = new String(a);
                allValidMoves.add(mov);
            }
        }
        return allValidMoves;
    }


    // At this stage, previousState just has 3 String representing one state (1st: moveSequence; 2nd: board; 3rd: mark)
    public static List<String> allNextStepPossibilities(List<String> previousState, int initialPlayer, int checkedPlayer, int numPlayers, String setup, String historyMove) {
        List<String> possibleState = new ArrayList<>();

        // get the completed-structured list with all possible next-step boards, without correct updated marks
        // (all marks are just previous marks)
        String previousBoard = previousState.get(1);
        List<String> allValidMoves = allValidMoves(previousBoard);
        char[] bd = previousBoard.toCharArray();

        for (String move : allValidMoves) {

            // find ZhangYi's location for previous board
//            char zyloc = previousBoard.charAt(previousBoard.indexOf('z') + 2);
            char zyloc = ' '; //initialize zy location
            int zy = 2;
            while (zyloc == ' ' && zy < previousBoard.length()) {
                if (bd[zy - 2] == 'z') {
                    zyloc = bd[zy];
                }
                zy = zy + 3;
            }
            if (zy != 2) {
                zy = zy - 3;
            }

            int ZY = normaliseLoc(zyloc);

            // add new move sequence for one possibleState
            char mov = (move.toCharArray())[2];
            String newMoveSequence = previousState.get(0) + mov;
            possibleState.add(newMoveSequence);

//            System.out.println(previousState.get(2));
            int totalMark = Integer.parseInt(previousState.get(2));

            int newMark = 0;

            // the normalised form for destination location
            int D = normaliseLoc(mov);

            // find the destination location on previousBoard board
            int m = 2;
            for (int p = 0; p != -1 && m < previousBoard.length(); m = m + 3) {
                if (bd[m] == mov) {
                    p = -1;
                }
            }
            if (m != 2) {
                m = m - 3;
            }

            //find the corresponding card at destination position
            char country = bd[m - 2];

            for (int k = 2; k < previousBoard.length(); k = k + 3) {
                int K = normaliseLoc(bd[k]);
                // first check whether the position is a valid move
                // the card belongs to same country with the destination
                if (isMoveLegal(previousBoard, bd[k])) {
                    if (bd[k - 2] == country) {
                        // the card is in the same row or column with ZhangYi and destination position
                        if ((sameRow(mov, bd[k]) && sameRow(zyloc, bd[k])) || (sameCol(mov, bd[k]) && sameCol(zyloc, bd[k]))) {
                            // the card is between ZhangYi and the destination position
                            if ((D < K && K < ZY) || (ZY < K && K < D)) {
                                // whenever found a card between ZY and the destination position, "add mark" by 1
                                newMark = newMark + 1;
                                bd[k] = '/';
                                bd[k - 1] = '/';
                                bd[k - 2] = '/';
                            }
                        }
                    }
                }
            }

            // move ZY to his new position
            bd[m - 2] = 'z';
            bd[m - 1] = '9';

            // set previous Zhang Yi's position to empty
            bd[zy - 2] = '/';
            bd[zy - 1] = '/';
            bd[zy] = '/';

            // update our new board
            String newBoard = "";
            for (int i = 0; i < bd.length; i++) {
                if (bd[i] != '/') {
                    char[] a = new char[]{bd[i]};
                    String s = new String(a);
                    newBoard = newBoard + s;
                }
            }
            possibleState.add(newBoard);

            // get the updated flag String (using method from Task8)
            String completedMoveSequence = historyMove + newMoveSequence;
            int[] flags = WarringStatesGame.getFlags(setup, completedMoveSequence, numPlayers);

            // if the current player owns the country's flag after this move, add the mark by 5
            if (country == 'a') {
                if (flags[0] == checkedPlayer) {
                    newMark = newMark + 5;
                }
            } else if (country == 'b') {
                if (flags[1] == checkedPlayer) {
                    newMark = newMark + 5;
                }
            } else if (country == 'c') {
                if (flags[2] == checkedPlayer) {
                    newMark = newMark + 5;
                }
            } else if (country == 'd') {
                if (flags[3] == checkedPlayer) {
                    newMark = newMark + 5;
                }
            } else if (country == 'e') {
                if (flags[4] == checkedPlayer) {
                    newMark = newMark + 5;
                }
            } else if (country == 'f') {
                if (flags[5] == checkedPlayer) {
                    newMark = newMark + 5;
                }
            } else {
                if (flags[6] == checkedPlayer) {
                    newMark = newMark + 5;
                }
            }

            // check whether the current checked player is our initial player (who wants to do the minimax)
            // if yes, add the mark to the total mark; if no, then minus the mark
            if (checkedPlayer == initialPlayer) {
                totalMark = totalMark + newMark;
            } else {
                totalMark = totalMark - newMark;
            }
            possibleState.add(Integer.toString(totalMark));

        }

        return possibleState;
    }


    // Do loops to get the bottom layer states for "Minimax" tree, given our required lookedLayers number
    public static List<String> bottomLookedLayerStates(int LookedLayers, String currentBoard, int initialPlayer, int numPlayers, String setup, String historyMove) {

        // initial state setting in String form (1st: moveSequence; 2nd: board; 3rd: mark)
        List<String> beginningStates = new ArrayList<>();
        beginningStates.add("");
        beginningStates.add(currentBoard); //
        beginningStates.add("0"); //

        for (int i = 1; i <= LookedLayers; i++) {

            // initialise the new created one whole layer "all possible states" String List
            List<String> completedOneLayer = new ArrayList<>();

            for (int p = 0; p < beginningStates.size(); p = p + 3) {

                // get the player ID of the currently checked player
                int checkedPlayer = (initialPlayer + i) % numPlayers;

                List<String> previousState = new ArrayList<>();
                previousState.add(beginningStates.get(p));
                previousState.add(beginningStates.get(p + 1));
                previousState.add(beginningStates.get(p + 2));

                // combine all possible states under different previous state nodes
                completedOneLayer.addAll(allNextStepPossibilities(previousState, initialPlayer, checkedPlayer, numPlayers, setup, historyMove));
            }

            // update beginningStates for next loop as the completed states combination for this loop
            beginningStates = completedOneLayer;
        }

        return beginningStates;
    }


    // According to our bottomLayerStates, get the possible highest mark for the initial player and its corresponding initial move.
    public static char bestMove(int LookedLayers, String placement, int initialPlayer, int numPlayers, String setup, String historyMove) {

        // 3 elements as a group: 1st -- moveSequence; 2nd -- board; 3rd -- mark
        List<String> bottomLayerStates = bottomLookedLayerStates(LookedLayers, placement, initialPlayer, numPlayers, setup, historyMove);

        // initialize
        String bestMoveSequence = bottomLayerStates.get(0);
        int bestMark = Integer.parseInt(bottomLayerStates.get(2));

        // compare final mark one by one in the loop, and keeps the moveSequence corresponding to the higher mark
        if (bottomLayerStates.size() >= 6) {
            for (int i = 5; i < bottomLayerStates.size(); i = i + 3) {
                int currentMark = Integer.parseInt(bottomLayerStates.get(i));
                if (currentMark > bestMark) {
                    bestMoveSequence = bottomLayerStates.get(i - 2);
                }
            }
        }

        // the first character of the bestMoveSequence is the move that the initial player is willing to go currently to maximize his/ her potential marks,
        // considering opponents' rational potential choices in future steps
        return ((bestMoveSequence.toCharArray())[0]);
    }


    // Basic Test
    public static void main(String[] args) {
        int layers = 4;
        int player = 0;
        int numPlayers = 2;
        String history = "";
        String setup = "g0Aa0Bf1Ca1Dc5Ee1Fa4Ge3He2Ia2Jc2Kd0Lf0Mb4Nd4Oa6Pc3Qe0Ra5Sc1Td1Uc4Vb5Wb0Xa7Yf2Zb10a31z92b33b64d35g16b27d28c09";

//        System.out.println(allValidMoves(setup));
        List<String> previousState = new ArrayList<>();
        previousState.add("");
        previousState.add(setup);
        previousState.add("0");
        System.out.println(allNextStepPossibilities(previousState, 0, 1, 2, setup, ""));
        System.out.println(bottomLookedLayerStates(layers, setup, player, numPlayers, setup, history));
//        System.out.println(bestMove(layers, setup, player, numPlayers, setup, history));

    }
}
