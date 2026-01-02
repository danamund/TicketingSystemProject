package com.hit.algorithm;

import org.junit.Assert;
import org.junit.Test;

public class IAlgoStringMatchingTest {


    @Test
    public void testDynamicAlgoBasic() {
        IAlgoStringMatching dynamicAlgo = new LcsDynamicAlgoImpl();
        String text1 = "stone";
        String text2 = "longest";
        Assert.assertEquals(3, dynamicAlgo.getCommonLength(text1, text2));
    }

    @Test
    public void testDynamicAlgoIdentical() {
        IAlgoStringMatching dynamicAlgo = new LcsDynamicAlgoImpl();
        Assert.assertEquals(5, dynamicAlgo.getCommonLength("hello", "hello"));
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
}