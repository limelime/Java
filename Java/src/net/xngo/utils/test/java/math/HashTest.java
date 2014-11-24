package net.xngo.utils.test.java.math;

import static org.testng.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import org.testng.annotations.Test;

import net.xngo.utils.java.io.FileUtils;
import net.xngo.utils.java.math.Hash;
import net.xngo.utils.java.test.Helpers;

public class HashTest
{

  @Test(description="Test hashing file using xxhash32.")
  public void xxhash32()
  {
    File file = Helpers.createTempFile("xxhash32", "xxhash32");
    
    String hash = Hash.xxhash32(file);
    
    assertEquals(hash, "-1107888657", String.format("[%s] has the wrong expected hash.", file.getAbsolutePath()));
    
    // Clean up.
    file.delete();
  }
  
  @Test(description="Test hashing file not found.", expectedExceptions={RuntimeException.class})
  public void xxhash32FileNotFound()
  {
    File file = new File("./xxhash32FileNotFound.txt");
    Hash.xxhash32(file);
  }
  
  
  @Test(description="Test hashing file using xxhash32.")
  public void xxhash32FilenameWrongEncoding()
  {
    try
    {
      //String filename = new String("./filename\uFFFD.txt".getBytes("UTF-8"));
      String filename = new String("./filename\uFFFD.txt");
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
  }
  
}
