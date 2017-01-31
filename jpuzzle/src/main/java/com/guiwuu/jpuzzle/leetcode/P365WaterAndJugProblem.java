package com.guiwuu.jpuzzle.leetcode;

import java.util.*;

/**
 * 365. Water and Jug Problem
 * https://leetcode.com/problems/water-and-jug-problem/
 *
 * @author guiwuu
 * @since 2017-01-31
 */
public class P365WaterAndJugProblem {

    private int x, y;

    public boolean canMeasureWater(int x, int y, int z) {
        if (x <= y) {
            this.x = x;
            this.y = y;
        } else {
            this.x = y;
            this.y = x;
        }

        Stack<Point> stack = new Stack<Point>();
        Set<Point> states = new LinkedHashSet<Point>();

        Point init = new Point(0,0);
        stack.push(init);
        states.add(init);
        while (!stack.isEmpty()){
            Point point = stack.pop();
            if (point.sum() == z) {
                return true;
            }

            Point newPoint = point.fullA();
            if (!states.contains(newPoint)) {
                stack.push(newPoint);
                states.add(newPoint);
            }
            newPoint = point.emptyA();
            if (!states.contains(newPoint)) {
                stack.push(newPoint);
                states.add(newPoint);
            }
            newPoint = point.fullB();
            if (!states.contains(newPoint)) {
                stack.push(newPoint);
                states.add(newPoint);
            }
            newPoint = point.emptyB();
            if (!states.contains(newPoint)) {
                stack.push(newPoint);
                states.add(newPoint);
            }
            newPoint = point.a2B();
            if (!states.contains(newPoint)) {
                stack.push(newPoint);
                states.add(newPoint);
            }
            newPoint = point.b2A();
            if (!states.contains(newPoint)) {
                stack.push(newPoint);
                states.add(newPoint);
            }
        }
        return false;
    }

    class Point {

        final int a;

        final int b;

        Point(int a, int b) {
            this.a=a;
            this.b=b;
        }

        int sum() {
            return a + b;
        }

        Point fullA() {
            return new Point(x, b);
        }

        Point emptyA() {
            return new Point(0, b);
        }

        Point fullB() {
            return new Point(a, y);
        }

        Point emptyB() {
            return new Point(a, 0);
        }

        Point a2B() {
            int c = a + b;
            if (c > y) {
                return new Point(a-(y-b), y);
            } else {
                return new Point(0, c);
            }
        }

        Point b2A() {
            int c = a + b;
            if (c > x) {
                return new Point(x, b-(x-a));
            } else {
                return new Point(c, 0);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Point point = (Point) o;

            if (a != point.a) return false;
            return b == point.b;
        }

        @Override
        public int hashCode() {
            int result = a;
            result = 31 * result + b;
            return result;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "a=" + a +
                    ", b=" + b +
                    '}';
        }
    }
}
