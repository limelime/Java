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
}
