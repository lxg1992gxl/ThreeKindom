package comp1110.ass2.gui;

import comp1110.ass2.WarringStatesGame;

import java.util.ArrayList;
import java.util.List;

import static comp1110.ass2.WarringStatesGame.normaliseLoc;
import static comp1110.ass2.WarringStatesGame.sameCol;
import static comp1110.ass2.WarringStatesGame.sameRow;

public class AIstrategies {

    // collect all valid-move cards
    public static List<String> allValidMoves(String currentBoard) {
        List<String> allValidMoves = new ArrayList<>();

        // find ZhangYi's location in the currentBoard
        char zyloc = currentBoard.charAt(currentBoard.indexOf('z') + 2);

        // find all valid cards in the "currentBoard" sequence (collect a card in a 3-character form)
        char[] place = currentBoard.toCharArray();
        for (int i = 2; i < currentBoard.length(); i = i + 3) {
            char loc = place[i];
            if (loc != zyloc && (sameRow(loc, zyloc) || sameCol(loc, zyloc))) {
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


    // At this stage, previousState just have 3 String represent one state (1st: previous moveSequence; 2nd: board; 3rd: mark)
    public static List<String> allNextStepPossibilities(List<String> previousState, int initialPlayer, int checkedPlayer, int numPlayers) {
        List<String> possibleState = new ArrayList<>();

        // get the completed-structured list with all possible next-step boards, without correct updated marks
        // (all marks are just previous marks)
        String previousBoard = previousState.get(1);
        List<String> allValidMoves = allValidMoves(previousBoard);

        for (String move : allValidMoves) {

            // add new move sequence for one possibleState
            String newMoveSequence = previousState.get(0) + move;
            possibleState.add(newMoveSequence);

            int totalMark = Integer.valueOf(previousState.get(2));

            int newMark = 0;

            char[] bd = previousBoard.toCharArray();
            char mov = (move.toCharArray())[0];

            // the normalised form for destination location
            int D = normaliseLoc(mov);

            // find ZhangYi's location for previous board
            char zyloc = previousBoard.charAt(previousBoard.indexOf('z') + 2);
            int ZY = normaliseLoc(zyloc);

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

            for (int k = 0; k < previousBoard.length(); k = k + 3) {
                int K = normaliseLoc(bd[k + 2]);
                // the card belongs to same country with the destination
                if (bd[k] == country) {
                    // the card is in the same row or column with ZhangYi and destination position
                    if ((sameRow(mov, bd[k + 2]) && sameRow(zyloc, bd[k + 2])) || (sameCol(mov, bd[k + 2]) && sameCol(zyloc, bd[k + 2]))) {
                        // the card is between ZhangYi and the destination position
                        if ((D < K && K < ZY) || (ZY < K && K < D)) {
                            // whenever found a card between ZY and the destination position, "add mark" by 1
                            newMark++;
                            bd[k] = '/';
                            bd[k - 1] = '/';
                            bd[k - 2] = '/';
                        }
                    }
                }
            }

            // set previous Zhang Yi's position to empty
            bd[previousBoard.indexOf('z') + 2] = '/';
            bd[previousBoard.indexOf('z') + 1] = '/';
            bd[previousBoard.indexOf('z')] = '/';

            // move ZY to his new position
            bd[m - 2] = 'z';
            bd[m - 1] = '9';

            // update our new board
            String newBoard = null;
            for (int i = 0; i < bd.length; i++) {
                if (bd[i] != '/') {
                    char[] a = new char[]{bd[i]};
                    newBoard = newBoard + a;
                }
            }
            possibleState.add(newBoard);

            // get the updated flag String (using method from Task8)
            int[] flags = WarringStatesGame.getFlags(previousBoard, move, numPlayers);

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


}
