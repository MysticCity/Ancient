package com.ancientshores.Ancient.Util.spell.operations;

import com.ancientshores.Ancient.Util.spell.ExecutionReturn;
import com.ancientshores.Ancient.Util.spell.data.Data;
import com.ancientshores.Ancient.Util.spell.item.SpellItem;
import java.util.Queue;

public class Operation
  implements SpellItem
{
  private Queue<String> queue;
  
  public ExecutionReturn excecute(Data... data)
  {
    return null;
  }
  
  public Operation(String expression)
  {
    this.queue = Mathematic.convertToRPN(expression);
  }
}
