package net.xngo.utils.test.java.util;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

import net.xngo.utils.java.util.CircularArrayList;

public class CircularArrayListTest
{
  @Test(description="Test array size more than capacity.")
  public void capacityArraySizeMore()
  {
    final int capacity = 3;
    CircularArrayList<String> circularList = new CircularArrayList<String>(capacity);
    
    // Add more than expected max size.
    for(int i=0; i<capacity+3; i++)
    {
      circularList.add(i+"");
    }
    
    assertEquals(circularList.capacity(), capacity);
  }
  
  @Test(description="Test array size less than capacity.")
  public void capacityArraySizeLess()
  {
    final int expectedMaxSize = 3;
    CircularArrayList<String> circularList = new CircularArrayList<String>(expectedMaxSize);
    
    // Add more than expected max size.
    for(int i=0; i<expectedMaxSize-1; i++)
    {
      circularList.add(i+"");
    }
    
    assertEquals(circularList.capacity(), expectedMaxSize);
  }
  
  @Test(description="Test add().")
  public void add()
  {
    final String[] oldValues = {"1", "2", "3"};
    final String[] newValues = {"A", "B", "C"};
    final int capacity = oldValues.length;
    CircularArrayList<String> circularList = new CircularArrayList<String>(capacity);
    
    // Add values in list.
    for(int i=0; i<capacity; i++)
    {
      circularList.add(oldValues[i]);
    }
    
    // Overwriting old values with new values. 
    for(int i=0; i<capacity; i++)
    {
      circularList.add(newValues[i]);
    }
    
    /** Main test: Old values are overwritten by new values.**/
    assertEquals(circularList.capacity(), capacity);
    
    for(int i=0; i<newValues.length; i++)
    {
      assertEquals(circularList.get(i), newValues[i]);
    }
  }
  
  @Test(description="Test get() with negative index.", expectedExceptions=java.lang.IndexOutOfBoundsException.class)
  public void getNegative()
  {
    final int capacity = 2;
    CircularArrayList<String> circularList = new CircularArrayList<String>(capacity);
    circularList.get(-1);
  }
  
  @Test(description="Test get() with index bigger than array size.", expectedExceptions=java.lang.IndexOutOfBoundsException.class)
  public void getIndexBiggerThanSize()
  {
    final int capacity = 2;
    CircularArrayList<String> circularList = new CircularArrayList<String>(capacity);
    circularList.get(capacity);
    circularList.get(capacity*2);
  }
  
}
