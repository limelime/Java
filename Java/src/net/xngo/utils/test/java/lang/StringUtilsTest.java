package net.xngo.utils.test.java.lang;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


import net.xngo.utils.java.lang.StringUtils;

import org.testng.annotations.Test;

public class StringUtilsTest
{
  @Test(description="findWords(): Check words found count.")
  public void findWordsBasic()
  {
    String str="I will try to trick you and you will not know it.";
    int wordsFoundCnt = StringUtils.findWords(str, "you").size();
    assertThat(wordsFoundCnt, equalTo(2));
  }
  
  @Test(description="findWords(): Not found.")
  public void findWordsNotFound()
  {
    String str="I will try to trick you and you will not know it.";
    int wordsFoundCnt = StringUtils.findWords(str, "youNotFound").size();
    assertThat(wordsFoundCnt, equalTo(0));
  }  
}
