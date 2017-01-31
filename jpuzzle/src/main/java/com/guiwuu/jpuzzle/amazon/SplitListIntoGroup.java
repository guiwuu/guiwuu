package com.guiwuu.jpuzzle.amazon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Give you a list of uppercase letters (from 'A' to 'Z'), you have to split them into groups based on below rules:
 * Rule 1: a single letter (e.g., 'A', 'B', etc.)
 * Rule 2: two identical letters (e.g., 'AA', 'BB', etc.)
 * Rule 3: at least five or more consecutive characters (e.g., 'ABCDE', 'ABCDEFGH', 'UVWXYZ', etc.)
 * <p/>
 * You can split the list in different ways, and in each way you can get a number of groups. You should find a way
 * to split the list into the smallest number of groups, and return that number.
 * <p/>
 * ---------------------------------------------------------------------------------------------------
 * <p/>
 * Example 1:
 * Input: AAABBBCCC
 * Output: 6
 * <p/>
 * Some split examples:
 * 1. A AA B BB C CC
 * 2. A A A B BB C CC
 * 3. A A A B B B C C C
 * <p/>
 * Example 2:
 * Input: AAABCDEFUXYZ
 * Output: 6
 * <p/>
 * Some split examples:
 * 1. AA ABCDEF U X Y Z
 * 2. AA ABCDE F U X Y Z
 * 3. A A ABCDE F U X Y Z
 *
 * @auther diancang
 * @since 2013-10-09
 */
public class SplitListIntoGroup {

    public static void main(String[] args) {
        test("AAABBBCCC", 6);
        test("AAABCDEFUXYZ", 6);
        test("AAABBBCCCDDDEEEFFFGGGHHH", 3);
        test("ABCDEEFFGHIJK", 2);
    }

    static void test(String input, int expect) {
        if (SplitLetterList(input) != expect) {
            throw new AssertionError();
        }
    }

    private static final char TOKEN_SPLIT = ',';

    private static final char TOKEN_BEGIN = 'b';

    private static final char TOKEN_NEGATIVE = 'n';

    private static final char TOKEN_EQUAL = 'e';

    private static final char TOKEN_CONSECUTIVE = 'c';

    private static final char TOKEN_JUMP = 'j';

    private static final int CONSECUTIVE_LIMIT = 5;

    static int SplitLetterList(String s) {
        int count = 0;
        for (String sub : prepare(s)) {
            SplitResult result = split(sub);
            count+=result.sum();
        }
        return count;
    }

    private static SplitResult split(String sub) {
        SplitResult result = new SplitResult();
        SplitRuleResult ruleResult = new SplitRuleResult();
        return result;
    }

    static class SplitResult {
        private int rule1 = 0;
        private int rule2 = 0;
        private int rule3 = 0;

        public int sum() {
            return rule1 + rule2 + rule3;
        }
    }

    static class SplitRuleResult {
        private int splitCount;
        private String rest;
    }

    private static String[] prepare(String input) {
        if (input == null) {
            throw new NullPointerException("Input is null!");
        }

        char[] chars = input.toCharArray();
        Arrays.sort(chars);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c < 'A' || c > 'Z') {
                throw new IllegalArgumentException("Found a non-uppercase letter at index: " + i);
            }
            sb.append(c);
            if (i > 0) {
               int diff = chars[i+1] - c;
               if (diff > 1) {
                   sb.append(TOKEN_SPLIT);
               }
            }
        }
        return sb.toString().split(TOKEN_SPLIT+"");
    }
}
