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
        char[] chunks = cardPlacement.toCharArray();

        if (chunks.length!=3){
             return false;
        }

        else {
            //check for first character
            if(chunks[0]>='a' & chunks[0]<='g'){
                //check for second character
                if((chunks[1]+chunks[0]<= 152) & (chunks[1]>=48)){
                    //check for third character
                    if((chunks[2]>='A' & chunks[2]<='Z')|(chunks[2]>='0' & chunks[2]<='9')){
                        //check in right range and also number possibilities
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                else {return false;} //if second not in range
            }

            else if (chunks[0]=='z'){
                if(chunks[1]=='9'){
                    if((chunks[2]>='A' & chunks[2]<='Z')|(chunks[2]>='0' & chunks[2]<='9')){
                        //check in right range and also number possibilities
                        return true;
                    }
                    else {return false;} //if third is not in range
                }
                else{ //if second is not in range
                    return false;
                }
            }

            else { //if first character is not in range
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
     *
     /* @param placement A string describing a placement of one or more cards
     * @return true if the placement is well-formed
     */

    static boolean isPlacementWellFormed(String placement) {
        //Firstly, empty and null string
        if ( placement == null| placement == ""){
            return false;
        }
        else { // 3*N format and each atom is well-formed  *第一、第二条完成*
            char[] chunks = placement.toCharArray();

            //checks for incomplete placement strings
            if (chunks.length%3 != 0){
                return false;
            }

            for (int i = 0; i < chunks.length/3; i++) {
                if (chunks[3*i] >= 'a' & chunks[3*i] <= 'g') {
                    //check for second character
                    if ((chunks[3*i + 1] <= '7') & (chunks[3*i + 1] >= '0')) {
                        //check for third character
                        if ((chunks[3*i + 2] >= 'A' & chunks[3*i + 2] <= 'Z') | (chunks[3*i + 2] >= '0' & chunks[3*i + 2] <= '9')) {
                            //check in right range and also number possibilities
                            //return true;
                            //Duplication check
                            //No same card
                           break;

                        } else {
                            return false;
                        }
                    } else { //if second is not in range
                        return false;
                    }
                } else if (chunks[3*i] == 'z'&chunks[3*i + 1] == '9') {
                        if ((chunks[3*i + 2] >= 'A' & chunks[3*i + 2] <= 'Z') | (chunks[3*i + 2] >= '0' & chunks[3*i + 2] <= '9')) {
                            break;
                            //return true; //check in right range and also number possibilities
                        } else { //if third is not in range
                            return false;
                        }

                } else { //if first character is not in range
                    return false;
                }
            }return true;
            /*String [] arrcard = new String[36];
            String [] arrposi = new String[36];

            for (int j = 0; j<placement.length()/3; j++){
                arrcard [j] = placement.substring(j*3,j*3+2);
                arrposi [j] = placement.substring(j*3+2,j*3+3);
            }
            Arrays.sort(arrcard);
            Arrays.sort(arrposi);
            for (int k = 0; k<placement.length()/3;k++){
                if (arrcard[k]==arrcard[k+1]|arrposi[k]==arrposi[k+1]){
                    return false;
                }else{
                    return true;
                }
            }*/

            /*for (int j = 0; j < chunks.length/3; j++){
                for (int k = j + 1; k < chunks.length/3; k++){
                    if (chunks[j * 3]==chunks[k * 3] & chunks[j * 3 +1]==chunks[k * 3 +1]){
                        return false;
                    }
                    else{ //No same location
                        for (int m = 0; m < chunks.length/3; m++){
                            for (int n = 0; n < chunks.length/3; n++){
                                if (chunks[3*m + 2] == chunks[3*n + 2]){
                                    return true;
                                }else {
                                    return true;
                                }

                            }
                        }

                    }

                }
            }*/
        }
    //return false;
    }

    /**
     * Determine whether a given move is legal given a provided valid placement:
     * - the location char is in the range A .. Z or 0..9
     * - there is a card at the chosen location;
     * - the location is in the same row or column of the grid as Zhang Yi's current position; and
     * - drawing a line from Zhang Yi's current location through the card at the chosen location,
     *   there are no other cards along the line from the same kingdom as the chosen card
     *   that are further away from Zhang Yi.
     * @param placement    the current placement string
     * @param locationChar a location for Zhang Yi to move to
     * @return true if Zhang Yi may move to that location
     */
    public static boolean isMoveLegal(String placement, char locationChar) {
        // FIXME Task 5: determine whether a given move is legal
        return false;
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
        return false;
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
     * @param placement the current placement string
     * @return a location character representing Zhang Yi's destination for the move
     */
    public static char generateMove(String placement) {
        // FIXME Task 10: generate a legal move
        return '\0';
    }
}
