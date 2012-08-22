package com.guiwuu.swissknife.converter.jumd;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;

/**
 * UMD utility
 *
 * @author Ray Liang (liangguanhui@qq.com), daijun
 * @since 2009-12-20
 * @version 2011-02-21
 */
public class UmdUtil {

    private static final Logger log = Logger.getLogger(UmdUtil.class.getName());
    private static final Random random = new Random();

    public static byte[] unicode(String s) {
        if (s == null) {
            throw new NullPointerException();
        }

        int len = s.length();
        byte[] ret = new byte[len * 2];
        int a, b, c;
        for (int i = 0; i < len; i++) {
            c = s.charAt(i);
            a = c >> 8;// high bits
            b = c & 0xFF;// low bits
            if (a < 0) {
                a += 0xFF;
            }
            if (b < 0) {
                b += 0xFF;
            }
            ret[i * 2] = (byte) b;
            ret[i * 2 + 1] = (byte) a;
        }
        return ret;
    }

    public static String unicode(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        int len = bytes.length / 2;
        for (int i = 0; i < len; i++) {
            char c = 0;
            c |= bytes[i * 2] & 0xFF;
            c |= bytes[i * 2 + 1] << 8;
            sb.append(c);
        }
        return sb.toString();
    }

    public static void saveFile(File f, byte[] content) throws IOException {
        FileOutputStream fos = new FileOutputStream(f);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bos.write(content);
            bos.flush();
        } finally {
            fos.close();
        }
    }

    public static byte[] readFile(File f) throws IOException {
        FileInputStream fis = new FileInputStream(f);
        try {
            return readBytes(fis);
        } finally {
            fis.close();
        }
    }

    public static byte[] readBytes(InputStream is) throws IOException {
        try {
            BufferedInputStream bis = new BufferedInputStream(is);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int ch;
            while ((ch = bis.read()) >= 0) {
                bos.write(ch);
            }
            bos.flush();
            return bos.toByteArray();
        } finally {
            is.close();
        }
    }

    public static byte[] genRandomBytes(int len) {
        if (len <= 0) {
            throw new IllegalArgumentException("Length must > 0: " + len);
        }
        byte[] ret = new byte[len];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = (byte) random.nextInt(256);
        }
        return ret;
    }

    public static byte[] appendRandomBytes(int len, byte... b) {
        byte[] ret = new byte[len + b.length];
        System.arraycopy(b, 0, ret, 0, b.length);
        System.arraycopy(genRandomBytes(len), 0, ret, b.length, len);
        return ret;
    }

    public static int bytesToInt(byte... bytes) {
        int i = bytes[0] & 0xFF;
        if (bytes.length > 1) {
            i |= bytes[1] << 8 & 0xFFFF;
        }
        if (bytes.length > 2) {
            i |= bytes[2] << 16 & 0xFFFFFF;
        }
        if (bytes.length > 3) {
            i |= bytes[3] << 24;
        }
        return i;
    }

    public static int[] bytesToInts(byte... bytes) {
        int len = bytes.length / 4;
        int[] ints = new int[len];
        for (int i = 0; i < len; i++) {
            byte[] b = new byte[4];
            for (int j = 0; j < 4; j++) {
                b[j] = bytes[i * 4 + j];
            }
            ints[i] = bytesToInt(b);
        }
        return ints;
    }

    public static byte[] intToBytes(int i) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (i & 0xFF);
        bytes[1] = (byte) (i >>> 8 & 0xFF);
        bytes[2] = (byte) (i >>> 16 & 0xFF);
        bytes[3] = (byte) (i >>> 24 & 0xFF);
        return bytes;
    }

    public static byte[] intsToBytes(int[] ints) {
        int len = ints.length;
        byte[] bytes = new byte[len * 4];
        for (int i = 0; i < len; i++) {
            byte[] b = intToBytes(ints[i]);
            System.arraycopy(b, 0, bytes, i * 4, 4);
        }
        return bytes;
    }

    public static byte[] intsToBytes(List<Integer> ints) {
        int[] array = new int[ints.size()];
        for (int i = 0; i < ints.size(); i++) {
            array[i] = ints.get(i);
        }
        return intsToBytes(array);
    }

    public static byte[] zip(byte[] bytes, int offset, int len) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(len + 256);
        DeflaterOutputStream zos = new DeflaterOutputStream(bos);
        zos.write(bytes, offset, len);
        zos.close();
        return bos.toByteArray();
    }

    public static byte[] unzip(byte[] bytes) {
        try {
            Inflater inflater = new Inflater();
            inflater.setInput(bytes);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(bytes.length);
            byte[] buf = new byte[8192];
            int got;
            while (!inflater.finished()) {
                got = inflater.inflate(buf);
                bos.write(buf, 0, got);
            }
            bos.flush();
            return bos.toByteArray();
        } catch (IOException e) {
            log.log(Level.SEVERE, "error occurs in unzipping", e);
        } catch (DataFormatException ex) {
            log.log(Level.SEVERE, "error occurs in unzipping", ex);
        }
        return null;
    }

    public static byte[] genImage(String title, int width, int height) throws IOException {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, img.getWidth(), img.getHeight());
        g.setColor(Color.WHITE);
        g.setFont(new Font("����", Font.PLAIN, 12));

        FontMetrics fm = g.getFontMetrics();
        int ascent = fm.getAscent();
        int descent = fm.getDescent();
        int strWidth = fm.stringWidth(title);
        int x = (img.getWidth() - strWidth) / 2;
        int y = (img.getHeight() - ascent - descent) / 2;
        g.drawString(title, x, y);
        g.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(baos);
        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(img);
        param.setQuality(0.5f, false);
        encoder.setJPEGEncodeParam(param);
        encoder.encode(img);

        return baos.toByteArray();
    }
}
