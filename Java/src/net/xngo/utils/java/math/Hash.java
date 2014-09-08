package net.xngo.utils.java.math;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import net.jpountz.xxhash.StreamingXXHash32;
import net.jpountz.xxhash.XXHashFactory;

/**
 * Hold all different hashing algorithms.
 * All methods should return their value as string.
 * @author Xuan Ngo
 *
 */
public class Hash
{

  /**
   * Get the hash(ID) of the file.
   * Note: -XXHash32 is chosen because it claims to be fast.
   *       -Check what is the collision rate of XXHash32 algorithm 
   *              because StreamingXXHash32.getValue() return an integer, 
   *              which has a limit of 2,147,483,648.
   * @param file
   * @return      the hash as string
   */
  public static final String xxhash32(File file)
  {
    
    XXHashFactory factory = XXHashFactory.fastestInstance();
    int seed = 0x9747b28c;  // used to initialize the hash value, use whatever
                            // value you want, but always the same
    StreamingXXHash32 hash32 = factory.newStreamingHash32(seed);
  
    try
    {
      byte[] bufferBlock = new byte[8192]; // 8192 bytes
      FileInputStream fileInputStream = new FileInputStream(file);
  
      int read;
      while ((read = fileInputStream.read(bufferBlock))!=-1) 
      {
        hash32.update(bufferBlock, 0, read);
      }
      
      fileInputStream.close();
      return hash32.getValue()+""; // Force to be a string to normalize with other hashing algorithm.
      
    }
    catch(UnsupportedEncodingException ex)
    {
      ex.printStackTrace();
    }
    catch(IOException ex)
    {
      // Throw an exception if file is locked.
      if(ex.getMessage().compareTo("The process cannot access the file because another process has locked a portion of the file")==0)
      {
        // Construct exception.
        RuntimeException fileLockedException = new RuntimeException(ex.getMessage()+"("+file.getAbsolutePath()+").");
        fileLockedException.setStackTrace(ex.getStackTrace());
        throw fileLockedException;
      }
      else
        ex.printStackTrace();
    }
    
    return null;
  }
  
  /**
   * @deprecated Not implemented yet.
   * @param file
   * @return
   */
  public String md5(File file)
  {
    // See http://www.asjava.com/core-java/java-md5-example/
    return null;
  }
}
