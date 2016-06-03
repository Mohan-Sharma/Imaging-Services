/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nzion.ext;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author Neill Harper
 */
public class FileEntry {
    private LocalFileHeader lfh;
        private CentralDirectoryFileHeader cdfh;
        
        private FileData fileBytes;
        private ZIPFile parent;
        private File filePath;
        private String password;
        private DataDescriptor dDesc;
        
        protected  int compressedSize;
        protected  int crc32;
        protected  int unCompressedSize;
        protected  String extraField;
        protected  String fileName;
        protected  GPBitFlag gpBitFlag;
        protected  DOSDateTime lastMod;
        protected  Method method;
        
        public FileEntry(String Filename,Method CompressionMethod) throws IOException
        {
            filePath = new File(Filename);
            initialise(CompressionMethod);
        }
        
        public FileEntry(String FileName, String Password, Method CompressionMethod) throws IOException
        {
            filePath = new File(FileName);
            password = Password;
            initialise(CompressionMethod);
            this.lfh.getGeneralPurposeBitFlag().AddFlag(GPBitFlag.Encrypted);
            this.lfh.getGeneralPurposeBitFlag().AddFlag(GPBitFlag.DataDescriptor);
            
        }
        
        
        private void initialise(Method CompMethod) throws IOException
        {
            this.method = CompMethod;
            lfh = new LocalFileHeader(getFilePath(),this);
            cdfh = new CentralDirectoryFileHeader(this);
            dDesc = new DataDescriptor(this);
            
            fileBytes = new FileData(this);
        }

    public LocalFileHeader getLfh() {
        return lfh;
    }

    public CentralDirectoryFileHeader getCdfh() {
        return cdfh;
    }

    public FileData getFileBytes() {
        return fileBytes;
    }

    public ZIPFile getParent() {
        return parent;
    }

    public void setParent(ZIPFile parent) {
        this.parent = parent;
    }

    public File getFilePath() {
        return filePath;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DataDescriptor getDDesc() {
        return dDesc;
    }

}
