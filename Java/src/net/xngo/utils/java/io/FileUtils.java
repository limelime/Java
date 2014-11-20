package net.xngo.utils.java.io;

import java.text.DecimalFormat;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.filefilter.TrueFileFilter;

public class FileUtils
{
  

  private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
  
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
  public static Set<File> listFiles(List<File> paths)
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
        try
        {
          System.out.println(String.format("[Warning] -> [%s] doesn't exist.", path.getCanonicalPath()));
        }
        catch(IOException ex)
        {
          ex.printStackTrace();
        }
      }
    }
    
    return listOfAllUniqueFiles;
  }
  
  public static String load(String filepath)
  {
    File file = new File(filepath);
    try
    {
      return org.apache.commons.io.FileUtils.readFileToString(file, UTF8_CHARSET);
    }
    catch(IOException ex)
    {
      ex.printStackTrace();
    }
    throw new RuntimeException("Unexpected error: This code line in FileUtils.load() should never be executed.");
  }
  
  public static void zip(String sourcePath, String destinationPath)
  {
    byte[] buffer = new byte[1024];
    
    try
    {

      FileOutputStream fos = new FileOutputStream(destinationPath);
      ZipOutputStream zos = new ZipOutputStream(fos);
      ZipEntry ze= new ZipEntry(new File(sourcePath).getName()); // File path in the zip file.
      zos.putNextEntry(ze);
      FileInputStream in = new FileInputStream(sourcePath);

      int length;
      while ((length = in.read(buffer)) > 0)
      {
        zos.write(buffer, 0, length);
      }

      in.close();
      zos.closeEntry();

      zos.close();

    }catch(IOException ex)
    {
       ex.printStackTrace();
    }    
  }
  
}
