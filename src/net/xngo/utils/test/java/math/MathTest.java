package net.xngo.utils.test.java.math;

import static org.testng.Assert.assertEquals;

import net.xngo.utils.java.math.Math;

import org.testng.annotations.Test;

public class MathTest
{
  @Test(description="Test positive percentage.")
  public void percentagePositive()
  {
    assertEquals(Math.percentage(1, 2), 50.0);
  }
  
  
  
  @Test(description="Test negative percentage.")
  public void percentageNegative()
  {
    assertEquals(Math.percentage(-1, 2), -50.0);
  }
  
  @Test(description="Test 100%.")
  public void percentage100()
  {
    assertEquals(Math.percentage(56252470681L, 56252470681L), 100.0);
  }
  
  @Test(description="Test readable positive percentage.")
  public void getReadablePercentagePositive()
  {
    assertEquals(Math.getReadablePercentage(1, 60), "1.67%");
  }
  
  @Test(description="Test readable positive percentage, over 100.")
  public void getReadablePercentageOver100()
  {
    assertEquals(Math.getReadablePercentage(7, 3), "233.33%");
  }
  
  @Test(description="Test 100%..")
  public void getReadablePercentage100()
  {
    assertEquals(Math.getReadablePercentage(56252470681L, 56252470681L), "100.00%");
  }  
  
}
