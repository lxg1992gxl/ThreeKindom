package comp1110.ass2.gui;

import comp1110.ass2.WarringStatesGame;

import java.util.ArrayList;
import java.util.List;

public class AIstrategies {

    // collect all valid-move cards
    public static List<Character> allValidCards (String currentBoard) {
        List<Character> allValidCards = new ArrayList<>();

        // find ZhangYi's location in the currentBoard
        char zyloc = currentBoard.charAt(currentBoard.indexOf('z') + 2);

        // find all valid cards in the "currentBoard" sequence (collect a card in a 3-character form)
        char[] place = currentBoard.toCharArray();
        for (int i = 2; i < currentBoard.length(); i = i + 3) {
            char loc = place[i];
            if (loc != zyloc && (WarringStatesGame.sameRow(loc, zyloc) || WarringStatesGame.sameCol(loc, zyloc))) {
                allValidCards.add(place[i - 2]);
                allValidCards.add(place[i - 1]);
                allValidCards.add(loc);
            }
        }

        return allValidCards;
    }






    // produce the corresponding moveSequence according to the number of steps we need to look on Minimax Tree
    public static List<Character> moveList (int lookSteps, String currentBoard) {
        List<Character> moves = new ArrayList<>();

        // find ZhangYi's initial location in the currentBoard
        char zyloc = currentBoard.charAt(currentBoard.indexOf('z') + 2);

        int i = 1;
        while (i <= lookSteps) {

            i++;
        }

        return moves;
    }


}
