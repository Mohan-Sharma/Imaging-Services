/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nzion.ext;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Neill Harper
 */
public class LocalFileHeader extends FileHeaderBase {
    
    private final int LOCAL_FILE_HEADER = 0x04034b50;
    
    public LocalFileHeader(File file,FileEntry Parent)
        {
            super(Parent);
            this.setExtraField("");
            this.setFileName(file.getName());
            
            
            Date d = new Date(file.lastModified());
            DOSDateTime dd = new DOSDateTime(d);
            this.setLastModDataTime(dd) ;
            this.setUnCompressedSize((int)file.length());
        }

    @Override
    public int getSignature() {
        return LOCAL_FILE_HEADER;
    }

    @Override
    protected byte[] GetOutput() {
        ByteBuffer buff = ByteBuffer.allocate(30 + this.FileNameLength() + this.getExtraFieldLength());
        buff.order(ByteOrder.LITTLE_ENDIAN);
        buff.putInt(this.getSignature()); //4 bytes
        buff.putShort(this.getVersionNeededToExtract()); //2 bytes
        buff = buff.put(this.getGeneralPurposeBitFlag().GetOutput()); // 2 bytes
        buff.putShort(this.getMethod().getMethodValue()); //2 bytes
        buff = buff.put(this.getLastModDataTime().GetOutput()); // 4 bytes
        buff.putInt(this.getCRC32()); // 4 bytes
        buff.putInt(this.getCompressedSize()); //4 bytes
        buff.putInt(this.getUnCompressedSize()); // 4 bytes
        buff.putShort(this.FileNameLength()); //2 bytes
        buff.putShort(this.getExtraFieldLength()); //2 bytes
        
        if (this.FileNameLength() > 0) {
            try {
                buff.put(this.getFileName().getBytes("US-ASCII"));
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(EndOfCentralDirectoryHeader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (this.getExtraFieldLength() > 0) {
            try {
                buff.put(this.getExtraField().getBytes("US-ASCII"));
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(EndOfCentralDirectoryHeader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return buff.array();
        
    }

}
