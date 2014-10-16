package net.xngo.utils.test.java.util;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

import net.xngo.utils.java.util.CircularList;

public class CircularListTest
{
  @Test(description="Test get max size.")
  public void getMaxSize()
  {
    final int expectedMaxSize = 3;
    CircularList<String> circularList = new CircularList<String>(expectedMaxSize);
    
    // Add more than expected max size.
    for(int i=0; i<expectedMaxSize+3; i++)
    {
      circularList.add(i+"");
    }
    
    assertEquals(circularList.getMaxSize(), expectedMaxSize);
  }
  
  @Test(description="Test add().")
  public void add()
  {
    final String[] oldValues = {"1", "2", "3"};
    final String[] newValues = {"4", "5", "6"};
    CircularList<String> circularList = new CircularList<String>(oldValues.length);
    
    // Add values in list.
    for(int i=0; i<oldValues.length; i++)
    {
      circularList.add(oldValues[i]);
    }
    
    // Overwriting old values with new values. 
    for(int i=0; i<newValues.length; i++)
    {
      circularList.add(newValues[i]);
    }
    
    /** Main test: Old values are overwritten by new values.**/
    for(int i=0; i<newValues.length; i++)
    {
      assertEquals(circularList.get(i), newValues[i]);
    }
  }
}
