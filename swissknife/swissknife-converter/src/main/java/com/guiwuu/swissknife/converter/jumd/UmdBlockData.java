package com.guiwuu.swissknife.converter.jumd;

/**
 * Function block of UMD
 *
 * @author daijun
 * @since 2011-02-12
 * @version 2011-02-12
 */
public class UmdBlockData extends UmdBlockBase {

    public final static byte PREFIX = '$';

    public UmdBlockData() {
        this.blockType = PREFIX;
    }

    public UmdBlockData(int blockId, byte[] content) {
        this.blockType = PREFIX;
        this.blockId = blockId;
        this.size = 9 + content.length;
        this.content = content;
    }
}
