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

public class HashMD5FingerPrintTest
{

  @Test(description="Test md5FingerPrint() is the same as md5().")
  public void md5FingerPrintSameAsMD5()
  {
    File file = Helpers.createTempFile("md5FingerPrintSameAsMD5", "md5FingerPrintSameAsMD5");
    
    String actualHash = Hash.md5FingerPrint(file, 1, "md5FingerPrintSameAsMD5".length()+5); // +5 to ensure bufferSize is bigger than fileSize.
    String expectedHash = Hash.md5(file);
    assertEquals(actualHash, expectedHash, String.format("md5FingerPrint() and md5() should return the exact same hash: %s", file.getAbsolutePath()));
    
    // Clean up.
    file.delete();
  }
  
  
  @Test(description="Test hashing with newline character.")
  public void md5FingerPrintNewlineCharacter()
  {
    File fileNewlineYes = Helpers.createTempFile("md5FingerPrintNewlineCharacterYES", "md5FingerPrintNewlineCharacter");
    File fileNewlineNo  = Helpers.createTempFile("md5FingerPrintNewlineCharacterNO" , "md5FingerPrintNewlineCharacter\n");
    
    int bufferSize=3;
    String hashNewlineYes = Hash.md5FingerPrint(fileNewlineYes, 1, bufferSize);
    String hashNewlineNo  = Hash.md5FingerPrint(fileNewlineNo, 1, bufferSize);
    
    assertNotEquals(hashNewlineYes, hashNewlineNo, 
        String.format("Hash of [%s(%s)] and [%s(%s)] should not be the same. Difference is 1 file has a new line character.", 
                          fileNewlineYes.getName(), hashNewlineYes, fileNewlineNo.getName(), hashNewlineNo));
    
    // Clean up.
    fileNewlineYes.delete();
    fileNewlineNo.delete();
  }  
  
  @Test(description="Test hashing with different in the middle.")
  public void md5FingerPrintDiffInMiddle()
  {
    File file           = Helpers.createTempFile("md5FingerPrintDiffInMiddle", "md5FingerPrintDiffInMiddle");
    File fileDiffMiddle = Helpers.createTempFile("md5FingerPrintDiffInMiddle", "md5FingerPrint DiffInMiddle");
    
    int bufferSize=3;
    int checkFrequency=3;
    String hash            = Hash.md5FingerPrint(file          , checkFrequency, bufferSize);
    String hashDiffMiddel  = Hash.md5FingerPrint(fileDiffMiddle, checkFrequency, bufferSize);
    
    assertNotEquals(hash, hashDiffMiddel, 
        String.format("Hash of [%s(%s)] and [%s(%s)] should not be the same. Difference is in the middle.", 
            file.getName(), hash, fileDiffMiddle.getName(), hashDiffMiddel));
    
    // Clean up.
    file.delete();
    fileDiffMiddle.delete();
  }    
  
  /*************************************************************************************************
   *                                Test error handling.
   *************************************************************************************************/
  
  @Test(description="Test error handling: file not found.", expectedExceptions={RuntimeException.class})
  public void md5FingerPrintFileNotFound()
  {
    File file = new File("./md5FingerPrintFileNotFound.txt");
    Hash.md5FingerPrint(file, 1, 1);
  }
  
  
  @Test(description="Test error handling: filename with wrong encoding.")
  public void md5FingerPrintFilenameWrongEncoding()
  {
    String filename = new String("./md5FingerPrintFilenameWrongEncoding_file\uFFFDname\uFFFD.txt");    
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
        String hash = Hash.md5FingerPrint(file, 1, 1);
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
  public void md5FingerPrintBufferSizeZero()
  {
    File file = new File("./md5FingerPrintBufferSizeZero.txt");
    Hash.md5FingerPrint(file, 1, 0);
  }
  

  
}
