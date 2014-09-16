package net.xngo.utils.java.io;

import java.text.DecimalFormat;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.filefilter.TrueFileFilter;

public class FileUtils
{
  /**
   * Return human readable file size. This function is limited to exabyte.
   * Note: How to handle overflow or underflow of long?
   * Source: http://stackoverflow.com/questions/3263892/format-file-size-as-mb-gb-etc
   * 
   * @param size
   * @return a human-readable display value (includes units - EB, PB, TB, GB, MB, KB or bytes).
   */
  public static String readableSize(long size) 
  {
    if(size <= 1) return size +" byte";
    final String[] units = new String[] { "bytes", "KB", "MB", "GB", "TB", "PB", "EB" };
    int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
    return new DecimalFormat("#,##0.##").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
  }
  
  public static long totalSize(List<File> files)
  {
    long size = 0;
    for(File f: files)
    {
      size += f.length();
    }
    return size;
  }
  
  public static long totalSize(Set<File> files)
  {
    long size = 0;
    for(File f: files)
    {
      size += f.length();
    }
    return size;
  }

  /**
   * Get a list of unique files from a set of paths(files or directories).
   * @param paths
   * @return list of unique files
   */
  public static Set<File> listFiles(Set<File> paths)
  {
    Set<File> listOfAllUniqueFiles = new HashSet<File>();
    
    for(File path: paths)
    {
      if(path.exists())
      {
        try
        {
          File canonicalFilePath = path.getCanonicalFile(); // Get all input files/directories paths as canonical to ensure that there will be no duplicate.
          if(canonicalFilePath.isFile())
          {
            listOfAllUniqueFiles.add(canonicalFilePath);
          }
          else
          {// It is a directory.
            Collection<File> filesList = org.apache.commons.io.FileUtils.listFiles(canonicalFilePath, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
            listOfAllUniqueFiles.addAll(filesList);
          }
        }
        catch(IOException e)
        {
          e.printStackTrace();
        }
        
      }
      else
      {
        System.out.println(String.format("[Warning] -> [%s] doesn't exist.", path.getAbsolutePath()));
      }
    }
    
    return listOfAllUniqueFiles;
  }
  
}
