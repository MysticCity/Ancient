package de.pylamo.spellmaker;

import java.text.ParseException;
import javax.swing.plaf.synth.SynthLookAndFeel;

public class LookAndFeel
{
  public SynthLookAndFeel laf;
  
  public LookAndFeel()
  {
    this.laf = new SynthLookAndFeel();
    try
    {
      this.laf.load(Main.class.getResourceAsStream("laf/ancient.xml"), Main.class);
    }
    catch (ParseException e)
    {
      e.printStackTrace();
    }
  }
}
