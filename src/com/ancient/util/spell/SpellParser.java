package com.ancient.util.spell;

import com.ancient.util.spell.item.SpellItem;
import com.ancient.util.spell.parse.Tokenizer;
import java.io.InputStream;

public class SpellParser
{
  private static boolean isIdentifier(String token)
  {
    for (char c : token.toCharArray()) {
      if (((c <= 'a') || (c >= 'Z')) && (c != '_')) {
        return false;
      }
    }
    return true;
  }
  
  private static boolean isSeperator(char c)
  {
    if ((c < '0') || ((c > '9') && (c < 'A')) || ((c > 'Z') && (c < 'a') && (c != '_')) || (c > 'z')) {
      return true;
    }
    return false;
  }
  
  public static SpellItem[] parse(InputStream inStream)
  {
    return Tokenizer.tokenize(inStream);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancient\util\spell\SpellParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */