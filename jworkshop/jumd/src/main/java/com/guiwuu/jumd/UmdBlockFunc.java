package com.guiwuu.jumd;

import java.util.ArrayList;
import java.util.List;

/**
 * Function block of UMD
 *
 * @author daijun
 * @since 2011-02-12
 * @version 2011-02-20
 */
public class UmdBlockFunc extends UmdBlockBase {

    public final static byte PREFIX = '#';
    public final static int ID_DOC_TYPE = 0x01;
    public final static int ID_TITLE = 0x02;
    public final static int ID_AUTHOR = 0x03;
    public final static int ID_YEAR = 0x04;
    public final static int ID_MONTH = 0x05;
    public final static int ID_DAY = 0x06;
    public final static int ID_GENDER = 0x07;
    public final static int ID_PUBLISHER = 0x08;
    public final static int ID_VENDOR = 0x09;
    public final static int ID_CID = 0x0a;
    public final static int ID_CLEN = 0x0b;
    public final static int ID_EOF = 0x0c;
    public final static int ID_EOC = 0x81;
    public final static int ID_COVER = 0x82;
    public final static int ID_CHAP_OFFSET = 0x83;
    public final static int ID_CHAP_TITLE = 0x84;
    public final static int ID_PAGE_OFFSET = 0x87;
    public final static int ID_CDS_KEY = 0xf0;
    public final static int ID_LICENSE = 0xf1;
    private List<UmdBlockData> datas;

    public UmdBlockFunc() {
        this.blockType = PREFIX;
    }

    public UmdBlockFunc(int blockId, byte[] content) {
        this.blockType = PREFIX;
        this.blockId = blockId;
        this.size = 5 + content.length;
        this.content = content;
    }

    public void addData(UmdBlockData data) {
        if (datas == null) {
            datas = new ArrayList<UmdBlockData>();
        }
        datas.add(data);
    }

    public UmdBlockData getData(int i) {
        if (i + 1 > datas.size()) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        return datas.get(i);
    }

    public int sizeOfDatas() {
        return datas == null ? 0 : datas.size();
    }
}
