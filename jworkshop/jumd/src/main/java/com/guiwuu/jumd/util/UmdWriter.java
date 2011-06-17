package com.guiwuu.jumd.util;

import static com.guiwuu.jumd.UmdBlockFunc.*;
import static com.guiwuu.jumd.util.UmdUtil.*;

import com.guiwuu.jumd.Umd;
import com.guiwuu.jumd.UmdBlockBase;
import com.guiwuu.jumd.UmdBlockData;
import com.guiwuu.jumd.UmdBlockFunc;
import com.guiwuu.jumd.UmdChapter;
import com.guiwuu.jumd.UmdHeader;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Umd writer
 *
 * @author daijun
 * @since 2011-02-19
 * @version 2011-02-23
 */
public class UmdWriter {

    private static final Logger log = Logger.getLogger(UmdWriter.class.getName());
    private static final int DEFAULT_DATA_CHUNK_SIZE = 32768;
    private byte[] data;
    private OutputStream os;
    private int written;
    private Umd umd;

    public UmdWriter(Umd umd) {
        this.umd = umd;
    }

    public void convertToTxt(File txt) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(txt);
            for (int i = 0; i < umd.sizeOfChapters(); i++) {
                UmdChapter chapter = umd.getChapter(i);
                byte[] bytes = chapter.getContent();
                if (bytes == null) {
                    bytes = new byte[]{};
                }
                fw.write(UmdUtil.unicode(bytes));
            }
            fw.flush();
        } catch (FileNotFoundException ex) {
            log.log(Level.SEVERE, "file not found: " + txt.getAbsolutePath(), ex);
        } catch (IOException ex) {
            log.log(Level.SEVERE, "io error ocurrs in writing umd to file", ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                log.log(Level.SEVERE, "error ocurrs in closing umd file", ex);
            }
        }
    }

    public void extractToFolder(File folder) {
        if (!folder.exists()) {
            folder.mkdirs();
        }
        extractContent(folder);
        extractCover(folder);
    }

    public void writeToUmd(File file) {
        try {
            OutputStream fos = new FileOutputStream(file);
            this.os = new BufferedOutputStream(fos);
            writeBof();
            writeHeader();
            writeContent();
            writeCover();
            writeEof();
            os.flush();
        } catch (FileNotFoundException ex) {
            log.log(Level.SEVERE, "file not found: " + file.getAbsolutePath(), ex);
        } catch (IOException ex) {
            log.log(Level.SEVERE, "io error ocurrs in writing umd to file", ex);
        } finally {
            try {
                os.close();
            } catch (IOException ex) {
                log.log(Level.SEVERE, "error ocurrs in closing umd file", ex);
            }
        }
    }

    private void writeBof() throws IOException {
        writeBytes(Umd.BEGIN);
    }

    private void writeHeader() throws IOException {
        UmdHeader header = umd.getHeader();
        writeBlockFunc(ID_DOC_TYPE, appendRandomBytes(2, (byte) header.getDocType()));
        writeBlockFunc(ID_TITLE, header.getTitle());
        writeBlockFunc(ID_AUTHOR, header.getAuthor());
        writeBlockFunc(ID_YEAR, header.getYear());
        writeBlockFunc(ID_MONTH, header.getMonth());
        writeBlockFunc(ID_DAY, header.getDay());
        writeBlockFunc(ID_GENDER, header.getGender());
        writeBlockFunc(ID_PUBLISHER, header.getPublisher());
        writeBlockFunc(ID_VENDOR, header.getVendor());
    }

    public void writeContent() throws IOException {
        if (umd.sizeOfChapters() == 0) {
            return;
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        for (int i = 0; i < umd.sizeOfChapters(); i++) {
            UmdChapter chapter = umd.getChapter(i);
            byte[] bytes = chapter.getContent();
            if (bytes == null) {
                bytes = new byte[]{};
            }
            bos.write(bytes);
        }
        data = bos.toByteArray();

        writeBlockFunc(ID_CLEN, data.length);
        writeChapterOffsets();
        writeChapterTtiles();
        writeChapterDatas();
    }

    private void writeChapterOffsets() throws IOException {
        int blockId = writeBlockFunc(ID_CHAP_OFFSET);

        int[] offsets;
        int chapterNum = umd.sizeOfChapters();
        if (chapterNum > 0) {
            offsets = new int[chapterNum];
            offsets[0] = 0;
        } else {
            offsets = new int[]{0};
        }
        for (int i = 1; i < chapterNum; i++) {
            int offset = offsets[i - 1];
            offsets[i] = offset + umd.getChapter(i - 1).getContent().length;
        }
        writeBlockData(blockId, intsToBytes(offsets));
    }

    private void writeChapterTtiles() throws IOException {
        int blockId = writeBlockFunc(ID_CHAP_TITLE);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        for (int i = 0; i < umd.sizeOfChapters(); i++) {
            UmdChapter chapter = umd.getChapter(i);
            byte[] bytes = unicode(chapter.getTitle());
            bos.write((byte) bytes.length);
            bos.write(bytes);
        }
        writeBlockData(blockId, bos.toByteArray());
    }

    private void writeChapterDatas() throws IOException {
        int startPos = 0;

        List<Integer> chunkIds = new ArrayList<Integer>();
        while (startPos < data.length) {
            int chunkId = bytesToInt(UmdUtil.genRandomBytes(4));
            chunkIds.add(chunkId);

            int left = data.length - startPos;
            int len = DEFAULT_DATA_CHUNK_SIZE < left ? DEFAULT_DATA_CHUNK_SIZE : left;
            byte[] chunk = zip(data, startPos, len);
            writeBlockData(chunkId, chunk);

            writeBlockFunc(ID_LICENSE, umd.getHeader().getLicense());
            startPos += len;
        }

        int blockId = writeBlockFunc(ID_EOC);
        writeBlockData(blockId, intsToBytes(chunkIds));
    }

    private void writeCover() throws IOException {
        byte[] bytes = umd.getCover().getData();
        if (bytes != null) {
            int blockId = writeBlockFunc(ID_COVER);
            writeBlockData(blockId, bytes);
        }
    }

    private void writeEof() throws IOException {
        byte[] end = new byte[9];
        System.arraycopy(Umd.END, 0, end, 0, Umd.END.length);

        byte[] fileSize = UmdUtil.intToBytes(written + 9);
        System.arraycopy(fileSize, 0, end, Umd.END.length, 4);

        writeBytes(end);
    }

    private void writeBlockFunc(int blockId, int i) throws IOException {
        writeBlockFunc(blockId, intToBytes(i));
    }

    private void writeBlockFunc(int blockId, String content) throws IOException {
        if (content != null) {
            writeBlockFunc(blockId, unicode(content));
        }
    }

    private int writeBlockFunc(int blockId) throws IOException {
        int dataBlockId = bytesToInt(UmdUtil.genRandomBytes(4));
        writeBlockFunc(blockId, dataBlockId);
        return dataBlockId;
    }

    private void writeBlockFunc(int blockId, byte[] bytes) throws IOException {
        writeBlock(new UmdBlockFunc(blockId, bytes));
    }

    private void writeBlockData(int blockId, byte[] bytes) throws IOException {
        writeBlock(new UmdBlockData(blockId, bytes));
    }

    private void writeBlock(UmdBlockBase block) throws IOException {
        writeBytes(block.getBlockType());
        if (UmdBlockFunc.PREFIX == block.getBlockType()) {
            writeBytes((byte) block.getBlockId(), (byte) 0, (byte) 0);
            writeBytes((byte) block.getSize());
        } else {
            writeInt(block.getBlockId());
            writeInt(block.getSize());
        }
        writeBytes(block.getContent());
    }

    private void inc(int value) {
        int temp = written + value;
        if (temp < 0) {
            temp = Integer.MAX_VALUE;
        }
        written = temp;
    }

    private void writeInt(int v) throws IOException {
        os.write(UmdUtil.intToBytes(v));
        inc(4);
    }

    private void writeBytes(byte... bytes) throws IOException {
        os.write(bytes);
        inc(bytes.length);
    }

    private void extractCover(File folder) {
        byte[] bytes = umd.getCover().getData();
        if (bytes == null) {
            return;
        }
        File cover = new File(folder.getAbsoluteFile() + File.separator + "cover.jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(cover);
            fos.write(bytes);
            fos.flush();
        } catch (FileNotFoundException ex) {
            log.log(Level.SEVERE, "file not found: " + cover.getAbsolutePath(), ex);
        } catch (IOException ex) {
            log.log(Level.SEVERE, "io error ocurrs in writing umd to file", ex);
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                log.log(Level.SEVERE, "error ocurrs in closing umd file", ex);
            }
        }

    }

    private void extractContent(File folder) {
        String folderName = folder.getAbsoluteFile() + File.separator;
        for (int i = 0; i < umd.sizeOfChapters(); i++) {
            UmdChapter chapter = umd.getChapter(i);
            byte[] bytes = chapter.getContent();
            if (bytes == null) {
                bytes = new byte[]{};
            }
            File txt = new File(folderName + (i + 1) + ".txt");
            FileWriter fw = null;
            try {
                fw = new FileWriter(txt);
                fw.write(UmdUtil.unicode(bytes));
                fw.flush();
            } catch (FileNotFoundException ex) {
                log.log(Level.SEVERE, "file not found: " + txt.getAbsolutePath(), ex);
            } catch (IOException ex) {
                log.log(Level.SEVERE, "io error ocurrs in writing umd to file", ex);
            } finally {
                try {
                    fw.close();
                } catch (IOException ex) {
                    log.log(Level.SEVERE, "error ocurrs in closing umd file", ex);
                }
            }
        }
    }
}
