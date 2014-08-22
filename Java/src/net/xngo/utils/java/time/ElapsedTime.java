package net.xngo.utils.java.time;

/**
 * Measure time elapsed.
 * @author Xuan Ngo
 *
 */
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
 
public class ElapsedTime 
{
  private Calendar startTime = null;
  private Calendar endTime   = null;
 
 
  public ElapsedTime(){}
 
  public void start()
  {
    this.startTime = Calendar.getInstance();
  }
  public void stop()
  {
    this.endTime = Calendar.getInstance();
  }
 
  /**
   * @return Return the elapsed time measured in milliseconds.
   */
  public final long getDiffInMillis()
  {
    return this.endTime.getTimeInMillis() - this.startTime.getTimeInMillis();
  }
 
  /**
   * @return Return the elapsed time measured as HH:MM:SS.mmmm.
   */
  public final String getDiffInString()
  {
    /***********************
     * Calculate statistics. 
     ***********************/
    long lTotalRuntime = this.getDiffInMillis();
    long lRuntime = lTotalRuntime;
 
    long lRuntimeHrs = lRuntime/(1000*3600);
    lRuntime = lRuntime - (lRuntimeHrs*1000*3600);// Runtime remaining.
    long lRuntimeMin = (lRuntime)/(1000*60);
    lRuntime = lRuntime - (lRuntimeMin*1000*60);  // Runtime remaining.
    long lRuntimeSec = lRuntime/(1000);
    lRuntime = lRuntime - (lRuntimeSec*1000);     // Runtime remaining.
 
    return String.format("%02d", lRuntimeHrs)+":"+String.format("%02d", lRuntimeMin)+":"+String.format("%02d", lRuntimeSec)+"."+lRuntime;
  }
 
  public void report()
  {
    // Start at YYYY-MM-DD HH:MM:SS.mmm
    System.out.println("\nStart at "+this.getDateTimeFormatted(this.startTime));
 
    // Ran for HH:MM:SS.mmm (milliseconds)
    System.out.println("Ran for "+this.getDiffInString());
 
    // End at YYYY-MM-DD HH:MM:SS.mmm
    System.out.println("End at "+this.getDateTimeFormatted(this.endTime));
  }
 
  private final String getDateTimeFormatted(Calendar oCalendar)
  {
    final String dateFormat = "yyyy-MM-dd_HH:mm:ss.SSSS";
 
    Date currentDate = oCalendar.getTime();
    SimpleDateFormat oSimpleDateFormat = new SimpleDateFormat(dateFormat);
    return oSimpleDateFormat.format(currentDate);
  }
}

