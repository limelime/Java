package net.xngo.utils.bench.java.math;

import java.io.File;

import net.xngo.utils.java.math.Hash;

public class BufferSizeBenchmark
{

  public static void main(String[] args)
  {
    File file = new File(args[0]);
    int bufferSize = Integer.parseInt(args[1]);
    
    long start = System.currentTimeMillis();
    String hash = Hash.md5(file, bufferSize);
    long end = System.currentTimeMillis();
    long runtime = end-start;

    System.out.println(String.format("%,d B | %,d ms| %,d B | %s", bufferSize, runtime, file.length(), file.getAbsolutePath()));
  }

}
