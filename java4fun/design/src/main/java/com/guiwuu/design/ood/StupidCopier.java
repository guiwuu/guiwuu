package com.guiwuu.design.ood;

import java.io.IOException;

public class StupidCopier {

    public enum InputDevice {

        STDIN, FILE
    }

    public enum OutputDevice {

        STDOUT, PRINTER
    }

    public static void main(String[] args) throws IOException {
        int ch;
        InputDevice in;
        OutputDevice out;

        try {
            in = InputDevice.valueOf(args[0]);
            out = OutputDevice.valueOf(args[1]);
        } catch (Exception e) {
            in = InputDevice.STDIN;   // 默认值
            out = OutputDevice.STDOUT; // 默认值
        }

        while ((ch = read(in)) >= 0) {
            write(out, (char) ch);
        }
    }

    private static int read(InputDevice in) throws IOException {
        switch (in) {
            case STDIN:
                return System.in.read();
            case FILE:
                return readFromFile();
            default:
                return Integer.MIN_VALUE;
        }
    }

    private static int readFromFile() {
        // TODO Auto-generated method stub
        return 0;
    }

    private static void write(OutputDevice out, char ch) {
        switch (out) {
            case STDOUT:
                System.out.print(ch);
                break;
            case PRINTER:
                writeToPrinter(ch);
                break;
        }
    }

    private static void writeToPrinter(char ch) {
        // TODO Auto-generated method stub
    }
}