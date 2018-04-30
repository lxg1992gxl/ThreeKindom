package comp1110.ass2;

import comp1110.ass2.WarringStatesGame;

import java.util.ArrayList;
import java.util.Collections;

public class SubOperationCheck {
    // Task 7
    public static String orderedCards(ArrayList<String> existedCards) {
        char[] s_arr;
        Collections.sort(existedCards);
        s_arr = new char[2 * existedCards.size()];
        for (int r = 0; r < existedCards.size(); r++) {
            char[] r_arr = existedCards.get(r).toCharArray();
            s_arr[2 * r] = r_arr[0];
            s_arr[2 * r + 1] = r_arr[1];
        }
        return (new String(s_arr));
    }

    // Task 9
    public static int[] playerFlagNumbers(int[] finalFlags){
        // here, "int[] finalFlags = WarringStatesGame.getFlags(setup, moveSequence, numPlayers)" which we can get from Task 8 result
        int[] flagNumbers = new int[4];
        for (int i = 0; i < 7; i++) {
            flagNumbers[finalFlags[i]] ++;
        }
        return flagNumbers;
    }


    }



