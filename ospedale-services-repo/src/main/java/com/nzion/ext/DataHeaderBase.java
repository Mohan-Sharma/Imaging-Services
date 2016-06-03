/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nzion.ext;

/**
 *
 * @author Neill Harper
 */
public abstract class DataHeaderBase extends HeaderBase {

    private FileEntry parent;

    public DataHeaderBase(FileEntry Parent) {
        parent = Parent;
    }


    public FileEntry getParent() {
        return parent;
    }

    public void setParent(FileEntry parent) {
        this.parent = parent;
    }

    public int getCompressedSize() {

        return this.parent.compressedSize;
    }

    protected void setCompressedSize(int value) {
        this.parent.compressedSize = value;
    }
    
    public int getUnCompressedSize() {

        return this.parent.unCompressedSize;
    }

    protected void setUnCompressedSize(int value) {
        this.parent.unCompressedSize = value;
    }
    
    public int getCRC32() {

        return this.parent.crc32;
    }

    protected void setCRC32(int value) {
        this.parent.crc32 = value;
    }
    
    
}
