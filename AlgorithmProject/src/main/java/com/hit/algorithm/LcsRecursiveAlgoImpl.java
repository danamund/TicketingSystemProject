package com.hit.algorithm;

public class LcsRecursiveAlgoImpl implements IAlgoStringMatching{

    @Override
    public int getCommonLength(String text1, String text2) {
        return calculateRecursive(text1, text2, text1.length(), text2.length());
    }

    private int calculateRecursive(String text1, String text2, int m, int n) {


        if (m == 0 || n == 0) {
            return 0;
        }

        if (text1.charAt(m - 1) == text2.charAt(n - 1)) {
            return 1 + calculateRecursive(text1, text2, m - 1, n - 1);
        } else {

            return Math.max(
                    calculateRecursive(text1, text2, m, n - 1),
                    calculateRecursive(text1, text2, m - 1, n)
            );
        }
    }
}