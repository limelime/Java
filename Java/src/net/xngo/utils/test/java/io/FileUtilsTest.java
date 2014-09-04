package net.xngo.utils.test.java.io;

import java.io.File;

import net.xngo.utils.java.io.FileUtils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;
import org.testng.annotations.Test;

public class FileUtilsTest
{
  @Test(description="Check different sizes.")
  public void readableFileSize()
  {
    long[] sizes = {0, 1, 937,                                // Less than 1 kilobyte.
                    1024L,                                    // KB
                    1124L*1024L,                              // MB
                    2224L*1024L*1024L,                        // GB
                    3324L*1024L*1024L*1024L,                  // TB
                    4424L*1024L*1024L*1024L*1024L,            // PB
                    5524L*1024L*1024L*1024L*1024L*1024L,      // EB
                    /*1524L*1024L*1024L*1024L*1024L*1024L*1024L*/};// Bigger than EB: How to handle overflow? 
    String[] expectedSizes = {  "0 byte",
                                "1 byte",
                                "937 bytes",
                                "1 KB",
                                "1.1 MB",
                                "2.17 GB",
                                "3.25 TB",
                                "4.32 PB",
                                "5.39 EB",
                              };
    
    for(int i=0; i<sizes.length; i++)
    {
      assertEquals(FileUtils.readableSize(sizes[i]), expectedSizes[i], String.format("Failed at index=%d, size=%d bytes.", i, sizes[i]));
    }
    
  }
  
}
