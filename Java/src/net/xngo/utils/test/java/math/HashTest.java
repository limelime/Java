package net.xngo.utils.test.java.math;

import static org.testng.Assert.assertEquals;

import java.io.File;

import org.testng.annotations.Test;

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
  
}
