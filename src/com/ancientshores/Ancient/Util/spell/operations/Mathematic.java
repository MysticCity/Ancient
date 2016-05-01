package com.ancientshores.Ancient.Util.spell.operations;

import com.ancientshores.Ancient.Util.spell.item.SpellItem;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class Mathematic
{
  public static final String OPERATORS = "+-*/^";
  public static final String DECIMAL_SEPERATOR = ".";
  public static final String BRACES = "()";
  
  public static SpellItem operation(String expression)
  {
    return new Operation(expression.replace(";", "").trim());
  }
  
  public static Queue<String> convertToRPN(String input)
  {
    Queue<String> resultQueue = new LinkedList();
    Stack<String> operationsStack = new Stack();
    
    input = input.replaceAll("\\s", "").replaceAll("\\(-", "(0-").replace(".-", ".0-").replace("(+", "(0+").replace(".+", ".0+");
    if ((input.charAt(0) == '-') || (input.charAt(0) == '+')) {
      input = "0" + input;
    }
    System.out.println(input);
    
    StringTokenizer tokenizer = new StringTokenizer(input, "+-*/^()", true);
    while (tokenizer.hasMoreTokens())
    {
      String token = tokenizer.nextToken();
      if (isNumber(token))
      {
        resultQueue.add(token);
      }
      else if (isVariableName(token))
      {
        resultQueue.add(token);
      }
      else if (isOperator(token))
      {
        while ((!operationsStack.isEmpty()) && 
          (isOperator((String)operationsStack.peek()))) {
          if (isLeftAssociative(token))
          {
            if (getPrecedence(token) > getPrecedence((String)operationsStack.peek())) {
              break;
            }
            resultQueue.add(operationsStack.pop());
          }
          else
          {
            if (getPrecedence(token) >= getPrecedence((String)operationsStack.peek())) {
              break;
            }
            resultQueue.add(operationsStack.pop());
          }
        }
        operationsStack.push(token);
      }
      else if (isLeftParenthesis(token))
      {
        operationsStack.push(token);
      }
      else if (isRightParenthesis(token))
      {
        while (!operationsStack.isEmpty())
        {
          if (isLeftParenthesis((String)operationsStack.peek()))
          {
            operationsStack.pop();
            break;
          }
          resultQueue.add(operationsStack.pop());
        }
      }
    }
    while (!operationsStack.isEmpty())
    {
      if (isLeftParenthesis((String)operationsStack.peek()))
      {
        operationsStack.pop();
        break;
      }
      resultQueue.add(operationsStack.pop());
    }
    return resultQueue;
  }
  
  public static String calculateFromRPN(Queue<String> queue)
  {
    Stack<String> buffer = new Stack();
    String[] complex = new String[3];
    for (int i = 0; i < complex.length; i++) {
      complex[i] = "";
    }
    while (!queue.isEmpty())
    {
      String next = (String)queue.poll();
      System.out.println(next);
      if (isOperator(next))
      {
        double x;
        double y;
        double result;
        try
        {
          if (complex[1].equals(""))
          {
            complex[1] = complex[0];
            complex[0] = ((String)buffer.pop());
          }
          x = Double.parseDouble(complex[0]);
          y = Double.parseDouble(complex[1]);
          result = 0.0D;
        }
        catch (Exception ex)
        {
          throw new IllegalArgumentException();
        }
        switch (next.charAt(0))
        {
        case '+': 
          result = x + y;
          break;
        case '-': 
          result = x - y;
          break;
        case '*': 
          result = x * y;
          break;
        case '/': 
          result = x / y;
          break;
        case '^': 
          result = Math.pow(x, y);
        }
        for (int i = 0; i < complex.length; i++) {
          complex[i] = "";
        }
        complex[0] = (result + "");
      }
      else if (complex[0].equals(""))
      {
        complex[0] = next;
      }
      else if (complex[1].equals(""))
      {
        complex[1] = next;
      }
      else
      {
        buffer.push(complex[0]);
        complex[0] = complex[1];
        complex[1] = next;
      }
    }
    return complex[0];
  }
  
  public static boolean isNumber(String expression)
  {
    try
    {
      Double.parseDouble(expression);
      return true;
    }
    catch (Exception ex) {}
    return false;
  }
  
  public static boolean isRightParenthesis(String expresison)
  {
    if (expresison.equals(")")) {
      return true;
    }
    return false;
  }
  
  public static boolean isLeftParenthesis(String expression)
  {
    if (expression.equals("(")) {
      return true;
    }
    return false;
  }
  
  public static int getPrecedence(String expression)
  {
    if (expression.equals("^")) {
      return 2;
    }
    if ((expression.equals("*")) || (expression.equals("/"))) {
      return 1;
    }
    if ((expression.equals("+")) || (expression.equals("-"))) {
      return 0;
    }
    return -1;
  }
  
  public static boolean isLeftAssociative(String expression)
  {
    if (expression.equals("^")) {
      return false;
    }
    return true;
  }
  
  public static boolean isOperator(String token)
  {
    return "+-*/^".contains(token);
  }
  
  public static boolean isVariableName(String token)
  {
    for (char c : token.toCharArray()) {
      if (((c < 'a') || (c > 'z')) && ((c < 'A') || (c > 'Z')) && (c != '_')) {
        return false;
      }
    }
    return true;
  }
}
