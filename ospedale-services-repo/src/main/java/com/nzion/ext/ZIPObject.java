/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nzion.ext;

import java.io.FileOutputStream;

/**
 *
 * @author Neill Harper
 */
public abstract class ZIPObject {
    /**
     * Returns a Byte array containing the bytes representing the object
       from a given offset for a given length
     * @param offset Number of bytes to skip
     * @param length Maximum number of bytes to return
     * @return Byte array containing the bytes representing a portion of this object
     */
    protected  byte[] GetOutput(int offset, int length)
        {

            byte[] bytes = this.GetOutput();
            byte[] bytesRet = new byte[bytes.length];

            System.arraycopy(bytes, offset, bytesRet, 0,length > bytes.length ? bytes.length : length);
            

            return bytesRet;
        }
    
    /**
     * Returns a Byte array representing all of this object
     * @return a byte array
     */
    protected abstract  byte[] GetOutput();
    
    /**
     * Puts bytes representing part of the object into a given
       stream
     * @param output
     * @param offset
     * @param length
     * @return boolean representing success
     */
    protected  boolean InsertOutput(FileOutputStream output, int offset, int length)
        {
            try
            {
                byte[] bytes = this.GetOutput(offset, length);
                
                output.write(bytes, 0, bytes.length);
            }
            catch (Exception E)
            {
                return false;
            }

            return true;
        }
    
    /**
     * Puts bytes representing the object into a given file stream
     * @param output 
     * @return boolean representing success
     */
    protected boolean InsertOutput(FileOutputStream output)
        {
            try
            {
                byte[] bytes = this.GetOutput();
                output.write(bytes, 0, bytes.length);
            }
            catch (Exception E)
            {
                return false;
            }

            return true;
        }

}
