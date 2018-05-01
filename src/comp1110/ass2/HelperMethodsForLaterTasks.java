package comp1110.ass2;

public class HelperMethodsForLaterTasks {

//    // Task 7
//    public static String orderedCards(ArrayList<String> existedCards) {
//        char[] s_arr;
//        Collections.sort(existedCards);
//        s_arr = new char[2 * existedCards.size()];
//        for (int r = 0; r < existedCards.size(); r++) {
//            char[] r_arr = existedCards.get(r).toCharArray();
//            s_arr[2 * r] = r_arr[0];
//            s_arr[2 * r + 1] = r_arr[1];
//        }
//        return (new String(s_arr));
//    }


    // Task 9
    // get an array of the final flags number of every player, player ID will work as array index
    public static int[] playerFlagNumbers(int[] finalFlags) {
        // here, "int[] finalFlags = WarringStatesGame.getFlags(setup, moveSequence, numPlayers)" which we can get from Task 8 result
        int[] flagNumbers = new int[4];
        for (int i = 0; i < 7; i++) {
            int p = finalFlags[i];
            if (p != -1) {
                flagNumbers[p]++;
            }
        }
        return flagNumbers;
    }


    // get an array with player IDs of players who hold the maximum number of flags
    public static int[] getMaximumFlagsIDs(int[] finalFlags) {
        // get No. of flags of every players
        int[] flagNumbers = playerFlagNumbers(finalFlags);

        // initialize an array to record the player with the maximum flags
        int[] maxFlagsPlayer = new int[] {-1, -1, -1, -1};

        // get the maximum No. of flags among all players
        int flagMax = 0;
        for (int i = 0; i < flagNumbers.length; i++) {
            if (flagNumbers[i] >= flagMax) {
                flagMax = flagNumbers[i];
            }
        }

        // get all players IDs that own that max No. of flags
        for (int i = 0; i < flagNumbers.length; i++) {
            if (flagNumbers[i] == flagMax) {
                maxFlagsPlayer[i] = i;
            }
        }

        return maxFlagsPlayer;
    }


    // get the final winner's ID
    public static int getWinnerID(int[] finalFlags) {
        int[] maxFlagsPlayer = getMaximumFlagsIDs(finalFlags);

        int winnerID = -1;

        // check whether there exists one only player holds the greatest number of flags
        int p = 0;
        int player = -1;
        for (int i = 0; i < maxFlagsPlayer.length; i++) {
            if (maxFlagsPlayer[i] != -1) {
                player = i;
                p++;
            }
        }

        if (p == 1) {
            winnerID = player;
        }
        else {

            // check whether there are player inside holds Qin flag
            for (int i = 0; i < maxFlagsPlayer.length; i++) {
                if (maxFlagsPlayer[i] == finalFlags[0]) {
                    winnerID = maxFlagsPlayer[i];
                }
            }
            // check whether there are player inside holds Qi flag
            if (winnerID == -1) {
                for (int i = 0; i < maxFlagsPlayer.length; i++) {
                    if (maxFlagsPlayer[i] == finalFlags[1]) {
                        winnerID = maxFlagsPlayer[i];
                    }
                }
            }
            // check whether there are player inside holds Chu flag
            if (winnerID == -1) {
                for (int i = 0; i < maxFlagsPlayer.length; i++) {
                    if (maxFlagsPlayer[i] == finalFlags[2]) {
                        winnerID = maxFlagsPlayer[i];
                    }
                }
            }
            // check whether there are player inside holds Zhao flag
            if (winnerID == -1) {
                for (int i = 0; i < maxFlagsPlayer.length; i++) {
                    if (maxFlagsPlayer[i] == finalFlags[3]) {
                        winnerID = maxFlagsPlayer[i];
                    }
                }
            }
        }

        return winnerID;
    }

}
