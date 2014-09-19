package net.xngo.utils.test.java.time;

import static org.testng.Assert.assertEquals;

import java.util.Calendar;

import net.xngo.utils.java.time.ElapsedTime;

import org.testng.annotations.Test;

public class ElapsedTimeTest
{
  @Test(description="Check day, minutes, hours, seconds.")
  public void getElapsedTime()
  {
    Calendar now = Calendar.getInstance();
    Calendar later = Calendar.getInstance();
    
    ElapsedTime elapsedTime = new ElapsedTime();
    elapsedTime.setStart(now);
    
    // Add day, minutes, hours, seconds.
    later.setTimeInMillis(now.getTimeInMillis());
    later.add(Calendar.DATE, 1);
    later.add(Calendar.MINUTE, 59);
    later.add(Calendar.SECOND, 1);
    
    elapsedTime.setStop(later);
    
    assertEquals(elapsedTime.getElapsedTime(), "24:59:01.0");
    
    elapsedTime.display();
  }

  
}
