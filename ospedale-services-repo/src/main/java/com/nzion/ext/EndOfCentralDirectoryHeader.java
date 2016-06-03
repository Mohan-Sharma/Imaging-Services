/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nzion.ext;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Neill Harper
 */
public class EndOfCentralDirectoryHeader extends HeaderBase {

    private final int END_OF_CENTRAL_DIRECTORY_HEADER = 0x06054b50;
    private short numberOfEntries;
    private int sizeOfCentralDir;
    private int offsetToCentralDir;
    private String zipComment = "";

    @Override
    public int getSignature() {
        return END_OF_CENTRAL_DIRECTORY_HEADER;
    }

    public short getDiskNumber() {
        return 0;
    }

    public short getCentralDirStartDisk() {
        return 0;
    }

    public short getNumberEntriesOnThisDisk() {
        return numberOfEntries;

    }

    public short getTotalNumberOfEntriesInCentralDir() {

        return numberOfEntries;
    }

    public void setTotalNumberOfEntriesInCentralDir(short value) {
        numberOfEntries = value;
    }

    public int getSizeOfCentralDirectory() {

        return sizeOfCentralDir;
    }

    public void setSizeOfCentralDirectory(int value) {

        sizeOfCentralDir = value;
    }

    public short getZipFileCommentLength() {
        return (short) this.zipComment.length();
    }

    @Override
    protected byte[] GetOutput()  {
        ByteBuffer buff = ByteBuffer.allocate(22 + this.getZipFileCommentLength());
        buff.order(ByteOrder.LITTLE_ENDIAN);
        buff.putInt(this.getSignature());
        buff.putShort(this.getDiskNumber());
        buff.putShort(this.getCentralDirStartDisk());
        buff.putShort(this.getNumberEntriesOnThisDisk());
        buff.putShort(this.getTotalNumberOfEntriesInCentralDir());
        buff.putInt(this.getSizeOfCentralDirectory());
        buff.putInt(this.getOffsetToCentralDir());
        buff.putShort(this.getZipFileCommentLength());
        
        if (this.getZipFileCommentLength() > 0) {
            try {
                buff.put(this.zipComment.getBytes("US-ASCII"));
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(EndOfCentralDirectoryHeader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return buff.array();
    }

    public int getOffsetToCentralDir() {
        return offsetToCentralDir;
    }

    public void setOffsetToCentralDir(int offsetToCentralDir) {
        this.offsetToCentralDir = offsetToCentralDir;
    }

    public String getZipComment() {
        return zipComment;
    }

    public void setZipComment(String zipComment) {
        this.zipComment = zipComment;
    }
}
