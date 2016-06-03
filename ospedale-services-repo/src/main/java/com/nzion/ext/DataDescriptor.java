/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nzion.ext;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *
 * @author Neill Harper
 */
public class DataDescriptor extends DataHeaderBase{
    
    private final int DATA_DESC_HEADER = 0x08074b50;
    
    public DataDescriptor(FileEntry Parent)
    {
            super(Parent);
    }

    @Override
    public int getSignature() {
        return DATA_DESC_HEADER;
    }

    @Override
    protected byte[] GetOutput() {
        ByteBuffer buff = ByteBuffer.allocate(16);
        buff.order(ByteOrder.LITTLE_ENDIAN);
        buff.putInt(this.getSignature());
        buff.putInt(this.getCRC32());
        buff.putInt(this.getCompressedSize());
        buff.putInt(this.getUnCompressedSize());
        
        return buff.array();
    }

}
