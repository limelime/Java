package net.xngo.utils.java.time;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalUtils
{
  /**
   * For more details about dateFormat, see http://docs.oracle.com/javase/6/docs/api/java/text/SimpleDateFormat.html
   * @param cal
   * @param dateFormat e.g. "yyyy-MM-dd HH:mm:ss.SSSS"
   * @return
   */
  public static String toString(Calendar cal, String dateFormat)
  {
    Date date = cal.getTime();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
    return simpleDateFormat.format(date);    
  }
  
  public static String toString(String dateFormat)
  {
    return CalUtils.toString(Calendar.getInstance(), dateFormat);
  }
}
