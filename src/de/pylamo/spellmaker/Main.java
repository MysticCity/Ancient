package de.pylamo.spellmaker;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.UIManager;

public class Main
{
  public static Image icon;
  
  public static void main(String[] args)
  {
    System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
    try
    {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception ignored) {}
    try
    {
      icon = ImageIO.read(Main.class.getResource("ancient.png"));
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    new Menu().setVisible(true);
  }
}
