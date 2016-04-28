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


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancient\util\spell\operations\Operation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */