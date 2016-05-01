package com.ancientshores.Ancient.Util.spell;

import com.ancientshores.Ancient.Util.spell.item.SpellItem;
import com.ancientshores.Ancient.Util.spell.parse.Tokenizer;
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
