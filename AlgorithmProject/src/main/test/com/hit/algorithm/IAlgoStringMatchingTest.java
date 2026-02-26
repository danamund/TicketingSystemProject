package com.hit.algorithm;

import org.junit.Assert;
import org.junit.Test;
import java.util.*;

public class IAlgoStringMatchingTest {


    @Test
    public void testDynamicAlgoBasic() {
        IAlgoStringMatching dynamicAlgo = new LcsDynamicAlgoImpl();
        Assert.assertEquals(3, dynamicAlgo.getCommonLength("stone", "longest"));
    }

    @Test
    public void testDynamicAlgoIdentical() {
        IAlgoStringMatching dynamicAlgo = new LcsDynamicAlgoImpl();
        Assert.assertEquals(5, dynamicAlgo.getCommonLength("hello", "hello"));
    }

    @Test
    public void testDynamicAlgoNoMatch() {
        IAlgoStringMatching dynamicAlgo = new LcsDynamicAlgoImpl();
        // בדיקה שאין אותיות משותפות - חשוב לכיסוי מלא
        Assert.assertEquals(0, dynamicAlgo.getCommonLength("abc", "xyz"));
    }


    @Test
    public void testRecursiveAlgoEmpty() {
        IAlgoStringMatching recursiveAlgo = new LcsRecursiveAlgoImpl();
        Assert.assertEquals(0, recursiveAlgo.getCommonLength("", "abc"));
    }

    @Test
    public void testRecursiveAlgoPartial() {
        IAlgoStringMatching recursiveAlgo = new LcsRecursiveAlgoImpl();
        Assert.assertEquals(4, recursiveAlgo.getCommonLength("Fish", "FishIsHere"));
    }

    @Test
    public void testRecursiveAlgoLongStrings() {
        IAlgoStringMatching recursiveAlgo = new LcsRecursiveAlgoImpl();
        Assert.assertEquals(4, recursiveAlgo.getCommonLength("aggtab", "gxtxayb"));
    }
}