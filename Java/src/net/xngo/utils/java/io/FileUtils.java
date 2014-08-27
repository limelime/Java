package net.xngo.utils.java.io;

import java.text.DecimalFormat;
import java.io.File;
import java.util.List;

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
  public static String readableFileSize(long size) 
  {
    if(size <= 1) return size +" byte";
    final String[] units = new String[] { "bytes", "KB", "MB", "GB", "TB", "PB", "EB" };
    int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
    return new DecimalFormat("#,##0.##").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
  }
  
  public static long getTotalSize(List<File> files)
  {
    int size = 0;
    for(File f: files)
    {
      size += f.length();
    }
    return size;
  }
  
}
