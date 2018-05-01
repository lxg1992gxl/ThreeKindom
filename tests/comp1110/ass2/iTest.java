package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static junit.framework.TestCase.assertTrue;

public class iTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(2000);

    @Test
    public void getPlayerFlagsNumber() {
        // four-player condition (should get "int[] {1,2,2,2}")
        int[] flags1 = new int[]{1, 2, 3, 1, 2, 3, 0};
        assertTrue("should get individual flag number correctly, but was rejected", HelperMethodsForLaterTasks.playerFlagNumbers(flags1)[0] == 1);
        assertTrue("should get individual flag number correctly, but was rejected", HelperMethodsForLaterTasks.playerFlagNumbers(flags1)[1] == 2);
        // two-player condition (should get "int[]{3,3,0,0}")
        int[] flags2 = new int[]{-1, 0, 1, 0, 0, 1, 1};
        assertTrue("should get individual flag number correctly, but was rejected", HelperMethodsForLaterTasks.playerFlagNumbers(flags2)[1] == 3);
        assertTrue("should get individual flag number correctly, but was rejected", HelperMethodsForLaterTasks.playerFlagNumbers(flags2)[2] == 0);
    }

    @Test
    public void getMaxFlagsPlayers() {
        // there exists a player who holds the greatest number of flags at the end
        int[] flags1 = new int[]{2, 1, 2, 2, 2, 0, 0};
        // (should get "int[] {-1,-1,2,-1}")
        assertTrue("should get players correctly, but was rejected", HelperMethodsForLaterTasks.getMaximumFlagsIDs(flags1)[0] == -1);
        assertTrue("should get players correctly, but was rejected", HelperMethodsForLaterTasks.getMaximumFlagsIDs(flags1)[3] == -1);
        assertTrue("should get players correctly, but was rejected", HelperMethodsForLaterTasks.getMaximumFlagsIDs(flags1)[2] == 2);
        // there exist more than one players who hold the greatest number of flags
        int[] flags2 = new int[]{1, 2, 3, 1, 2, 3, 0};
        // (should get "int[] {-1,1,2,3}")
        assertTrue("should get players correctly, but was rejected", HelperMethodsForLaterTasks.getMaximumFlagsIDs(flags2)[0] == -1);
        assertTrue("should get players correctly, but was rejected", HelperMethodsForLaterTasks.getMaximumFlagsIDs(flags2)[2] == 2);
        assertTrue("should get players correctly, but was rejected", HelperMethodsForLaterTasks.getMaximumFlagsIDs(flags2)[3] == 3);
        // exist flags that are not held by any player
        int[] flags3 = new int[]{-1, 0, 1, 0, 0, 1, 1};
        // (should get "int[] {0,1,-1,-1}")
        assertTrue("should get players correctly, but was rejected", HelperMethodsForLaterTasks.getMaximumFlagsIDs(flags3)[1] == 1);
    }

    @Test
    public void getWinnerIndex() {
        // there exists a player who holds the greatest number of flags at the end
        int[] flags1 = new int[]{2, 1, 2, 2, 2, 0, 0};
        int winner1 = 2;
        assertTrue("should get the final winner correctly, but was rejected", HelperMethodsForLaterTasks.getWinnerID(flags1) == winner1);
        // there exist more than one players who hold the greatest number of flags
        int[] flags2 = new int[]{1, 2, 3, 1, 2, 3, 0};
        int winner2 = 1;
        assertTrue("should get the final winner correctly, but was rejected", HelperMethodsForLaterTasks.getWinnerID(flags2) == winner2);
        int[] flags3 = new int[]{0, 2, 1, 1, 2, 1, 2};
        int winner3 = 2;
        assertTrue("should get the final winner correctly, but was rejected", HelperMethodsForLaterTasks.getWinnerID(flags3) == winner3);
    }

}
