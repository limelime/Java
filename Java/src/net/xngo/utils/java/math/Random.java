package net.xngo.utils.java.math;

public class Random
{
  private static java.util.Random random = new java.util.Random(System.currentTimeMillis());
  
  /**
   * http://stackoverflow.com/questions/363681/generating-random-integers-in-a-range-with-java
   * 
   * Returns a pseudo-random number between min and max, inclusive.
   * The difference between min and max can be at most
   * <code>Integer.MAX_VALUE - 1</code>.
   * 
   * @param min Minimum value
   * @param max Maximum value.  Must be greater than min.
   * @return Integer between min and max, inclusive.
   * @see java.util.Random#nextInt(int)
   */
  public static int Int(int min, int max) {

      // NOTE: Usually this should be a field rather than a method
      // variable so that it is not re-seeded every call.
      Random rand = new Random();

      // nextInt is normally exclusive of the top value,
      // so add 1 to make it inclusive
      int randomNum = random.nextInt((max - min) + 1) + min;

      return randomNum;
  }
  
  public static int Int()
  {
    return random.nextInt();
  }
  
}
