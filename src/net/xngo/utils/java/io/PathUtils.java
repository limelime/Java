package net.xngo.utils.java.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class PathUtils
{
  public static Path createTempDir()
  {
    return createTempDir("");
  }
  
  public static Path createTempDir(final String prefix)
  {
    try
    {
      return Files.createTempDirectory(prefix);
    }
    catch(IOException ex)
    {
      ex.printStackTrace();
    }
    return null;
  }
  
  public static boolean delete(Path path)
  {
    try
    {
      Files.deleteIfExists(path);
      return true;
    }
    catch(IOException ex)
    {
      ex.printStackTrace();
    }
    
    return false;
  }
  
  public static Path createTempFile()
  {
    return createTempFile(null);
  }
  
  public static Path createTempFile(String content)
  {
    try
    {
      Path tmpFile = Files.createTempFile("", null);
      
      if(content==null)
        Files.write(tmpFile, tmpFile.toString().getBytes("utf-8"), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
      else
        Files.write(tmpFile, content.getBytes("utf-8"), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

      return tmpFile;
    }
    catch(IOException ex)
    {
      ex.printStackTrace();
    }
    return null;
  }  
}
