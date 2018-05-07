package comp1110.ass2.gui;

import comp1110.ass2.WarringStatesGame;

import java.util.ArrayList;
import java.util.List;

public class AIstrategies {

    // Overall, have time limitation for AI Strategic operation

    // First, need to check all cards in the same row or column, and collect them as a corresponding list
    public static List<String> allSameColumnOrRowWithZY (String placement) {
        List<String> allCards = new ArrayList<>();

        // find ZhangYi's location in the placement
        char zyloc = placement.charAt(placement.indexOf('z') + 2);
        int ZY = WarringStatesGame.normaliseLoc(zyloc);

        // find all positions in the "placement" sequence that still have cards
        char[] place = placement.toCharArray();
        List<Character> allPositions = new ArrayList<>();
        for (int p = 2; p < placement.length(); p = p + 3) {
            allPositions.add(place[p-2]);
            allPositions.add(place[p-1]);
            allPositions.add(place[p]);
        }

//        for (int p = 2; p < allPositions.size(); p = p + 3) {
//            char p = allPositions.get(i);
//            if (p != zyloc && (WarringStatesGame.sameCol(p, zyloc) || WarringStatesGame.sameRow(p, zyloc))) {
//            }
//        }


        return allCards;
    }
}
