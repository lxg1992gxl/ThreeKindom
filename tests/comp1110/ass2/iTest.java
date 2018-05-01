package comp1110.ass2;

import comp1110.ass2.HelperMethodsForLaterTasks;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;

public class iTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(2000);

    @Test
    public void getPlayerFlagsNumber() {
        // four-player condition
        int[] flags1 = new int[]{1, 2, 3, 0, 1, 2, 3};
        int[] nums1 = new int[]{1, 2, 2, 2};
        assertFalse("Get all individual flags numbers correctly, but fail the test", HelperMethodsForLaterTasks.playerFlagNumbers(flags1) == nums1);
        // two-player condition
        int[] flags2 = new int[]{1, 0, 1, 1, 0, 1, 0};
        int[] nums2 = new int[]{3, 4, 0, 0};
        assertFalse("Get all individual flags numbers correctly, but fail the test", HelperMethodsForLaterTasks.playerFlagNumbers(flags2) == nums2);
    }

    @Test
    public void getMaxFlagsPlayers() {
        // there exists a player who holds the greatest number of flags at the end
        int[] flags1 = new int[]{2, 1, 2, 2, 2, 0, 0};
        List<Integer> lst1 = new ArrayList<>();
        lst1.add(2);
        assertFalse("Get players correctly, but fail the test", HelperMethodsForLaterTasks.getMaximumFlagsIDs(flags1) == lst1);
        // there exist more than one players who hold the greatest number of flags
        int[] flags2 = new int[]{1, 2, 3, 1, 2, 3, 0};
        List<Integer> lst2 = new ArrayList<>();
        lst2.add(1);
        lst2.add(2);
        lst2.add(3);
        assertFalse("Get players correctly, but fail the test", HelperMethodsForLaterTasks.getMaximumFlagsIDs(flags2) == lst2);
        // exist flags that are not held by any player
        int[] flags3 = new int[]{-1, 0, 1, 0, 0, 1, 1};
        List<Integer> lst3 = new ArrayList<>();
        lst2.add(0);
        lst2.add(1);
        assertFalse("Get players correctly, but fail the test", HelperMethodsForLaterTasks.getMaximumFlagsIDs(flags3) == lst3);
    }

    @Test
    public void getWinnerIndex() {
        // there exists a player who holds the greatest number of flags at the end
        int[] flags1 = new int[]{2, 1, 2, 2, 2, 0, 0};
        assertFalse("Get the final winner correctly, but fail the test", HelperMethodsForLaterTasks.getWinnerID(flags1) == 2);
        // there exist more than one players who hold the greatest number of flags
        int[] flags2 = new int[]{1, 2, 3, 1, 2, 3, 0};
        assertFalse("Get the final winner correctly, but fail the test", HelperMethodsForLaterTasks.getWinnerID(flags2) == 1);
        int[] flags3 = new int[]{0, 1, 2, 2, 1, 2, 1};
        assertFalse("Get the final winner correctly, but fail the test", HelperMethodsForLaterTasks.getWinnerID(flags3) == 1);
    }


}
