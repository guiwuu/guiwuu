package com.guiwuu.puzzle.amazon;

/**
 * Give you a list of uppercase letters (from 'A' to 'Z'), you have to split them into groups based on below rules:
 * Rule1: a single letter (e.g., 'A', 'B', etc.)
 * Rule2: two identical letter (e.g., 'AA', 'BB')
 * Rule3: at least five or more consecutive characters (e.g., 'ABCDE', 'ABCDEFGH', 'UVWXYZ', etc.)
 *
 * You can split the list in different ways, and in each way you can get a number of groups. You should find a way to
 * split the list into the smallest number of groups, and return that number;
 *
 * @auther diancang
 * @since 10/8/13
 */
public class SplitCount {

    private static final char TOKEN_BEGIN = 'a';

    private static final char TOKEN_NEGATIVE = 'b';

    private static final char TOKEN_EQUAL = 'c';

    private static final char TOKEN_CONSECUTIVE = 'd';

    private static final char TOKEN_JUMP = 'e';

    public static void main(String[] args) {
        test("AAABBBCCC", 6);
        test("AAABCDEFUXYZ", 6);
        test("AAABABCDEFUTTTTXYZ", 10);
    }

    private static void test(String input, int expect) {
        if (splitCount1(input) != expect) {
            throw new AssertionError();
        } else if (splitCount2(input) != expect) {
            throw new AssertionError();
        }
    }

    private static int splitCount1(String input) {
        String prepared = prepare(input);

        // apply rule3
        prepared = prepared.replaceAll("." + TOKEN_CONSECUTIVE + "{4,}", ",");

        // apply rule2
        prepared = prepared.replaceAll("." + TOKEN_EQUAL, ",");
        return prepared.length();
    }

    private static int splitCount2(String input) {
        String prepared = prepare(input);
        int count = 0;
        for (int i = 0; i < prepared.length();count++) {
            int next = i + 1;// next position to input string
            if (next < prepared.length()) {
                char c = prepared.charAt(next);

                // count consecutive number
                int consecutiveNum = 0;
                while (c == TOKEN_CONSECUTIVE && next + consecutiveNum < prepared.length()-1) {
                    consecutiveNum++;
                    c = prepared.charAt(next + consecutiveNum);
                }

                if (consecutiveNum >= 4) {// use rule3 first
                    next += consecutiveNum;
                } else if (prepared.charAt(next) == TOKEN_EQUAL) {// then rule2
                    next++;
                }
            }
            i = next;
        }
        return count;
    }

    private static String prepare(String input) {
        if (input == null) {
            throw new NullPointerException("Input is null!");
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c < 'A' || c > 'Z') {
                throw new IllegalArgumentException("Found a non-uppercase letter at index: " + i);
            }
            if (i == 0) {
                sb.append(TOKEN_BEGIN);
            } else {
                int diff = c - input.charAt(i - 1);
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
