package com.guiwuu.puzzle.amazon;

import java.util.Arrays;

/**
 * We have a 14 cards in hand, every card marks a letter from A, B, C,...,L, M, N. Your task is to discard cards from
 * them according to some rules, and try to discard all the cards in hand.
 * Rule 1: We MUST discard a pair of cards with same letter. for example, B,B or C,C, etc.
 * Rule 2: We can discard three adjacent cards. for example, A,B,C or B,C,D or G,H,I, etc.
 *
 * Please note, once the cards have been discarded, you could not use them again. Then we have a method need you to
 * implement, the input is an array of cards in hand, the output is whether those cards can be all discard or not.
 * Return 1 means we can discard all cards in hand, otherwise return 0.
 *
 * @auther diancang
 * @since 10/8/13
 */
public class Majiang {

    private static final char TOKEN_BEGIN = 'a';

    private static final char TOKEN_EQUAL = 'c';

    private static final char TOKEN_CONSECUTIVE = 'd';

    private static final char TOKEN_JUMP = 'e';

    private static final int CARDS = 14;

    public static void main(String[] args) {
        test("AABCDDEFGHILMN", 1);
    }

    private static void test(String input, int expect) {
        if (hu(input) != expect) {
            throw new AssertionError();
        }
    }

    private static int hu(String input) {
        String prepared = prepare(input);
        return isHu(prepared) ? 1 : 0;
    }

    private static boolean isHu(String prepared) {
        int len = prepared.length();
        if (len == 0) {
            return true;
        } else if (len == 1) {
            return false;
        }

        // check the first two cards
        char second = prepared.charAt(1);
        if (second == TOKEN_JUMP) {// they are not consecutive or equal
            return false;
        }

        // if they are equal
        if (second == TOKEN_EQUAL) {// recursively check the rest meets rules or not
            return isHu(prepared.substring(2));
        }

        // or they are consecutive, but only two consecutive cards is not sufficient
        if (len == 2) {
            return false;
        }

        StringBuilder sb = new StringBuilder();
        int p = -1;// position to the third consecutive card
        for (int i = 2; i < len;i++){// starting searching from third card
            char c = prepared.charAt(i);
            if (p > 0) {// adds to rest string
                sb.append(c);
            } else if (c == TOKEN_EQUAL) {// adds to rest string
                sb.append(c);
            } else if (c == TOKEN_CONSECUTIVE) {// found the third one
                p = i;
            } else {// not consecutive any more
                return false;
            }
        }

        // still not found third consecutive card
        if (p == -1) {
            return false;
        }

        // recursively check the rest meets rules or not
        return isHu(sb.toString());
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
            if (c < 'A' || c > 'N') {
                throw new IllegalArgumentException("Found an invalid card at index: " + i);
            }
            if (i == 0) {
                sb.append(TOKEN_BEGIN);
            } else {
                int diff = c - chars[i - 1];
                if (diff == 0) {
                    sb.append(TOKEN_EQUAL);
                } else if (diff == 1) {
                    sb.append(TOKEN_CONSECUTIVE);
                } else {
                    sb.append(TOKEN_JUMP);
                }
            }
        }

        if (sb.length() != CARDS) {
            throw  new IllegalArgumentException("Not enough cards!");
        }
        return sb.toString();
    }
}
