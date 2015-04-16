package net.xngo.utils.test.java.time;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

import java.util.Calendar;

import org.testng.annotations.Test;

import net.xngo.utils.java.time.Chronometer;

public class ChronometerTest
{
  @Test(description="formatTime(): Zero padding on 1 millisecond.")
  public void formatTimeMillisZeroPadding()
  {
    Chronometer chrono = new Chronometer();
    assertThat(chrono.formatTime(1), equalTo("00:00:00.001"));
  }
  
  @Test(description="formatTime(): Check hours, minutes, seconds, millisecond.")
  public void formatTimeBasic()
  {
    //*** Prepare data.
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(0); // Reset cal to zero.
    cal.add(Calendar.HOUR_OF_DAY, 23);
    cal.add(Calendar.MINUTE, 59);
    cal.add(Calendar.SECOND, 1);
    cal.add(Calendar.MILLISECOND, 37);
    
    //*** Main test & Validation
    Chronometer chrono = new Chronometer();
    assertThat(chrono.formatTime(cal.getTimeInMillis()), equalTo("23:59:01.037"));

  }  
}
