package comp1110.ass2.gui;

import comp1110.ass2.WarringStatesGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public static int[] playerFlagNumbers(int[] finalFlags) {
        // here, "int[] finalFlags = WarringStatesGame.getFlags(setup, moveSequence, numPlayers)" which we can get from Task 8 result
        int[] flagNumbers = new int[4];
        for (int i = 0; i < 7; i++) {
            flagNumbers[finalFlags[i]]++;
        }
        return flagNumbers;
    }

    public static int getWinnerID(int[] finalFlags) {
        // get No. of flags of every players
        int[] flagNumbers = playerFlagNumbers(finalFlags);

        int winnerID = -1;
        // get the maximum No. of flags among all players
        int flagMax = 0;
        for (int i = 0; i < flagNumbers.length; i++) {
            if (flagNumbers[i] >= flagMax) {
                flagMax = flagNumbers[i];
            }
        }

        // get all players IDs that own that max No. of flags
        List<Integer> maxFlagsPlayer = new ArrayList<>();
        for (int i = 0; i < flagNumbers.length; i++) {
            if (flagNumbers[i] == flagMax) {
                maxFlagsPlayer.add(i);
            }
        }

        // check whether there exists one only player holds the greatest number of flags
        if (maxFlagsPlayer.size() == 1) {
            winnerID = maxFlagsPlayer.get(0);
        } else {
            // check whether there are player inside holds Qin flag
            for (int i = 0; i < maxFlagsPlayer.size(); i++) {
                if (maxFlagsPlayer.get(i) == finalFlags[0]) {
                    winnerID = maxFlagsPlayer.get(i);
                }
            }
            // check whether there are player inside holds Qi flag
            if (winnerID == -1) {
                for (int i = 0; i < maxFlagsPlayer.size(); i++) {
                    if (maxFlagsPlayer.get(i) == finalFlags[1]) {
                        winnerID = maxFlagsPlayer.get(i);
                    }
                }
            }
            // check whether there are player inside holds Chu flag
            if (winnerID == -1) {
                for (int i = 0; i < maxFlagsPlayer.size(); i++) {
                    if (maxFlagsPlayer.get(i) == finalFlags[2]) {
                        winnerID = maxFlagsPlayer.get(i);
                    }
                }
            }
            // check whether there are player inside holds Zhao flag
            if (winnerID == -1) {
                for (int i = 0; i < maxFlagsPlayer.size(); i++) {
                    if (maxFlagsPlayer.get(i) == finalFlags[3]) {
                        winnerID = maxFlagsPlayer.get(i);
                    }
                }
            }

        }

        return winnerID;
    }

}



