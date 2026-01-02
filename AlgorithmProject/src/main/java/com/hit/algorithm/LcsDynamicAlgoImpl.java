package com.hit.algorithm;

public class LcsDynamicAlgoImpl implements IAlgoStringMatching {

    @Override
    public int getCommonLength(String text1, String text2) {
        return calculateDynamic(text1, text2, text1.length(), text2.length());
    }

    private int calculateDynamic(String text1, String text2, int m, int n) {
        int combinations[][] = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 || j == 0)
                    combinations[i][j] = 0;
                else if (text1.charAt(i - 1) == text2.charAt(j - 1))
                    combinations[i][j] = combinations[i - 1][j - 1] + 1;
                else
                    combinations[i][j] = Math.max(combinations[i - 1][j], combinations[i][j - 1]);
            }
        }
        return combinations[m][n];
    }
}
