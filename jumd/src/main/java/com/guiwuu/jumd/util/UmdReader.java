package com.guiwuu.jumd.util;

import static com.guiwuu.jumd.UmdBlockFunc.*;
import static com.guiwuu.jumd.util.UmdUtil.*;

import com.guiwuu.jumd.UmdHeader;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.guiwuu.jumd.Umd;
import com.guiwuu.jumd.UmdBlockData;
import com.guiwuu.jumd.UmdBlockFunc;
import com.guiwuu.jumd.UmdChapter;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * UMD parser
 *
 * @author daijun
 * @since 2011-02-12
 * @version 2011-02-22
 */
public class UmdReader {

    private static final Logger log = Logger.getLogger(UmdReader.class.getName());
    private File file;
    private List<UmdBlockFunc> blocks = new ArrayList<UmdBlockFunc>();
    private int pointer = 0;
    private Umd umd = new Umd();
    private int[] chapterOffsets = {};
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    private List<Integer> chunkIds = new ArrayList<Integer>();
    private int[] expectedChunkIds;

    public Umd getUmd() {
        return umd;
    }

    public UmdReader(File file) {
        this.file = file;
    }

    public Umd read() throws IOException {
        byte[] bytes = UmdUtil.readFile(file);
        boolean ret = readUmdBlocks(bytes);
        if (!ret) {
            log.log(Level.SEVERE, "Not a UMD file: {0}", file.getAbsolutePath());
            return null;
        } else if (blocks.isEmpty()) {
            log.log(Level.WARNING, "An empty UMD file: {0}", file.getAbsolutePath());
        }

        for (UmdBlockFunc block : blocks) {
            parseUmdBlockFunc(block);
        }

        parseDataChunk();
        return umd;
    }

    private boolean readUmdBlocks(byte[] bytes) {
        if (!validBeginOfUmd(bytes)) {
            return false;
        }

        if (!validEndOfUmd(bytes)) {
            log.warning("Umd file does not end correctly");
        }

        // parse blocks
        while (pointer < bytes.length) {
            if (bytes[pointer] == UmdBlockFunc.PREFIX) {// function block
                // 2nd byte is identifier of block
                int blockId = UmdUtil.bytesToInt(bytes[++pointer]);

                // unkown of 3rd and 4th byte, skip to 5th
                pointer += 2;

                // 5th byte is size of total block bytes
                int contentLength = bytes[++pointer] - 5;

                // 6th~6th+funcBlockContentLength-1 is block content
                byte[] content = new byte[contentLength];
                System.arraycopy(bytes, ++pointer, content, 0, contentLength);

                // new function block
                UmdBlockFunc block = new UmdBlockFunc(blockId, content);

                // move to next block
                pointer += contentLength;

                // a function block may contains many data blocs
                while (pointer < bytes.length && bytes[pointer] == UmdBlockData.PREFIX) {
                    // 2nd~5th bytes represent int value of block identifier
                    byte[] b = {bytes[++pointer], bytes[++pointer], bytes[++pointer], bytes[++pointer]};
                    int dataBlockId = UmdUtil.bytesToInt(b);

                    // 6nd~9th bytes represent int value of size of total block bytes
                    b = new byte[]{bytes[++pointer], bytes[++pointer], bytes[++pointer], bytes[++pointer]};
                    contentLength = UmdUtil.bytesToInt(b) - 9;

                    // 10th~10th+dataBlockContentLength-1 is block content
                    byte[] dataContent = new byte[contentLength];
                    System.arraycopy(bytes, ++pointer, dataContent, 0, contentLength);

                    // add to function block
                    UmdBlockData data = new UmdBlockData(dataBlockId, dataContent);
                    block.addData(data);

                    // step to begin of next block
                    pointer += contentLength;
                }

                // add new function block
                blocks.add(block);
            } else {// Ignore unexpected bytes
                log.log(Level.WARNING, "Unexpected byte: {0}", bytes[++pointer]);
            }
        }

        return true;
    }

    private void parseUmdBlockFunc(UmdBlockFunc block) throws UnsupportedEncodingException {
        switch (block.getBlockId()) {
            case ID_DOC_TYPE:
            case ID_TITLE:
            case ID_AUTHOR:
            case ID_YEAR:
            case ID_MONTH:
            case ID_DAY:
            case ID_GENDER:
            case ID_PUBLISHER:
            case ID_VENDOR:
            case ID_CLEN:
            case ID_EOF:
                parseHeader(block);
                break;
            case ID_CHAP_OFFSET:
            case ID_CHAP_TITLE:
                parseChapter(block);
                break;
            case ID_EOC:
                parseEoc(block);
                break;
            case ID_COVER:
                parseCover(block);
                break;
            case ID_LICENSE:
            case ID_CID:
                appendDataChunk(block, 0);
                break;
            case ID_PAGE_OFFSET:
            case ID_CDS_KEY:
            default:
                ignore(block);
                break;
        }
    }

    private void parseHeader(UmdBlockFunc block) {
        byte[] content = block.getContent();
        UmdHeader header = umd.getHeader();
        int blockId = block.getBlockId();
        if (ID_DOC_TYPE == blockId) {
            header.setDocType(block.getContent()[0]);
        } else if (ID_TITLE == blockId) {
            header.setTitle(unicode(content));
        } else if (ID_AUTHOR == blockId) {
            header.setAuthor(unicode(content));
        } else if (ID_YEAR == blockId) {
            header.setYear(unicode(content));
        } else if (ID_MONTH == blockId) {
            header.setMonth(unicode(content));
        } else if (ID_DAY == blockId) {
            header.setDay(unicode(content));
        } else if (ID_GENDER == blockId) {
            header.setGender(unicode(content));
        } else if (ID_PUBLISHER == blockId) {
            header.setPublisher(unicode(content));
        } else if (ID_VENDOR == blockId) {
            header.setVendor(unicode(content));
        } else if (ID_CLEN == blockId) {
            header.setContentSize(bytesToInt(content));
        } else if (ID_EOF == blockId) {
            header.setTotalSize(bytesToInt(content));
        }
    }

    private void parseChapter(UmdBlockFunc block) {
        validateBlockFuncWithData(block);
        if (ID_CHAP_OFFSET == block.getBlockId()) {
            UmdBlockData data = block.getData(0);
            chapterOffsets = bytesToInts(data.getContent());
        } else if (ID_CHAP_TITLE == block.getBlockId()) {
            byte[] b = block.getData(0).getContent();
            int i = 0;
            while (i < b.length) {
                int len = bytesToInt(b[i]);
                byte[] bb = new byte[len];
                System.arraycopy(b, ++i, bb, 0, len);
                String title = unicode(bb);
                umd.addChapter(new UmdChapter(title));
                i += len;
            }
            appendDataChunk(block, 1);
        }
    }

    private void parseEoc(UmdBlockFunc block) {
        validateBlockFuncWithData(block);
        UmdBlockData data = block.getData(0);
        expectedChunkIds = bytesToInts(data.getContent());
    }

    private void parseCover(UmdBlockFunc block) {
        validateBlockFuncWithData(block);
        UmdBlockData data = block.getData(0);
        umd.getCover().setData(data.getContent());
    }

    private void ignore(UmdBlockFunc block) {
        String hex = "0x" + Integer.toHexString(block.getBlockId());
        log.log(Level.WARNING, "Ignore function block id: {0}", hex);
    }

    private void appendDataChunk(UmdBlockFunc block, int begin) {
        for (int j = begin; j < block.sizeOfDatas(); j++) {
            try {
                chunkIds.add(block.getData(j).getBlockId());
                bos.write(unzip(block.getData(j).getContent()));
            } catch (IOException ex) {
                log.log(Level.SEVERE, "error ocurrs in put data block conent together, j=" + j, ex);
            }
        }
    }

    private void parseDataChunk() throws IOException {
        bos.close();
        byte[] datas = bos.toByteArray();

        // validate content size
        int expectedSize = umd.getHeader().getContentSize();
        if (expectedSize != datas.length) {
            umd.getHeader().setContentSize(datas.length);
            String msg = "Expected clen is {0} but {1}";
            Object[] param = {expectedSize, datas.length};
            log.log(Level.WARNING, msg, param);
        }

        if (!validateChunkIds()) {
            log.warning("Chunk ids do not match");
        }

        for (int j = 0; j < chapterOffsets.length; j++) {
            int len;
            if (j + 1 < chapterOffsets.length) {
                len = chapterOffsets[j + 1] - chapterOffsets[j];
            } else {
                len = datas.length - chapterOffsets[j];
            }
            byte[] b = new byte[len];
            if (chapterOffsets[j] >= datas.length) {
                String msg = "page offset({0}) is arange out of datas length({1})";
                Object[] param = {chapterOffsets[j], datas.length};
                log.log(Level.WARNING, msg, param);
                break;
            } else if (chapterOffsets[j] + len > datas.length) {
                String msg = "chapter end({0}) is arange out datas length({1})";
                Object[] param = {chapterOffsets[j] + len, datas.length};
                log.log(Level.WARNING, msg, param);
                len = datas.length - chapterOffsets[j];
            }

            System.arraycopy(datas, chapterOffsets[j], b, 0, len);
            umd.getChapter(j).setContent(b);
        }
    }

    private boolean validBeginOfUmd(byte[] bytes) {
        for (pointer = 0; pointer < 4; pointer++) {
            if (bytes[pointer] != Umd.BEGIN[pointer]) {
                return false;
            }
        }
        return true;
    }

    private boolean validEndOfUmd(byte[] bytes) {
        byte[] expectedEnd = new byte[9];
        System.arraycopy(Umd.END, 0, expectedEnd, 0, Umd.END.length);
        byte[] fileSize = UmdUtil.intToBytes(bytes.length);
        System.arraycopy(fileSize, 0, expectedEnd, Umd.END.length, 4);
        for (int i = bytes.length - 9, j = 0; i < bytes.length; i++, j++) {
            if (bytes[i] != expectedEnd[j]) {
                return false;
            }
        }
        return true;
    }

    private boolean validateBlockFuncWithData(UmdBlockFunc block) {
        if (block.sizeOfDatas() == 0) {
            return true;
        }

        int expectedId = bytesToInt(block.getContent());
        int id = block.getData(0).getBlockId();
        if (id != expectedId) {
            String msg = "function block(id=0x{0}) expect data block id is {1} but {2}";
            Object[] params = {Integer.toHexString(block.getBlockId()), expectedId, id};
            log.log(Level.WARNING, msg, params);
            return false;
        }
        return true;
    }

    private boolean validateChunkIds() {
        // validate chunk number
        if (expectedChunkIds == null && chunkIds == null) {
            return true;
        } else if (expectedChunkIds == null) {
            log.warning("expected chunk is null but acutal is not null");
            return false;
        }

        // validate chunk number
        int expectedLen = expectedChunkIds.length;
        int actualLen = chunkIds.size();
        if (expectedLen != actualLen) {
            String msg = "Expected data chunk num is {0} but {1}";
            Object[] param = {expectedLen, actualLen};
            log.log(Level.WARNING, msg, param);
            return false;
        }

        // validate chunk ids
        for (int i = 0; i < expectedLen && i < actualLen; i++) {
            if (expectedChunkIds[i] != chunkIds.get(i)) {
                String msg = "Expected {0}th data chunk id is {1} but {2}";
                Object[] param = {i, expectedChunkIds[i], chunkIds.get(i)};
                log.log(Level.WARNING, msg, param);
                return false;
            }
        }
        return true;
    }
}
