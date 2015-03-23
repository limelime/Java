package net.xngo.utils.test.java.lang;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;


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
  
  @Test(description="isHex(): Test uppercase hexadecimal string.")
  public void isHexUppercaseHexString()
  {
    String str="B42A0FF8B903CEEC1BF229C232F64821";
    assertTrue(StringUtils.isHex(str));
  }
  
  @Test(description="isHex(): Test aphabet non-hexadecimal string.")
  public void isHexNonHexString()
  {
    String str="GHIJKLMNOPQRSTUVWXYZ";
    assertFalse(StringUtils.isHex(str));
  }
  
  @Test(description="isHex(): Test non-aphabet string.")
  public void isHexNonAphaString()
  {
    String str="~!@#$%^ &*()_+=";
    assertFalse(StringUtils.isHex(str));
  }   
  
}
