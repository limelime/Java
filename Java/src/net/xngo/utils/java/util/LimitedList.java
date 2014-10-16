package net.xngo.utils.java.util;

import java.util.ArrayList;
/**
 * http://stackoverflow.com/questions/1963806/is-there-a-fixed-sized-queue-which-removes-excessive-elements
 * @author Xuan Ngo
 *
 * @param <K>
 */
public class LimitedList<K> extends ArrayList<K> 
{
  
  /**
   * 
   */
  private static final long serialVersionUID = 2784995695378956707L;
  
  private int maxSize;

  public LimitedList(int size)
  {
    this.maxSize = size;
  }

  public boolean add(K k)
  {
    boolean r = super.add(k);
    if (size() > maxSize)
    {
      removeRange(0, size() - maxSize - 1);
    }
    return r;
  }
  
  public int getMaxSize()
  {
    return this.maxSize;
  }
}