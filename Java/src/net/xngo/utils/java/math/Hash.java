package net.xngo.utils.java.math;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.nio.file.Path;
import java.nio.file.Files;

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
   * @deprecated
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
    // Throw an exception if it is a directory.
    if(file.isDirectory())
      throw new RuntimeException("Can't process directory: "+file.getAbsolutePath());
    
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
      RuntimeException rException = new RuntimeException(ex.getMessage());
      rException.setStackTrace(ex.getStackTrace());
      throw rException;
    }
    
    return null;
  }
  
  public static final String xxhash32SuperClass(File file)
  {
    // Throw an exception if it is a directory.
    if(file.isDirectory())
      throw new RuntimeException("Can't process directory: "+file.getAbsolutePath());
    
    XXHashFactory factory = XXHashFactory.fastestInstance();
    int seed = 0x9747b28c;  // used to initialize the hash value, use whatever
                            // value you want, but always the same
    StreamingXXHash32 hash32 = factory.newStreamingHash32(seed);
  
    try
    {
      byte[] bufferBlock = new byte[8192]; // 8192 bytes
      InputStream fileInputStream = new FileInputStream(file);
  
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
      RuntimeException rException = new RuntimeException(ex.getMessage());
      rException.setStackTrace(ex.getStackTrace());
      throw rException;
    }
    
    return null;
  }
  
  public static final String xxhash32Buffer(File file)
  {
    // Throw an exception if it is a directory.
    if(file.isDirectory())
      throw new RuntimeException("Can't process directory: "+file.getAbsolutePath());
    
    XXHashFactory factory = XXHashFactory.fastestInstance();
    int seed = 0x9747b28c;  // used to initialize the hash value, use whatever
                            // value you want, but always the same
    StreamingXXHash32 hash32 = factory.newStreamingHash32(seed);
  
    try
    {
      byte[] bufferBlock = new byte[8192]; // 8192 bytes
      BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(file));
  
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
      RuntimeException rException = new RuntimeException(ex.getMessage());
      rException.setStackTrace(ex.getStackTrace());
      throw rException;
    }
    
    return null;
  }
  
  public static final String xxhash32(Path path)
  {
    // Throw an exception if it is a directory.
    if(Files.isDirectory(path))
      throw new RuntimeException("Can't process directory: "+path.toString());
    
    XXHashFactory factory = XXHashFactory.fastestInstance();
    int seed = 0x9747b28c;  // used to initialize the hash value, use whatever
                            // value you want, but always the same
    StreamingXXHash32 hash32 = factory.newStreamingHash32(seed);
  
    try
    {
      byte[] bufferBlock = new byte[8192]; // 8192 bytes
      InputStream inputStream = Files.newInputStream(path);
  
      int read;
      while ((read = inputStream.read(bufferBlock))!=-1) 
      {
        hash32.update(bufferBlock, 0, read);
      }
      
      inputStream.close();
      return hash32.getValue()+""; // Force to be a string to normalize with other hashing algorithm.

    }
    catch(UnsupportedEncodingException ex)
    {
      ex.printStackTrace();
    }
    catch(IOException ex)
    {
      RuntimeException rException = new RuntimeException(ex.getMessage());
      rException.setStackTrace(ex.getStackTrace());
      throw rException;
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
