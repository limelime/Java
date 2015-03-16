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
   * Get the hash(ID) of the file.
   * Note: -XXHash32 is chosen because it claims to be fast.
   *       -Check what is the collision rate of XXHash32 algorithm 
   *              because StreamingXXHash32.getValue() return an integer, 
   *              which has a limit of 2,147,483,648.
   * @param file
   * @return NULL if hash failed. Otherwise, the hash string.
   */
  public static final String xxhash32(File file)
  {
    // Throw an exception if it is a directory.
    if(file.isDirectory())
      throw new RuntimeException("Can't process directory: "+file.getAbsolutePath());
    
    XXHashFactory factory = XXHashFactory.fastestInstance();
    int seed = 0x9747b28c;  // used to initialize the hash value, use whatever
                            // value you want, but always the same.
    StreamingXXHash32 hash32 = factory.newStreamingHash32(seed);
    
    // Optimization: Reduce the number of reads.
    //  8KB < bufferSize < 5MB
    // TODO: Use multiple of 4KB or 8KB
    int bufferSize = file.length()/100; 
    if(bufferSize>5242880) // 5242880 bytes = 5MB
      bufferSize=5242880;
    else if(bufferSize<8192) // 8192 bytes = 8 KB
      bufferSize=8192;
      
    try
    {
      byte[] bufferBlock = new byte[bufferSize];
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
    
    return null; // hash failed
  }
  /**
   * @deprecated Don't use this. It is not reliable to uniquely ID a file.
   *              This might be good to be used in conjunction with other logic.
   * Hash the beginning, the middle and the end of the file.
   * @param file
   * @param bufferSize Buffer size in bytes.
   * @return NULL if hash failed. Otherwise, the hash string.
   */
  public static final String xxhash32Spread(File file, int bufferSize)
  {
    // Throw an exception if it is a directory.
    if (file.isDirectory())
      throw new RuntimeException("Can't process directory: " + file.getAbsolutePath());

    // Throw an exception if bufferSize is less than 1.
    if(bufferSize<1)
      throw new RuntimeException("Buffer size parameter can't be less than 1.");
    
    XXHashFactory factory = XXHashFactory.fastestInstance();
    int seed = 0x9747b28c; // used to initialize the hash value, use whatever
                           // value you want, but always the same.
    StreamingXXHash32 hash32 = factory.newStreamingHash32(seed);

    try
    {
      byte[] bufferBlock = new byte[bufferSize];
      FileInputStream fileInputStream = new FileInputStream(file);

      int totalLength = fileInputStream.available();
      int read=0;
      
      // Check if it can hash 3 buffer size of the stream.
      if(totalLength<(3*bufferSize))
      {
        fileInputStream.close();
        throw new RuntimeException(String.format("Filesize(%d) has to be at least 3 times the buffer size(3*%d=%d).", file.length(), bufferSize, (3*bufferSize)));
      }
      
      //*** Hash the beginning of stream.
      read = fileInputStream.read(bufferBlock);
      hash32.update(bufferBlock, 0, read);
System.out.println(String.format("Beginning: read %d",read));
      //*** Hash the middle of stream.
      int skipToMiddle = (totalLength / 2) - (bufferSize / 2) - bufferSize;
      fileInputStream.skip(skipToMiddle);
      read = fileInputStream.read(bufferBlock);
      hash32.update(bufferBlock, 0, read);      
System.out.println(String.format("Middle: skip %d, read %d", skipToMiddle, read));
      //*** Hash the end of stream.
      int skipToEnd = totalLength - bufferSize - skipToMiddle - bufferSize - bufferSize;
      fileInputStream.skip(skipToEnd);
      read = fileInputStream.read(bufferBlock);
      hash32.update(bufferBlock, 0, read);
System.out.println(String.format("End: skip %d, read %d", skipToEnd, read));

/*
byte[] totalLengthByte = (totalLength+"").getBytes();
System.out.println("Add "+totalLength);
hash32.update(totalLengthByte, 0, totalLengthByte.length); // Checking hash at 3 spots is not enough. This step is needed.
*/
      fileInputStream.close();
      return hash32.getValue() + ""; // Force to be a string to normalize with other hashing algorithm.

    }
    catch (UnsupportedEncodingException ex)
    {
      ex.printStackTrace();
    }
    catch (IOException ex)
    {
      RuntimeException rException = new RuntimeException(ex.getMessage());
      rException.setStackTrace(ex.getStackTrace());
      throw rException;
    }

    return null; // hash failed
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
