package net.xngo.utils.java.math;

import java.text.DecimalFormat;

public class Math
{
  public static double percentage(long a, long b)
  {
    return ((double)a/(double)b)*100.0;
  }
  
  public static String getReadablePercentage(long a, long b)
  {
    double percent = percentage(a, b);
    
    return new DecimalFormat("0.00").format(percent)+"%";
  }
}
