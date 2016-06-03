/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nzion.ext;

/**
 *
 * @author Neill Harper
 */
public abstract class FileHeaderBase extends DataHeaderBase {

    private final short PKUNZIP2_0 = 20;

    public FileHeaderBase(FileEntry Parent) {
        super(Parent);
        this.setGeneralPurposeBitFlag(new GPBitFlag());
    }

    public short getVersionNeededToExtract() {
        return PKUNZIP2_0;
    }

    public GPBitFlag getGeneralPurposeBitFlag() {

        {
            return this.getParent().gpBitFlag;
        }

    }

    protected void setGeneralPurposeBitFlag(GPBitFlag value) {
        this.getParent().gpBitFlag = value;
    }

    public Method getMethod() {

        {
            return this.getParent().method;
        }

    }

    public void setMethod(Method value) {
        this.getParent().method = value;
    }
    
    public DOSDateTime getLastModDataTime()
        {
            
                return this.getParent().lastMod;
            
            
        }
    
    public void setLastModDataTime(DOSDateTime value)
            {
                this.getParent().lastMod = value;
            }
    
    public String getFileName()
        {
            
                return this.getParent().fileName;
            
            
        }
    
    public void setFileName(String value)
            {
                this.getParent().fileName = value;
            }
    
    public String getExtraField()
        {
            
                return this.getParent().extraField;
            
            
        }
    
    public void setExtraField(String value)
            {
                this.getParent().extraField = value;
            }
    
    public short FileNameLength()
        {
            
            {
                return (short)this.getParent().fileName.length();
            }
            
        }
    
    public short getExtraFieldLength()
        {
            
            {
                return (short)(this.getParent().extraField.length());
            }
        }
}
