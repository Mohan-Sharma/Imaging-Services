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
public class GPBitFlag extends ZIPObject {
    
    private short bitFlags = 0;
    
    static final short Empty = 0;
    static final short Encrypted = 1;
    static final short PrivateComp1 = 2;
    static final short PrivateComp2 = 4;
    static final short DataDescriptor = 8;
    static final short EnhancedDeflating = 16;
    static final short CompressedPatch = 32;
    static final short StrongEncryption = 64;
    static final short Unused1 = 128;
    static final short Unused2 = 256;
    static final short Unused3 = 512;
    static final short Unused4 = 1024;
    static final short UTF8Encoding = 2048;
    static final short ReservedEnhancedCompression = 4096;
    static final short EncryptedCentralDirectory = 8192;
    static final short ReservedPK1 = 16384;
    static final short ReservedPK2 = (short) 32768;
    
    public void AddFlag(short Flag)
    {
        bitFlags |= Flag;
    }
    
    public void RemoveFlag(short Flag)
    {
        bitFlags = (short) (bitFlags & ~Flag);
    }
    
    
    public GPBitFlag()
    {
        bitFlags = 0;
    }
    
    
    
    

    @Override
    protected byte[] GetOutput() {
        
        ByteBuffer buff = ByteBuffer.allocate(2);
        buff.order(ByteOrder.LITTLE_ENDIAN);
        buff.putShort(bitFlags);
       return buff.array();
    }

}
