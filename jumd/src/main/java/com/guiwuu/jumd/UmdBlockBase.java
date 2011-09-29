package com.guiwuu.jumd;

/**
 * Base block of UMD
 *
 * @author daijun
 * @since 2011-02-12
 * @version 2011-02-12
 */
public class UmdBlockBase {

    /**
     * Type of UMD: function block and data block
     */
    protected byte blockType;
    /**
     * Identifier of UMD blocks
     */
    protected int blockId;
    /**
     * Total size of block bytes
     */
    protected int size;
    /**
     * Content
     */
    protected byte[] content;

    public byte getBlockType() {
        return blockType;
    }

    public void setBlockType(byte blockType) {
        this.blockType = blockType;
    }

    public int getBlockId() {
        return blockId;
    }

    public void setBlockId(int blockId) {
        this.blockId = blockId;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
