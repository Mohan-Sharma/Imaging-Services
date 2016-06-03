/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nzion.ext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Neill Harper
 */
public class ZIPFile extends ZIPObject {
    
    private List<FileEntry> files;
    private EndOfCentralDirectoryHeader eocdh;
    private File filePath;
    
    public ZIPFile(String ZipFileName)
        {
            filePath =  new File(ZipFileName);
            files = new ArrayList<FileEntry>();
            eocdh = new EndOfCentralDirectoryHeader();
        }
    
    public void AddFile(FileEntry file)
    {
        file.setParent(this);
        files.add(file);
        
    }
    
    public void CreateZIP() throws IOException
        {
            FileOutputStream outStream = new FileOutputStream(filePath);
            byte[] buff;
            for (FileEntry f : files)
            {
                //before anything is written for this file get the position in the stream
                f.getCdfh().setRelativeOffsetOfLocalHeader((int)outStream.getChannel().position());
                
                buff = f.getLfh().GetOutput();
                
                outStream.write(buff,0,buff.length);

                buff = f.getFileBytes().GetOutput();
                outStream.write(buff, 0, buff.length);

                buff = f.getDDesc().GetOutput();
                outStream.write(buff, 0, buff.length);
            }

            //need to get hold of the start of the central directory position before it is written
            this.getEocdh().setOffsetToCentralDir((int)outStream.getChannel().position());

            for (FileEntry f : files)
            {
                buff = f.getCdfh().GetOutput();
                this.getEocdh().setTotalNumberOfEntriesInCentralDir((short)(this.getEocdh().getTotalNumberOfEntriesInCentralDir() + 1));
                this.getEocdh().setSizeOfCentralDirectory((int)(this.getEocdh().getSizeOfCentralDirectory() + (int)buff.length));
                outStream.write(buff,0,buff.length);
            }


            buff = this.getEocdh().GetOutput();

            outStream.write(buff, 0, buff.length);
            

            outStream.close();
        }

    @Override
    protected byte[] GetOutput() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public EndOfCentralDirectoryHeader getEocdh() {
        return eocdh;
    }

    public File getFilePath() {
        return filePath;
    }

    public void setFilePath(File filePath) {
        this.filePath = filePath;
    }
}