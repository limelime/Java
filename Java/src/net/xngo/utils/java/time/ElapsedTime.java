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
 
  private String startTimeString    = "";
  private String endTimeString      = "";
  private String elapsedTimeString  = "";
 
  public ElapsedTime(){}
 
  public void start()
  {
    this.startTime = Calendar.getInstance();
  }
  
  public void setStart(final Calendar calendar)
  {
    this.startTime = calendar;
  }
  
  public void stop()
  {
    this.endTime = Calendar.getInstance();
  }
 
  public void setStop(final Calendar calendar)
  {
    this.endTime = calendar;
  }
  
  /**
   * @return Return the elapsed time measured in milliseconds.
   */
  public final long getDiffInMillis()
  {
    return this.endTime.getTimeInMillis() - this.startTime.getTimeInMillis();
  }
 
  public void display()
  {
    // Start at YYYY-MM-DD HH:MM:SS.mmm
    this.startTimeString = String.format("Start at %s", this.getStartTime());
    System.out.println(this.startTimeString);
    
    // End at YYYY-MM-DD HH:MM:SS.mmm
    this.endTimeString = String.format("End   at %s", this.getEndTime());
    System.out.println(this.endTimeString);
    
    // Ran for HH:MM:SS.mmm (milliseconds)
    this.elapsedTimeString = String.format("Ran  for %s", this.getElapsedTime());
    System.out.println(this.elapsedTimeString);
  }
  
  public String getStartTime()
  {
    return this.getDateTimeFormatted(this.startTime);
  }
  public String getEndTime()
  {
    return this.getDateTimeFormatted(this.endTime);
  }
  
  /**
   * @return Return the elapsed time measured as HH:MM:SS.mmmm.
   */
  public final String getElapsedTime()
  {

    long lTotalRuntime = this.getDiffInMillis();
    long lRuntime      = lTotalRuntime;
 
    // Calculate hours, minutes and seconds.     
    long lRuntimeHrs = lRuntime/(1000*3600);
    lRuntime         = lRuntime - (lRuntimeHrs*1000*3600);// Runtime remaining.
    long lRuntimeMin = (lRuntime)/(1000*60);
    lRuntime         = lRuntime - (lRuntimeMin*1000*60);  // Runtime remaining.
    long lRuntimeSec = lRuntime/(1000);
    lRuntime         = lRuntime - (lRuntimeSec*1000);     // Runtime remaining.
 
    return String.format("%02d:%02d:%02d.%d", lRuntimeHrs, lRuntimeMin, lRuntimeSec, lRuntime);
  }
  
  private final String getDateTimeFormatted(Calendar oCalendar)
  {
    final String dateFormat = "yyyy-MM-dd HH:mm:ss.SSSS";
 
    Date currentDate = oCalendar.getTime();
    SimpleDateFormat oSimpleDateFormat = new SimpleDateFormat(dateFormat);
    return oSimpleDateFormat.format(currentDate);
  }
}

