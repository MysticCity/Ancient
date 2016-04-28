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


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\de\pylamo\spellmaker\LookAndFeel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */