package comp1110.ass2;

import comp1110.ass2.SubOperationCheck;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static comp1110.ass2.TestUtility.*;

public class iTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(2000);

    @Test
    public void getPlayerFlagsNumber() {
        int[] flags1 = new int[] {1,2,3,0,1,2,3};
        int[] nums1 = new int[] {1,2,2,2};
        int[] flags2 = new int[] {1,0,1,1,0,1,0};
        int[] nums2 = new int[] {3,4,0,0};
        assertTrue("Get all individual flags numbers correct, but fail the test", SubOperationCheck.playerFlagNumbers(flags1) == nums1);
        assertTrue("Get all individual flags numbers correct, but fail the test", SubOperationCheck.playerFlagNumbers(flags2) == nums2);
    }




}
