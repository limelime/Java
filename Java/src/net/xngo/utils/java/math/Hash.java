package net.xngo.utils.java.math;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.FileNotFoundException;

import java.nio.file.Path;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import net.jpountz.xxhash.StreamingXXHash32;
import net.jpountz.xxhash.XXHashFactory;



/**
 * Hold all different hashing algorithms.
 * All methods should return their value as string.
 * 
 * http://en.wikipedia.org/wiki/List_of_hash_functions#Cryptographic_hash_functions
 * 
 * @author Xuan Ngo
 *
 */
public class Hash
{
  
  // Buffer size = 8192 bytes = 8 KB is optimal.
  // Device block size is optimal.
  final static int BUFFER_SIZE = 8192; 
  
 
  

  
  public static final String md5(File file)
  {
    return md5(file, BUFFER_SIZE);
  }
  
  /**
   * See http://www.asjava.com/core-java/java-md5-example/
   * http://www.mkyong.com/java/java-md5-hashing-example/
   * 
   * @param file
   * @return
   */
  public static final String md5(File file, int bufferSize)
  {
    try
    {
      MessageDigest md = MessageDigest.getInstance("MD5");
      FileInputStream fis = new FileInputStream(file);

      byte[] dataBytes = new byte[bufferSize];

      int nread = 0;
      while ((nread = fis.read(dataBytes)) != -1)
      {
        md.update(dataBytes, 0, nread);
      }
      
      byte[] mdbytes = md.digest();

      // convert the byte to hex format method 1
      StringBuffer hexString = new StringBuffer();
      for (int i = 0; i < mdbytes.length; i++)
      {
        hexString.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
      }
      
      fis.close();
      return hexString.toString();
    }
    catch(NoSuchAlgorithmException ex)
    {
      ex.printStackTrace();
    }
    catch(FileNotFoundException ex)
    {
      RuntimeException rException = new RuntimeException(ex.getMessage());
      rException.setStackTrace(ex.getStackTrace());
      throw rException;
    }
    catch(IOException ex)
    {
      ex.printStackTrace();
    }

    return null; // hash failed
  }

  public static final String md5FingerPrint(File file, int checkFrequency)
  {
    return md5FingerPrint(file, checkFrequency, BUFFER_SIZE);
  }
  
  public static final String md5FingerPrint(File file, int checkFrequency, int bufferSize)
  {
    try
    {
      MessageDigest md = MessageDigest.getInstance("MD5");
      FileInputStream fis = new FileInputStream(file);

      byte[] dataBytes = new byte[bufferSize];
      int fileSize = fis.available();
      int sliceSize = fileSize/checkFrequency;
      int skipSize = sliceSize-bufferSize;
      
      // Error handling.
//      if(sliceSize<(bufferSize*2))
//      {
//        fis.close();
//        throw new RuntimeException(String.format("sliceSize(%d) has to be 2 times bigger than bufferSize(%d).", sliceSize, bufferSize));
//      }
      
//      System.out.println(String.format("%s: Total size=%,d ", file.getAbsolutePath(), fileSize));      
      int pos = 0;
      int nread = 0;
      for(int i=0; i<checkFrequency-1; i++)
      {
        // Read buffer.
        nread = fis.read(dataBytes);
        md.update(dataBytes, 0, nread);
//        System.out.println(String.format("Pos=%d, Read %d ", pos, bufferSize));
        pos+=bufferSize;
        
        // Skip X bytes.
        pos+=skipSize;
        fis.skip(skipSize);
//        System.out.println(String.format("\t Skip %d bytes, pos=%d", skipSize, pos));
      }
      // Read the buffer stream from the end of the file. This is where it is likely to change.
      skipSize = (fileSize-bufferSize)-pos;
      pos+=skipSize;
      if(skipSize>0)
        fis.skip(skipSize);
      
      nread = fis.read(dataBytes);
      md.update(dataBytes, 0, nread);
//      System.out.println(String.format("Pos=%d, Read %d ", pos, bufferSize));
      

      // Convert the byte to hex format method 1
      byte[] mdbytes = md.digest();
      StringBuffer hexString = new StringBuffer();
      for (int i = 0; i < mdbytes.length; i++)
      {
        hexString.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
      }
      
      fis.close();
      return hexString.toString();
    }
    catch(NoSuchAlgorithmException ex)
    {
      ex.printStackTrace();
    }
    catch(FileNotFoundException ex)
    {
      RuntimeException rException = new RuntimeException(ex.getMessage());
      rException.setStackTrace(ex.getStackTrace());
      throw rException;
    }
    catch(IOException ex)
    {
      ex.printStackTrace();
    }

    return null; // hash failed
  }
}
