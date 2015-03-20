package net.xngo.utils.test.java.math;

import static org.testng.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import org.testng.annotations.Test;

import net.xngo.utils.java.io.FileUtils;
import net.xngo.utils.java.io.PathUtils;
import net.xngo.utils.java.math.Hash;
import net.xngo.utils.java.test.Helpers;

public class HashTestMD5
{

  @Test(description="Test hashing file using md5.")
  public void md5file()
  {
    File file = Helpers.createTempFile("md5", "md5");
    
    String hash = Hash.md5(file);
    
    assertEquals(hash, "1bc29b36f623ba82aaf6724fd3b16718", String.format("[%s] has the wrong expected hash.", file.getAbsolutePath()));
    
    // Clean up.
    file.delete();
  }
  
  /*************************************************************************************************
   *                                Test error handling.
   *************************************************************************************************/
  
  @Test(description="Test error handling: file not found.", expectedExceptions={RuntimeException.class})
  public void xxhash32FileNotFound()
  {
    File file = new File("./xxhash32FileNotFound.txt");
    Hash.xxhash32(file);
  }
  
  
  @Test(description="Test error handling: filename with wrong encoding.")
  public void xxhash32FilenameWrongEncoding()
  {
    String filename = new String("./xxhash32FilenameWrongEncoding_file\uFFFDname\uFFFD.txt");    
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
        String hash = Hash.xxhash32(file);
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
  
  /**************************************************************************************************************
   *******************************************  NIO  *******************************************
   **************************************************************************************************************/
  @Test(description="Test hashing directory using xxhash32.", expectedExceptions={RuntimeException.class})
  public void xxhash32Directory()
  {
    Path dirPath = PathUtils.createTempDir("xxhash32");
    try
    {
      Hash.xxhash32(dirPath);
    }
    finally
    {
      PathUtils.delete(dirPath);
    }
  }
  
  @Test(description="Test hashing file path using xxhash32.")
  public void xxhash32Path()
  {
    //*** Create data.
    Path tmpFile = PathUtils.createTempFile("xxhash32");
    
    //*** Main test: hash the temporary file.
    String hash = Hash.xxhash32(tmpFile);
    
    //*** Validation
    assertEquals(hash, "-1107888657", String.format("[%s] has the wrong expected hash.", tmpFile.toString()));
    
    //*** Clean up.
    PathUtils.delete(tmpFile);  
  }
  
}
