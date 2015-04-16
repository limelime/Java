package net.xngo.utils.java.test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Helpers
{
  public static File createTempFile(final String affix)
  {
    return Helpers.createTempFile(affix, null, null);
  }
  
  public static File createTempFile(final String affix, final File directory)
  {
    return Helpers.createTempFile(affix, directory, null);
  }

  public static File createTempFile(final String affix, final String content)
  {
    return Helpers.createTempFile(affix, null, content);
  }
  
  public static File createTempFile(final String affix, final File directory, final String content)
  {
    File uniqueFile = null;
    try
    {
      final String prefix = String.format("Test_%s_", affix);
      final String suffix = ".tmp";
      uniqueFile = File.createTempFile(prefix, suffix, directory);
      
      if(content==null)
        FileUtils.writeStringToFile(uniqueFile, uniqueFile.getAbsolutePath(), true);
      else
        FileUtils.writeStringToFile(uniqueFile, content, true);
    }
    catch(IOException e)
    {
      e.printStackTrace();
    }
    return uniqueFile;
  }
  
  public static void writeStringToFile(final File file, final String content, final boolean append)
  {
    try
    {
      FileUtils.writeStringToFile(file, content, append);
    }
    catch(IOException e)
    {
      e.printStackTrace();
    }
  }  
}
