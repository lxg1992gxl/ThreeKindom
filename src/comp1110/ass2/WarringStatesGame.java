package comp1110.ass2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static comp1110.ass2.iHelperMethods.*;

/**
 * This class provides the text interface for the Warring States game
 */
public class WarringStatesGame {


    public static boolean isValidLocation(char location) {
        if ((location >= 'A' & location <= 'Z') | (location >= '0' & location <= '9')) {
            return true;
        } else {
            return false;
        }
    }

    //return true if the String given represents a card within the game specifications, otherwise returns false
    public static boolean isValidCard (String card){
        char [] chunks;

        if(card.length()==2) {
            chunks = card.toCharArray();
        }
        else{
            return false;
        }


        if(chunks[0] >= 'a' & chunks[0] <= 'g'){
            if ((chunks[1] + chunks[0] <= 152) & (chunks[1] >= 48)){
                return true;
            }
            else {return false;}
        }
        else {return false;}

    }


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
                    //FIXME
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
        // Task 3: determine whether a placement is well-formed

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
        if (locationChar >= 'A' && locationChar <= 'Z') { //normalises letter locations
            result = (int) locationChar;
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

        //FIXME returns true for location char where ZY is 
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
        // Task 6: determine whether a placement sequence is valid
        // check whether has empty string or initial out of range
        if (moveSequence.length() > 36 || moveSequence == "" || setup == null || setup == "") {
            return false;
        } else if (!isPlacementWellFormed(setup)) {
            return false;
        } else {
            char[] move = moveSequence.toCharArray();

            for (int i = 0; i < 36; i++) {  //check duplication of moveSequence
                int count = 0;
                for (int k = 0; k < moveSequence.length(); k++) {
                    if (i == normaliseLoc(move[k])) {
                        count += 1;
                    }
                }
                if (count > 1) {
                    return false;
                }
            }

            // go through every move in moveSequence one by one
            int i = 0;
            while (i != -1 && i < moveSequence.length()) {

                char[] board = setup.toCharArray();

                if (!isMoveLegal(setup, move[i])) {
                    // use i to record "have found an invalid move in the sequence"
                    i = -1;

                } else {
                    // find ZhangYi's location
                    char zyloc = setup.charAt(setup.indexOf('z') + 2);
                    int ZY = normaliseLoc(zyloc);

                    // update setup board with the new checked move
                    int p = 2;
                    while (p != -1 && p < setup.length()) {
                        // find the setup board location same with the current move location
                        if (board[p] == move[i]) {
                            // find the corresponding country for the card in current move location
                            char loc = board[p];
                            char country = board[p - 2];
                            int D = normaliseLoc(loc);

                            // go through the board to find the card from same country between ZhangYi and goal location
                            int k = 2;
                            // find all cards between Zhangyi and the destination, delete them at the same time
                            while (k < setup.length()) {
                                if (board[k - 2] == country) {
                                    int K = normaliseLoc(board[k]);
                                    // the card is in the same row or column with ZhangYi and destination position
                                    if ((sameRow(loc, board[k]) && sameRow(zyloc, board[k])) || (sameCol(loc, board[k]) && sameCol(zyloc, board[k]))) {
                                        // the card is between ZhangYi and the destination position
                                        if (((D < K && K < ZY) || (ZY < K && K < D)) && (D != K)) { //D:destination of zhang//K:possible same contry card//ZY:zhangyi's location
                                            board[k] = '/';
                                            board[k - 1] = '/';
                                            board[k - 2] = '/';
                                        }
                                    }
                                }
                                k = k + 3;
                            }

                            // set previous Zhang Yi's position to empty
                            board[setup.indexOf('z') + 2] = '/';
                            board[setup.indexOf('z') + 1] = '/';
                            board[setup.indexOf('z')] = '/';

                            // move ZY to his new position
                            board[p - 2] = 'z';
                            board[p - 1] = '9';

                            setup = new String(); // generate new setup string by remove all '/' in board array
                            for (int n = 0; n < board.length; n++) {
                                if (board[n] != '/') {
                                    setup += board[n];
                                } else {
                                }
                            }
                            p = -1;
                        } else {
                            p = p + 3;
                        }
                    }
                    i = i + 1;
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
        // Task 7: get the list of supporters for a given player after a sequence of moves

        char[] mov = moveSequence.toCharArray();
        char[] bd = setup.toCharArray();

        // initialize the supporter
        List<String> sup0 = new ArrayList<>();
        List<String> sup1 = new ArrayList<>();
        List<String> sup2 = new ArrayList<>();
        List<String> sup3 = new ArrayList<>();

        // find ZhangYi's initial location for setup board
        char zyloc = setup.charAt(setup.indexOf('z') + 2);

        // make a loop to read all corresponding positions in the moveSequence
        int i = 0;
        while (i < moveSequence.length()) {

            int ZY = normaliseLoc(zyloc);

            // find the destination location on setup board
            int m = 2;
            for (int p = 0; p != -1 && m < setup.length(); m = m + 3) {
                if (bd[m] == mov[i]) {
                    p = -1;
                }
            }
            if (m != 2) {
                m = m - 3;
            }

            //find the corresponding card at destination position
            char country = bd[m - 2];
            char sID = bd[m - 1];

            // the normalised form for destination location
            int D = normaliseLoc(mov[i]);

            // add supporter String at the given destination
            char[] a_arr = new char[]{country, sID};
            String a_str = new String(a_arr);
            // check for different player
            if (i % numPlayers == 0) {
                sup0.add(a_str);
            } else if (i % numPlayers == 1) {
                sup1.add(a_str);
            } else if (i % numPlayers == 2) {
                sup2.add(a_str);
            } else {
                sup3.add(a_str);
            }

            for (int k = 0; k < setup.length(); k = k + 3) {
                int K = normaliseLoc(bd[k + 2]);
                // the card belongs to same country with the destination
                if (bd[k] == country) {
                    // the card is in the same row or column with ZhangYi and destination position
                    if ((sameRow(mov[i], bd[k + 2]) && sameRow(zyloc, bd[k + 2])) || (sameCol(mov[i], bd[k + 2]) && sameCol(zyloc, bd[k + 2]))) {
                        // the card is between ZhangYi and the destination position
                        if ((D < K && K < ZY) || (ZY < K && K < D)) {
                            // add other supporters from the same country between ZhangYi and the destination
                            char[] b_arr = new char[]{bd[k], bd[k + 1]};
                            String b_str = new String(b_arr);

                            // the supporter ID have not been contained in other player's supporters list
                            if (!sup0.contains(b_str) && !sup1.contains(b_str) && !sup2.contains(b_str) && !sup3.contains(b_str)) {

                                if (i % numPlayers == 0) {
                                    sup0.add(b_str);
                                } else if (i % numPlayers == 1) {
                                    sup1.add(b_str);
                                } else if (i % numPlayers == 2) {
                                    sup2.add(b_str);
                                } else {
                                    sup3.add(b_str);
                                }

                            }

                        }
                    }
                }
            }

            // Update ZhangYi's location
            zyloc = mov[i];

            i++;
        }

        char[] s_arr;
        if (playerId == 0) {
            // Make the final supporters list in country order
            Collections.sort(sup0);
            // transform the Character List to our required String type
            s_arr = new char[2 * sup0.size()];
            for (int r = 0; r < sup0.size(); r++) {
                char[] r_arr = sup0.get(r).toCharArray();
                s_arr[2 * r] = r_arr[0];
                s_arr[2 * r + 1] = r_arr[1];
            }
        } else if (playerId == 1) {
            Collections.sort(sup1);
            s_arr = new char[2 * sup1.size()];
            for (int r = 0; r < sup1.size(); r++) {
                char[] r_arr = sup1.get(r).toCharArray();
                s_arr[2 * r] = r_arr[0];
                s_arr[2 * r + 1] = r_arr[1];
            }
        } else if (playerId == 2) {
            Collections.sort(sup2);
            s_arr = new char[2 * sup2.size()];
            for (int r = 0; r < sup2.size(); r++) {
                char[] r_arr = sup2.get(r).toCharArray();
                s_arr[2 * r] = r_arr[0];
                s_arr[2 * r + 1] = r_arr[1];
            }
        } else {
            Collections.sort(sup3);
            s_arr = new char[2 * sup3.size()];
            for (int r = 0; r < sup3.size(); r++) {
                char[] r_arr = sup3.get(r).toCharArray();
                s_arr[2 * r] = r_arr[0];
                s_arr[2 * r + 1] = r_arr[1];
            }
        }

        return (new String(s_arr));
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
        // Task 8: determine which player controls the flag of each kingdom after a given sequence of moves

        char[] mov = moveSequence.toCharArray();
        char[] bd = setup.toCharArray();

        // initialize final output array
        int[] flags = new int[]{-1, -1, -1, -1, -1, -1, -1};

        // initialize the supporter "CHARACTER" LIST
        List<Character> sup0 = new ArrayList<>();
        List<Character> sup1 = new ArrayList<>();
        List<Character> sup2 = new ArrayList<>();
        List<Character> sup3 = new ArrayList<>();

        // initialize the supporter "STRING" LIST
        List<String> sup00 = new ArrayList<>();
        List<String> sup01 = new ArrayList<>();
        List<String> sup02 = new ArrayList<>();
        List<String> sup03 = new ArrayList<>();

        // find ZhangYi's initial location for setup board
        char zyloc = setup.charAt(setup.indexOf('z') + 2);

        // make a loop to read all corresponding positions in the moveSequence
        int i = 0;
        while (i < moveSequence.length()) {
            int currentPlayer = i % numPlayers;

            int ZY = normaliseLoc(zyloc);

            // find the destination location on setup board
            int m = 2;
            for (int p = 0; p != -1 && m < setup.length(); m = m + 3) {
                if (bd[m] == mov[i]) {
                    p = -1;
                }
            }
            if (m != 2) {
                m = m - 3;
            }

            //find the corresponding card at destination position
            char country = bd[m - 2];
            char sID = bd[m - 1];

            // the normalised form for destination location
            int D = normaliseLoc(mov[i]);

            // add supporter String/Character Pairs at the given destination & check for different players
            char[] a_arr = new char[]{country, sID};
            String a_str = new String(a_arr);

            // add supporter String at the given destination & check for different players
            if (currentPlayer == 0) {
                sup0.add(country);
                sup0.add(sID);
                sup00.add(a_str);
            } else if (currentPlayer == 1) {
                sup1.add(country);
                sup1.add(sID);
                sup01.add(a_str);
            } else if (currentPlayer == 2) {
                sup2.add(country);
                sup2.add(sID);
                sup02.add(a_str);
            } else {
                sup3.add(country);
                sup3.add(sID);
                sup03.add(a_str);
            }

            for (int k = 0; k < setup.length(); k = k + 3) {
                int K = normaliseLoc(bd[k + 2]);
                // the card belongs to same country with the destination
                if (bd[k] == country) {
                    // the card is in the same row or column with ZhangYi and destination position
                    if ((sameRow(mov[i], bd[k + 2]) && sameRow(zyloc, bd[k + 2])) || (sameCol(mov[i], bd[k + 2]) && sameCol(zyloc, bd[k + 2]))) {
                        // the card is between ZhangYi and the destination position
                        if ((D < K && K < ZY) || (ZY < K && K < D)) {
                            // add other supporters from the same country between ZhangYi and the destination
                            char[] b_arr = new char[]{bd[k], bd[k + 1]};
                            String b_str = new String(b_arr);
                            // the supporter ID have not been contained in other player's supporters list
                            if (!sup00.contains(b_str) && !sup01.contains(b_str) && !sup02.contains(b_str) && !sup03.contains(b_str)) {

                                if (currentPlayer == 0) {
                                    sup0.add(bd[k]);
                                    sup0.add(bd[k + 1]);
                                    sup00.add(b_str);
                                } else if (currentPlayer == 1) {
                                    sup1.add(bd[k]);
                                    sup1.add(bd[k + 1]);
                                    sup01.add(b_str);
                                } else if (currentPlayer == 2) {
                                    sup2.add(bd[k]);
                                    sup2.add(bd[k + 1]);
                                    sup02.add(b_str);
                                } else {
                                    sup3.add(bd[k]);
                                    sup3.add(bd[k + 1]);
                                    sup03.add(b_str);
                                }
                            }
                        }
                    }
                }
            }

            // Update ZhangYi's location
            zyloc = mov[i];


            // ** Main new part for Task8 comparing to Task7 !

            // get No. of the corresponding updated country's cards of current player

            int playerCardsNum = 0;

            if (currentPlayer == 0) {
                for (int p = 0; p < sup0.size(); p = p + 2) {
                    if (sup0.get(p) == country) {
                        playerCardsNum++;
                    }
                }
            } else if (currentPlayer == 1) {
                for (int p = 0; p < sup1.size(); p = p + 2) {
                    if (sup1.get(p) == country) {
                        playerCardsNum++;
                    }
                }
            } else if (currentPlayer == 2) {
                for (int p = 0; p < sup2.size(); p = p + 2) {
                    if (sup2.get(p) == country) {
                        playerCardsNum++;
                    }
                }
            } else {
                for (int p = 0; p < sup3.size(); p = p + 2) {
                    if (sup3.get(p) == country) {
                        playerCardsNum++;
                    }
                }
            }


            // get the flagOwner playerId that currently has the "country" flag
            int flagOwner;
            if (country == 'a') {
                flagOwner = flags[0];
            } else if (country == 'b') {
                flagOwner = flags[1];
            } else if (country == 'c') {
                flagOwner = flags[2];
            } else if (country == 'd') {
                flagOwner = flags[3];
            } else if (country == 'e') {
                flagOwner = flags[4];
            } else if (country == 'f') {
                flagOwner = flags[5];
            } else {
                flagOwner = flags[6];
            }

            // First, check whether no previous country holds the flag
            if (flagOwner == -1) {
                if (country == 'a') {
                    flags[0] = currentPlayer;
                } else if (country == 'b') {
                    flags[1] = currentPlayer;
                } else if (country == 'c') {
                    flags[2] = currentPlayer;
                } else if (country == 'd') {
                    flags[3] = currentPlayer;
                } else if (country == 'e') {
                    flags[4] = currentPlayer;
                } else if (country == 'f') {
                    flags[5] = currentPlayer;
                } else {
                    flags[6] = currentPlayer;
                }
            }
            // Then, check whether the flag owner is the current player, if yes, skip all following check steps (no change will happen)
            else if (flagOwner != currentPlayer) {
                // Find No. of the corresponding country's cards of the current flagOwner
                int flagCardsNum = 0;

                if (flagOwner == 0) {
                    for (int p = 0; p < sup0.size(); p = p + 2) {
                        if (sup0.get(p) == country) {
                            flagCardsNum++;
                        }
                    }
                } else if (flagOwner == 1) {
                    for (int p = 0; p < sup1.size(); p = p + 2) {
                        if (sup1.get(p) == country) {
                            flagCardsNum++;
                        }
                    }
                } else if (flagOwner == 2) {
                    for (int p = 0; p < sup2.size(); p = p + 2) {
                        if (sup2.get(p) == country) {
                            flagCardsNum++;
                        }
                    }
                } else if (flagOwner == 3) {
                    for (int p = 0; p < sup3.size(); p = p + 2) {
                        if (sup3.get(p) == country) {
                            flagCardsNum++;
                        }
                    }
                }

                // check whether the current player holds an equal or greater number of characters of a country than the current flag owner
                if (playerCardsNum >= flagCardsNum) {
                    if (country == 'a') {
                        flags[0] = currentPlayer;
                    } else if (country == 'b') {
                        flags[1] = currentPlayer;
                    } else if (country == 'c') {
                        flags[2] = currentPlayer;
                    } else if (country == 'd') {
                        flags[3] = currentPlayer;
                    } else if (country == 'e') {
                        flags[4] = currentPlayer;
                    } else if (country == 'f') {
                        flags[5] = currentPlayer;
                    } else {
                        flags[6] = currentPlayer;
                    }
                }

            }
            i++;
        }

        return flags;
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
        // Task 10: generate a legal move

        // find ZhangYi's location in the placement
        char zyloc = placement.charAt(placement.indexOf('z') + 2);

        // if there is no legal move available, return null character
        if (noMoreValidMove(placement)) {
            return '\0';
        } else {
            // randomly produce a "move character" until it satisfies our requirements
            Random rand = new Random();
            char move = ' ';
            while (!((move >= 'A' && move <= 'Z') || (move >= '0' && move <= '9')) || move == zyloc || !isMoveLegal(placement, move)) {
                move = (char) rand.nextInt(91);
            }
            return move;
        }
    }


    public void makeMove(String placement, char location, String history, int players, int playerID){
        //move ZY to the location
        placement = placement ; //(minus supporters, move ZY)

        //remove from placement, loop for getSupporters, and +1 to include location also
        getSupporters(placement, history, players, playerID);
        //take card from location and any on way off board/placement
        // reverse of cards player has?)

        //show supporters for relevant player

        //check if any valid moves left (use HelperMethods)
        if(noMoreValidMove(placement)){ //but then remove card(s) as applicable
            endGame(placement, history, players);
        }

        //inGame class, when call this method, currentPLayer += 1 (and loop around based on total number of players)

        //if current player == computer, make move using computerplayer methods
    }



    public void endGame(String placement, String history, int players){
        //check flags

        //determine winner
        int winner = getWinnerID(getFlags(placement,history, players)); //int[])
        //display winner

        //don't allow to continue playing when finished- boolean playable?

    }


    public void startGame(){
        //creates a random setup
        Random r = new Random();


        //use logic from testUtility, be sure to include in statement of originality!!!

        //random card from the available, goes into A...Z....9


    }

}
