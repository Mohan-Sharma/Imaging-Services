/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nzion.ext;

/**
 *
 * @author Neill Harper
 */
public class Method {
    
    public static final short Stored = 0;
    public static final short Shrunk = 1;
    public static final short ReducedCompFactor1 = 2;
    public static final short ReducedCompFactor2 = 3;
    public static final short ReducedCompFactor3 = 4;
    public static final short ReducedCompFactor4 = 5;
    public static final short Imploded = 6;
    public static final short Tokenized = 7;
    public static final short Deflated = 8;
    public static final short Deflate64 = 9;
    public static final short OldImploding = 10;
    public static final short Reserved1 = 11;
    public static final short BZIP2 = 12;
    public static final short Reserved2 = 13;
    public static final short LZMA = 14;
    
    
    
    
    
    private short meth;

    public short getMethodValue() {
        return meth;
    }

    public void setMethodValue(short value) {
        this.meth = value;
    }
    
    

}
