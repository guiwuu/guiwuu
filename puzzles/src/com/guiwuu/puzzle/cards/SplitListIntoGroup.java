package com.guiwuu.puzzle.cards;

import java.util.Arrays;

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
 * @since 10/9/13
 */
public class SplitListIntoGroup {

    public static void main(String[] args) {
        test("AAABBBCCC", 6);
        test("AAABCDEFUXYZ", 6);
        test("AAABBBCCCDDDEEEFFFGGGHHH", 3);
    }

    static void test(String input, int expect) {
        if (SplitLetterList(input) != expect) {
            throw new AssertionError();
        }
    }

    private static final char TOKEN_BEGIN = 'b';

    private static final char TOKEN_NEGATIVE = 'n';

    private static final char TOKEN_EQUAL = 'e';

    private static final char TOKEN_CONSECUTIVE = 'c';

    private static final char TOKEN_JUMP = 'j';

    private static final int CONSECUTIVE_LIMIT = 5;

    static int SplitLetterList(String s) {
        String prepared = prepare(s);
        return split(prepared);
    }

    private static int split(String prepared) {
        int len = prepared.length();
        if (len == 0) {
            return 0;
        } else if (len == 1) {// meets rule 1
            return 1;
        }

        // check first two characters whether meets any rule or not
        char second = prepared.charAt(1);

        // meets no rule, which means the first itself makes a group, and recursively split the rest
        if (second == TOKEN_JUMP) {
            return 1 + split(prepared.substring(1));
        }

        // only two possible cases exist, equal or consecutive
        int equalSplits = Integer.MAX_VALUE;
        int consecutiveSplits = Integer.MAX_VALUE;

        // meets rule2, try making the first two a group and recursively split the rest
        if (second == TOKEN_EQUAL) {
            equalSplits = 1 + split(prepared.substring(2));
        }

        // meets rule 2 only, return the result
        if (equalSplits < Integer.MAX_VALUE && len < CONSECUTIVE_LIMIT) {
            return equalSplits;
        }

        StringBuilder sb = new StringBuilder();
        int consecutiveCount = 1;
        boolean isBreak = false;// ending flag of finding consecutive characters
        for (int i = 1; i < len; i++) {
            char c = prepared.charAt(i);
            if (isBreak) {
                sb.append(c);
            } else if (c == TOKEN_JUMP) {// end of finding
                sb.append(c);
                isBreak = true;
            } else if (c == TOKEN_EQUAL) {// equal character, append the former
                sb.append(prepared.charAt(i - 1));
            } else if (c == TOKEN_CONSECUTIVE) {// finds a consecutive character
                consecutiveCount++;
            }
        }

        // meets rule3, try make these consecutive characters a group and recursively split the rest
        if (consecutiveCount >= CONSECUTIVE_LIMIT) {
            consecutiveSplits = 1 + split(sb.toString());
        }

        // meets no rule, which means the first itself makes a group, and recursively split the rest
        if (equalSplits == Integer.MAX_VALUE && consecutiveSplits == Integer.MAX_VALUE) {
            return 1 + split(prepared.substring(1));
        }
        return Math.min(equalSplits, consecutiveSplits);// return smaller number of two split solutions
    }

    private static String prepare(String input) {
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
            if (i == 0) {
                sb.append(TOKEN_BEGIN);
            } else {
                int diff = c - chars[i - 1];
                if (diff < 0) {
                    sb.append(TOKEN_NEGATIVE);
                } else if (diff == 0) {
                    sb.append(TOKEN_EQUAL);
                } else if (diff == 1) {
                    sb.append(TOKEN_CONSECUTIVE);
                } else {
                    sb.append(TOKEN_JUMP);
                }
            }
        }
        return sb.toString();
    }
}
