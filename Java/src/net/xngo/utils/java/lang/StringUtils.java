package net.xngo.utils.java.lang;

import java.util.List;
import java.util.ArrayList;


public class StringUtils
{
  /**
   * Find string in words of a string.
   * @param source
   * @param keyword
   * @return Number of time the string in found.
   */
  public static List<String> findStringWords(String source, String keyword)
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
   * 
   * @param strings
   * @param keyword
   * @return Index of string found. Otherwise, return -1 for not found.
   */
  public static int indexOfStrings(String[] strings, String keyword)
  {
    int index=-1;
    for(int i=0; i<strings.length; i++)
    {
      if(strings[i].contains(keyword))
        return i;
    }
    return index;
  }
  
  public static int indexOfKeywords(String string, String[] keywords)
  {
    int index=-1;
    for(int i=0; i<keywords.length; i++)
    {
      if(string.contains(keywords[i]))
        return i;
    }
    return index;
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
