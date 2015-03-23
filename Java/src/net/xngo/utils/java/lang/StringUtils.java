package net.xngo.utils.java.lang;

import java.util.List;
import java.util.ArrayList;


public class StringUtils
{
  public static List<String> findWords(String source, String keyword)
  {
    List<String> wordsFound = new ArrayList<String>();
    
    String[] tokens = source.split(" ");
    for(String token: tokens)
    {
      if(token.contains(keyword))
        wordsFound.add(token);
    }
    
    return wordsFound;
  }
  
  /**
   * @param str
   * @return True if string contains hexadecimal characters. Otherwise, false.
   */
  public static boolean isHex(String str)
  {
    boolean isHex; 
    for(int i=0; i<str.length(); i++)
    {
      char c = str.charAt(i);
      isHex = ((c >= '0' && c <= '9') || 
               (c >= 'a' && c <= 'f') || 
               (c >= 'A' && c <= 'F'));

      if(!isHex)
          return false;
    }
    return true;    
  }
}
