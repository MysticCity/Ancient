package de.pylamo.spellmaker;

import java.text.ParseException;

import javax.swing.plaf.synth.SynthLookAndFeel;

public class LookAndFeel {
	public SynthLookAndFeel laf;

	public LookAndFeel() {
		laf = new SynthLookAndFeel();
		
		try {
			laf.load(Main.class.getResourceAsStream("laf/ancient.xml"), Main.class);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
//		laf;
	}
	
//	@Override
//	public SynthStyle getStyle(JComponent c, Region id) {
//		if (id == Region.BUTTON) {
//			return buttonStyle;
//		}
//		else if (id == Region.TREE) {
//			return treeStyle;
//		}
//		return defaultStyle;
//	}
}
