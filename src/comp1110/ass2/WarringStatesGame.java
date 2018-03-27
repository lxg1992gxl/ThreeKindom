package comp1110.ass2;

import java.util.Arrays;

/**
 * This class provides the text interface for the Warring States game
 */
public class WarringStatesGame {

    /**
     * Determine whether a card placement is well-formed according to the following:
     * - it consists of exactly three characters
     * - the first character is in the range a..g (kingdom) or is z (Zhang Yi)
     * - the second character is numeric, and is a valid character number for that kingdom (9 for Zhang Yi)
     * - the third character is in the range A .. Z or 0..9 (location)
     *
     * @param cardPlacement A string describing a card placement
     * @return true if the card placement is well-formed
     */
    static boolean isCardPlacementWellFormed(String cardPlacement) {
        //Task 2: determine whether a card placement is well-formed

        char[] chunks = cardPlacement.toCharArray();

        if (chunks.length != 3) {
            return false;
        } else {
            //check for first character
            if (chunks[0] >= 'a' & chunks[0] <= 'g') {
                //check for second character
                if ((chunks[1] + chunks[0] <= 152) & (chunks[1] >= 48)) {
                    //check for third character
                    if ((chunks[2] >= 'A' & chunks[2] <= 'Z') | (chunks[2] >= '0' & chunks[2] <= '9')) {
                        //check in right range and also number possibilities
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                } //if second not in range
            } else if (chunks[0] == 'z') {
                if (chunks[1] == '9') {
                    if ((chunks[2] >= 'A' & chunks[2] <= 'Z') | (chunks[2] >= '0' & chunks[2] <= '9')) {
                        //check in right range and also number possibilities
                        return true;
                    } else {
                        return false;
                    } //if third is not in range
                } else { //if second is not in range
                    return false;
                }
            } else { //if first character is not in range
                return false;
            }

        }

    }

    /**
     * Determine whether a placement string is well-formed:
     * - it consists of exactly N three-character card placements (where N = 1 .. 36);
     * - each card placement is well-formed
     * - no card appears more than once in the placement
     * - no location contains more than one card
     * <p>
     * * @param placement A string describing a placement of one or more cards
     *
     * @return true if the placement is well-formed
     */

    static boolean isPlacementWellFormed(String placement) {
        //Task 3: determine whether a placement is well-formed

        //Construct the possible number index in each different country group
        char qin[] = new char[]{'0', '1', '2', '3', '4', '5', '6', '7'};
        char qi[] = new char[]{'0', '1', '2', '3', '4', '5', '6'};
        char chu[] = new char[]{'0', '1', '2', '3', '4', '5'};
        char zhao[] = new char[]{'0', '1', '2', '3', '4'};
        char han[] = new char[]{'0', '1', '2', '3'};
        char wei[] = new char[]{'0', '1', '2'};
        char yan[] = new char[]{'0', '1'};
        char zy[] = new char[]{'9'};
        char loc[] = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        // Some initial check : whether the string is empty OR have more elements (>36) than possible OR have not only three-digit expression
        if (placement == null || placement == "" || placement.length() % 3 != 0 || placement.length() > 36 * 3) {
            return false;
        } else {
            char[] place = placement.toCharArray();

            // Check each three characters by arranging them into different country groups (7 countries and Zhangyi)
            //* use i to control the whole three-character taken loop (if one set of three characters gets the requirements, go to the next three)
            //* and check whether the 1st character is in the country representation range
            int i = 0;
            while (i != -1 && i < placement.length()) {
                //* use k to check whether 2nd character is defined in this 'country' group range
                //* and ensure no defined card appears more than once
                int k = 0;
                if (place[i] == 'z' && place[i + 1] == '9') {
                    if (zy != null) {
                        zy = null;
                        k = -1;
                    }
                } else if (place[i] == 'a') {
                    while (k != -1 && k < qin.length) {
                        if (place[i + 1] == qin[k]) {
                            qin[k] = '/';
                            k = -1;
                        } else {
                            k++;
                        }
                    }
                } else if (place[i] == 'b') {
                    while (k != -1 && k < qi.length) {
                        if (place[i + 1] == qi[k]) {
                            qi[k] = '/';
                            k = -1;
                        } else
                            k++;
                    }
                } else if (place[i] == 'c') {
                    while (k != -1 && k < chu.length) {
                        if (place[i + 1] == chu[k]) {
                            chu[k] = '/';
                            k = -1;
                        } else
                            k++;
                    }
                } else if (place[i] == 'd') {
                    while (k != -1 && k < zhao.length) {
                        if (place[i + 1] == zhao[k]) {
                            zhao[k] = '/';
                            k = -1;
                        } else
                            k++;
                    }
                } else if (place[i] == 'e') {
                    while (k != -1 && k < han.length) {
                        if (place[i + 1] == han[k]) {
                            han[k] = '/';
                            k = -1;
                        } else
                            k++;
                    }
                } else if (place[i] == 'f') {
                    while (k != -1 && k < wei.length) {
                        if (place[i + 1] == wei[k]) {
                            wei[k] = '/';
                            k = -1;
                        } else
                            k++;
                    }
                } else if (place[i] == 'g') {
                    while (k != -1 && k < yan.length) {
                        if (place[i + 1] == yan[k]) {
                            yan[k] = '/';
                            k = -1;
                        } else
                            k++;
                    }
                }
                if (k == -1)
                    i = i + 3;
                else
                    i = -1;
            }

            //* if the cards' check if fine, go to the location check
            if (i == -1) {
                return false;
            } else {
                // check whether the 3rd character is in the location representative range
                // and ensure no location contains more than one card
                int l = 2;
                while (l != -1 && l < placement.length()) {
                    int k = 0;
                    while (k != -1 && k < loc.length) {
                        if (place[l] == loc[k]) {
                            loc[k] = '/';
                            k = -1;
                        } else {
                            k++;
                        }
                    }
                    if (k == -1)
                        l = l + 3;
                    else
                        l = -1;
                }
                if (l == -1) {
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    /**
     * takes a locationChar and normalises it to use in row and column comparisons
     * and distance methods
     * assuming that locations are encoded A-Z and 0-9 for a 6x6 grid.
     *
     * @param locationChar
     * @return the location char normalises to an int 0-35
     */


    public static int normaliseLoc(char locationChar) {
        int result = -1;
        if (locationChar >= 'A' & locationChar <= 'Z') { //normalises letter locations
            result = ((int) locationChar);
            result -= 65;
        } else { //normalises number locations
            result = (int) locationChar;
            result -= 22;
        }
        return result;
    }

    /**
     * Compares whether two location characters are in the same row,
     * assuming that locations are encoded A-Z and 0-9 for a 6x6 grid.
     *
     * @param location1
     * @param location2
     * @return true if the locations are in the same row as each other
     */

    public static boolean sameRow(char location1, char location2) {
        int loc1 = normaliseLoc(location1);
        int loc2 = normaliseLoc(location2);

        if (loc1 % 6 == loc2 % 6) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Compares whether two location characters are in the same column,
     * assuming that locations are encoded A-Z and 0-9 for a 6x6 grid.
     *
     * @param location1
     * @param location2
     * @return true if the locations are in the same column as each other
     */

    public static boolean sameCol(char location1, char location2) {
        int loc1 = normaliseLoc(location1);
        int loc2 = normaliseLoc(location2);

        if (loc1 / 6 == loc2 / 6) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Determine whether a given move is legal given a provided valid placement:
     * - the location char is in the range A .. Z or 0..9
     * - there is a card at the chosen location;
     * - the location is in the same row or column of the grid as Zhang Yi's current position; and
     * - drawing a line from Zhang Yi's current location through the card at the chosen location,
     * there are no other cards along the line from the same kingdom as the chosen card
     * that are further away from Zhang Yi.
     *
     * @param placement    the current placement string
     * @param locationChar a location for Zhang Yi to move to
     * @return true if Zhang Yi may move to that location
     */

    public static boolean isMoveLegal(String placement, char locationChar) {
        //Task 5: determine whether a given move is legal

        //check placement valid
        //check location char in range
        if ((locationChar >= 'A' & locationChar <= 'Z') | (locationChar >= '0' & locationChar <= '9')) {
            //get Zhang yi's current location
            char zhangloc = placement.charAt(placement.indexOf('z') + 2);

            //check for card at loc
            String cardAtLoc = null;
            for (int i = 0; i < placement.length() / 3; i++) {
                if (locationChar == placement.charAt((i * 3) + 2)) {
                    cardAtLoc = placement.substring(i * 3, (i * 3) + 3);
                }
            }

            if (cardAtLoc == null) {
                return false;
            }

            if (!(sameRow(locationChar, zhangloc)) & !(sameCol(locationChar, zhangloc))) {
                //System.out.println(cardAtLoc);
                return false;
            }


            //check for other same kingdom in line
            //check distance inc direction
            //if distance is less than 0, none that ar less, if distance is greater than 0, none that are greater
            //card at loc

            char kingdom = cardAtLoc.charAt(0);
            for (int i = 0; i < placement.length() / 3; i++) {
                if (placement.charAt(i * 3) == kingdom) { //checks for other cards in same kingdom as the locationChar

                    //take the chunk for that card
                    String a = placement.substring(i * 3, (i * 3) + 3);
                    //check for same col

                    if (sameCol(zhangloc, locationChar)) {

                        if (sameCol(zhangloc, a.charAt(2))) {
//                          //compare distances
                            int dist = normaliseLoc(locationChar) - normaliseLoc(zhangloc);
                            int dist2 = normaliseLoc(a.charAt(2)) - normaliseLoc(zhangloc);
                            if (dist > 0) {
                                if (dist2 > dist) {
                                    return false;
                                }
                            } else {
                                if (dist2 < dist) {
                                    return false;
                                }
                            }

                        }
                    }
                    //check for same row
                    if (sameRow(zhangloc, locationChar)) {
                        if (sameRow(locationChar, a.charAt(2))) {
                            //compare distances
                            int dist = normaliseLoc(locationChar) - normaliseLoc(zhangloc);
                            int dist2 = normaliseLoc(a.charAt(2)) - normaliseLoc(zhangloc);
                            if (dist > 0) {
                                if (dist2 > dist) {
                                    return false;
                                }
                            } else {
                                if (dist2 < dist) {
                                    return false;
                                }
                            }

                        }
                    }

                }
            }


            return true;
        } else { //locationChar is out of range
            return false;
        }


    }


    /**
     * Determine whether a move sequence is valid.
     * To be valid, the move sequence must be comprised of 1..N location characters
     * showing the location to move for Zhang Yi, and each move must be valid
     * given the placement that would have resulted from the preceding sequence
     * of moves.
     *
     * @param setup        A placement string representing the board setup
     * @param moveSequence a string of location characters representing moves
     * @return True if the placement sequence is valid
     */
    static boolean isMoveSequenceValid(String setup, String moveSequence) {
        // FIXME Task 6: determine whether a placement sequence is valid
        // check whether has empty string or initial out of range
        if (moveSequence.length() > 36 || moveSequence == null || moveSequence == "" || setup == null || setup == "") {
            return false;
        }
        else {
            char[] move = moveSequence.toCharArray();

            // find ZhangYi's location
            char zyloc = setup.charAt(setup.indexOf('z') + 2);

            // go through every move in moveSequence one by one
            int i = 0;
            while (i != -1 && i < moveSequence.length()) {

                char[] board = setup.toCharArray();

                if (isMoveLegal(setup, move[i])) {
                    // update setup board with the new checked move
                    int p = 2;
                    int P = normaliseLoc(board[p]);

                    while (p != -1 && p < setup.length()) {
                        // find the setup board location same with the current move location
                        if (board[p] == move[i]) {
                            board[p] = '-';
                            board[p - 1] = '-';
                            board[p - 2] = '-';

                            // find the corresponding country for the card in current move location
                            char country = board[p - 2];

                            // go through the board to find the card from same country between ZhangYi and goal location
                            int k = 2;
                            int K = normaliseLoc(board[k]);
                            int ZY = normaliseLoc(board[zyloc]);

                            while (k < setup.length()) {
                                if (sameCol(board[p], board[k]) && country == board[k - 2] && Math.abs(K - ZY) < Math.abs(P - ZY)) {
                                    board[k] = '-';
                                    board[k - 1] = '-';
                                    board[k - 2] = '-';
                                } else if (sameRow(board[p], board[k]) && country == board[k - 2] && Math.abs(K - ZY) < Math.abs(P - ZY)) {
                                    board[k] = '-';
                                    board[k - 1] = '-';
                                    board[k - 2] = '-';
                                }
                                k = k + 3;
                            }

                            setup = Arrays.toString(board);
                            p = -1;
                        } else {
                            p = p + 3;
                        }
                    }
                    i++;
                } else {
                    // use i to record "have found an invalid move"
                    i = -1;
                }
            }

            // justify whether we check until the end of the moveSequence
            if (i == -1) {
                return false;
            } else {
                return true;
            }

        }
    }


    /**
     * Get the list of supporters for the chosen player, given the provided
     * setup and move sequence.
     * The list of supporters is a sequence of two-character card IDs, representing
     * the cards that the chosen player collected by moving Zhang Yi.
     *
     * @param setup        A placement string representing the board setup
     * @param moveSequence a string of location characters representing moves
     * @param numPlayers   the number of players in the game, must be in the range [2..4]
     * @param playerId     the player number for which to get the list of supporters, [0..(numPlayers-1)]
     * @return the list of supporters for the given player
     */
    public static String getSupporters(String setup, String moveSequence, int numPlayers, int playerId) {
        // FIXME Task 7: get the list of supporters for a given player after a sequence of moves
        return null;
    }

    /**
     * Given a setup and move sequence, determine which player controls the flag of each kingdom
     * after all the moves in the sequence have been played.
     *
     * @param setup        A placement string representing the board setup
     * @param moveSequence a string of location characters representing a sequence of moves
     * @param numPlayers   the number of players in the game, must be in the range [2..4]
     * @return an array containing the player ID who controls each kingdom, where
     * - element 0 contains the player ID of the player who controls the flag of Qin
     * - element 1 contains the player ID of the player who controls the flag of Qi
     * - element 2 contains the player ID of the player who controls the flag of Chu
     * - element 3 contains the player ID of the player who controls the flag of Zhao
     * - element 4 contains the player ID of the player who controls the flag of Han
     * - element 5 contains the player ID of the player who controls the flag of Wei
     * - element 6 contains the player ID of the player who controls the flag of Yan
     * If no player controls a particular house, the element for that house will have the value -1.
     */
    public static int[] getFlags(String setup, String moveSequence, int numPlayers) {
        // FIXME Task 8: determine which player controls the flag of each kingdom after a given sequence of moves
        return null;
    }

    /**
     * Generate a legal move, given the provided placement string.
     * A move is valid if:
     * - the location char is in the range A .. Z or 0..9
     * - there is a card at the chosen location;
     * - the destination location is different to Zhang Yi's current location;
     * - the destination is in the same row or column of the grid as Zhang Yi's current location; and
     * - drawing a line from Zhang Yi's current location through the card at the chosen location,
     * there are no other cards along the line from the same kingdom as the chosen card
     * that are further away from Zhang Yi.
     * If there is no legal move available, return the null character '\0'.
     *
     * @param placement the current placement string
     * @return a location character representing Zhang Yi's destination for the move
     */
    public static char generateMove(String placement) {
        // FIXME Task 10: generate a legal move
        return '\0';
    }
}
