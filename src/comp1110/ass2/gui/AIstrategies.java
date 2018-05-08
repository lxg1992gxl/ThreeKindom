package comp1110.ass2.gui;

import comp1110.ass2.WarringStatesGame;

import java.util.ArrayList;
import java.util.List;

public class AIstrategies {

    // collect all valid-move cards
    public static List<Character> allSameColumnOrRowWithZY (String placement) {
        List<Character> allValidCards = new ArrayList<>();

        // find ZhangYi's location in the placement
        char zyloc = placement.charAt(placement.indexOf('z') + 2);
        int ZY = WarringStatesGame.normaliseLoc(zyloc);

        // find all valid cards in the "placement" sequence (collect a card in a 3-character form)
        char[] place = placement.toCharArray();
        for (int i = 2; i < placement.length(); i = i + 3) {
            char loc = place[i];
            if (loc!=zyloc && (WarringStatesGame.sameRow(loc, zyloc) || WarringStatesGame.sameCol(loc, zyloc))) {
                allValidCards.add(place[i-2]);
                allValidCards.add(place[i-1]);
                allValidCards.add(loc);
            }
        }

        return allValidCards;
    }

    
}
