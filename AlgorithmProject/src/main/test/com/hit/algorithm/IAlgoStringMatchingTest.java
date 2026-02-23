package com.hit.algorithm;

import org.junit.Assert;
import org.junit.Test;

public class IAlgoStringMatchingTest {

    // --- בדיקות לאלגוריתם הדינאמי ---

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

    // --- בדיקות לאלגוריתם הרקורסיבי ---

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
        // בדיקה של מחרוזות קצת יותר ארוכות עם אותיות מפוזרות
        Assert.assertEquals(2, recursiveAlgo.getCommonLength("aggtab", "gxtxayb"));
    }
}