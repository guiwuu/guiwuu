package com.guiwuu.jumd;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import com.guiwuu.jumd.util.UmdUtil;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * This is the cover part of the UMD file.
 * <P>
 * NOTICE: if the "coverData" is empty, it will be skipped when building UMD file.
 * </P>
 * There are 3 ways to load the image data:
 * <ol>
 *     <li>new constructor function of UmdCover.</li>
 *     <li>use UmdCover.load function.</li>
 *     <li>use UmdCover.initDefaultCover, it will generate a simple image with text.</li>
 * </ol>
 *
 * @author Ray Liang (liangguanhui@qq.com), daijun
 * @since 2009-12-20
 * @version 2011-02-19
 */
public class UmdCover {

    public static final int DEFAULT_COVER_WIDTH = 120;
    public static final int DEFAULT_COVER_HEIGHT = 160;
    private byte[] data;

    public UmdCover() {
    }

    public UmdCover(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
