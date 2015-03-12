package net.xngo.utils.test.java.math;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.xngo.utils.java.io.FileUtils;
import net.xngo.utils.java.math.Hash;
import net.xngo.utils.java.test.Helpers;

import org.testng.annotations.Test;

public class HashXX32SpreadTest
{

  @Test(description="Test simple hash of file.")
  public void xxhash32SpreadSimple()
  {
    File file = Helpers.createTempFile("xxhash32Spread", "xxhash32Spread");
    
    String hash = Hash.xxhash32Spread(file, 1);
    
    assertEquals(hash, "-773304053", String.format("[%s] has the wrong expected hash.", file.getAbsolutePath()));
    
    // Clean up.
    file.delete();
  }
  
  @Test(description="Test xxhash32Spread() to have the same hash as xxhash32().")
  public void xxhash32SpreadSameHash()
  {
    //*** Preconditions:
    //      -Total bytes = 3
    //      -xxhash32Spread()'s buffer size = 1
    //      -Seed of xxhash32Spread() and xxhash32() should be the same.
    
    File file = Helpers.createTempFile("xxhash32SpreadSameHash", "x3H"); // Should only be 3 bytes.
    
    String hash       = Hash.xxhash32(file);    
    String hashSpread = Hash.xxhash32Spread(file, 1);

    assertEquals(hashSpread, hash, String.format("xxhash32Spread() and xxhash32() should return the exact same hash.", file.getAbsolutePath()));
    
    // Clean up.
    file.delete();
  }  
  
  @Test(description="Test hashing with newline character.")
  public void xxhash32SpreadNewlineCharacter()
  {
    File fileNewlineYes = Helpers.createTempFile("xxhash32SpreadNewlineCharacterYES", "xxhash32SpreadNewlineCharacter");
    File fileNewlineNo  = Helpers.createTempFile("xxhash32SpreadNewlineCharacterNO" , "xxhash32SpreadNewlineCharacter\n");
    
    int bufferSize=3;
    String hashNewlineYes = Hash.xxhash32Spread(fileNewlineYes, bufferSize);
    String hashNewlineNo  = Hash.xxhash32Spread(fileNewlineNo, bufferSize);
    
    assertNotEquals(hashNewlineYes, hashNewlineNo, 
        String.format("Hash of [%s(%s)] and [%s(%s)] should not be the same. Difference is 1 file has a new line character.", 
                          fileNewlineYes.getName(), hashNewlineYes, fileNewlineNo.getName(), hashNewlineNo));
    
    // Clean up.
    fileNewlineYes.delete();
    fileNewlineNo.delete();
  }  
  
  
  /*************************************************************************************************
   *                                Test error handling.
   *************************************************************************************************/
  
  @Test(description="Test error handling: file not found.", expectedExceptions={RuntimeException.class})
  public void xxhash32SpreadFileNotFound()
  {
    File file = new File("./xxhash32SpreadFileNotFound.txt");
    Hash.xxhash32Spread(file, 2);
  }
  
  
  @Test(description="Test error handling: filename with wrong encoding.")
  public void xxhash32SpreadFilenameWrongEncoding()
  {
    String filename = new String("./xxhash32SpreadFilenameWrongEncoding_file\uFFFDname\uFFFD.txt");    
    try
    {
      //String filename = new String("./filename\uFFFD.txt".getBytes("UTF-8"));
      FileWriter oFileWriter = new FileWriter(filename);
      BufferedWriter oBufferedWriter = new BufferedWriter(oFileWriter);
      oBufferedWriter.write("Java");
      oBufferedWriter.close();
      System.out.println("done.");
      
      List<File> files = new ArrayList<File>();
      files.add(new File(filename));
      Set<File> listOfFiles = FileUtils.listFiles(files);
      for (File file : listOfFiles) 
      {
        String hash = Hash.xxhash32Spread(file, 1);
        System.out.println(file.getName()+": "+hash);        
      }
      
    }
    catch(IOException ex)
    {
      System.out.println(ex.getMessage());
    }
    finally
    {
      new File(filename).delete();
    }

  }

  @Test(description="Test error handling: buffer size is zero.", expectedExceptions={RuntimeException.class})
  public void xxhash32SpreadBufferSizeZero()
  {
    File file = new File("./xxhash32SpreadBufferSizeZero.txt");
    Hash.xxhash32Spread(file, 0);
  }
  
  @Test(description="Test error handling: File size smaller than 3 time the buffer.", expectedExceptions={RuntimeException.class})
  public void xxhash32SpreadFileSizeLessThan3XBuffer()
  {
    File file = Helpers.createTempFile("xxhash32SpreadFileSizeLessThan3XBuffer", "xxhash32SpreadFileSizeLessThan3XBuffer");
    
    Hash.xxhash32Spread(file, (int)(file.length()/2));
    
    // Clean up.
    file.delete();
  }  
  
}
