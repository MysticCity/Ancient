package com.ancient.util.spell.operations;

import com.ancient.util.spell.ExecutionReturn;
import com.ancient.util.spell.data.Data;
import com.ancient.util.spell.item.SpellItem;
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
