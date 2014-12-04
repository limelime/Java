package net.xngo.utils.java.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
}
