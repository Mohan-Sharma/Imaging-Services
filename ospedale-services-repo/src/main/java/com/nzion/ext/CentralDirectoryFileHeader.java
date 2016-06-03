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
public class CentralDirectoryFileHeader extends FileHeaderBase {
    
    private final int CENTRAL_DIRECTORY_HEADER = 0x02014b50;
    private String fileComment;
    protected int relativeOffset;
    
        private final short WIN32_ZIP2_3 = 0xB17;
        
        public CentralDirectoryFileHeader(FileEntry Parent)
        {
            super(Parent);
            fileComment = "";
        }
        
        public short getVersionMadeBy()
        {
            
            
                return WIN32_ZIP2_3;
            
        }
        
        public short getFileCommentLength()
        {
            
            
                return (short)(fileComment.length());
            
            
        }
        
        public short getDiskNumberStart()
        {
            
            
                //Currently only support writing to one big ZIP file
                return 0;
            
        }
        
        public short getInternalFileAttributes()
        {
            
            
                //Hard coded and dirty but will hopefully work
                return 0;
            
        }
        
        public int getExternalFileAttributes()
        {
            
                //Hard coded and dirty but will hopefully work
                return 0;
            
            
        }
        
        public int getRelativeOffsetOfLocalHeader()
        {
           
                return relativeOffset;
            
            
        }
        
        protected void setRelativeOffsetOfLocalHeader(int value)
            {
                relativeOffset = value;
            }
        
        public String getFileComment()
        {
            
                return fileComment;
            
            
        }
        
        public void setFileComment(String value)
            {
                fileComment = value;
            }

    @Override
    public int getSignature() {
        return CENTRAL_DIRECTORY_HEADER;
    }

    @Override
    protected byte[] GetOutput() {
        ByteBuffer buff = ByteBuffer.allocate(46 + this.FileNameLength() + this.getExtraFieldLength() + this.getFileCommentLength());
        buff.order(ByteOrder.LITTLE_ENDIAN);
        buff.putInt(this.getSignature()); //4 bytes
        buff.putShort(this.getVersionMadeBy()); //2 bytes
        buff.putShort(this.getVersionNeededToExtract()); //2 bytes
        buff = buff.put(this.getGeneralPurposeBitFlag().GetOutput()); //2 bytes
        buff.putShort(this.getMethod().getMethodValue()); // 2 bytes
        buff = buff.put(this.getLastModDataTime().GetOutput()); // 4 bytes
        buff.putInt(this.getCRC32()); // 4 bytes
        buff.putInt(this.getCompressedSize()); //4 bytes
        buff.putInt(this.getUnCompressedSize()); // 4 bytes
        buff.putShort(this.FileNameLength()); //2 bytes
        buff.putShort(this.getExtraFieldLength()); //2 bytes
        buff.putShort(this.getFileCommentLength()); //2 bytes
        buff.putShort(this.getDiskNumberStart());
        buff.putShort(this.getInternalFileAttributes());
        buff.putInt(this.getExternalFileAttributes());
        buff.putInt(this.getRelativeOffsetOfLocalHeader());
        
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
        
        if (this.getFileCommentLength() > 0) {
            try {
                buff.put(this.getFileComment().getBytes("US-ASCII"));
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(EndOfCentralDirectoryHeader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return buff.array();
        
    }
    

}
