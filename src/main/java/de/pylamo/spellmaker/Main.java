package de.pylamo.spellmaker;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.UIManager;

public class Main {
	public static Image icon;
   
	public static void main(String[] args) {
		
		/*
		 * Der folgende Code unterbindet eine Exception die durch einen Bug im Java RE/JDK 7 ausgelöst wird.
		 * Immer mal wieder Testen, ob der Bug weiter besteht.
		 * Falls er nicht mehr existiert diese Zeile auskommentieren oder löschen.
		 * 
		 * Es handelt sich um folgende Exception:
		 * 
		 * Exception in thread "AWT-EventQueue-0" java.lang.IllegalArgumentException: Comparison method violates its general contract!
		 * at java.util.TimSort.mergeLo(TimSort.java:747)
		 * at java.util.TimSort.mergeAt(TimSort.java:483)
		 * at java.util.TimSort.mergeCollapse(TimSort.java:410)
		 * at java.util.TimSort.sort(TimSort.java:214)
		 * at java.util.TimSort.sort(TimSort.java:173)
		 * at java.util.Arrays.sort(Arrays.java:659)
		 * at java.util.Collections.sort(Collections.java:217)
		 * at javax.swing.SortingFocusTraversalPolicy.enumerateAndSortCycle(SortingFocusTraversalPolicy.java:136)
		 * at javax.swing.SortingFocusTraversalPolicy.getFocusTraversalCycle(SortingFocusTraversalPolicy.java:110)
		 * at javax.swing.SortingFocusTraversalPolicy.getFirstComponent(SortingFocusTraversalPolicy.java:435)
		 * at javax.swing.LayoutFocusTraversalPolicy.getFirstComponent(LayoutFocusTraversalPolicy.java:166)
		 * at javax.swing.SortingFocusTraversalPolicy.getDefaultComponent(SortingFocusTraversalPolicy.java:515)
		 * at java.awt.FocusTraversalPolicy.getInitialComponent(FocusTraversalPolicy.java:169)
		 * at java.awt.DefaultKeyboardFocusManager.dispatchEvent(DefaultKeyboardFocusManager.java:380)
		 * at java.awt.Component.dispatchEventImpl(Component.java:4731)
		 * at java.awt.Container.dispatchEventImpl(Container.java:2287)
		 * at java.awt.Window.dispatchEventImpl(Window.java:2719)
		 * at java.awt.Component.dispatchEvent(Component.java:4687)
		 * at java.awt.EventQueue.dispatchEventImpl(EventQueue.java:735)
		 * at java.awt.EventQueue.access$200(EventQueue.java:103)
		 * at java.awt.EventQueue$3.run(EventQueue.java:694)
		 * at java.awt.EventQueue$3.run(EventQueue.java:692)
		 * at java.security.AccessController.doPrivileged(Native Method)
		 * at java.security.ProtectionDomain$1.doIntersectionPrivilege(ProtectionDomain.java:76)
		 * at java.security.ProtectionDomain$1.doIntersectionPrivilege(ProtectionDomain.java:87)
		 * at java.awt.EventQueue$4.run(EventQueue.java:708)
		 * at java.awt.EventQueue$4.run(EventQueue.java:706)
		 * at java.security.AccessController.doPrivileged(Native Method)
		 * at java.security.ProtectionDomain$1.doIntersectionPrivilege(ProtectionDomain.java:76)
		 * at java.awt.EventQueue.dispatchEvent(EventQueue.java:705)
		 * at java.awt.EventDispatchThread.pumpOneEventForFilters(EventDispatchThread.java:242)
		 * at java.awt.EventDispatchThread.pumpEventsForFilter(EventDispatchThread.java:161)
		 * at java.awt.EventDispatchThread.pumpEventsForHierarchy(EventDispatchThread.java:150)
		 * at java.awt.EventDispatchThread.pumpEvents(EventDispatchThread.java:146)
		 * at java.awt.EventDispatchThread.pumpEvents(EventDispatchThread.java:138)
		 * at java.awt.EventDispatchThread.run(EventDispatchThread.java:91)
		 * 
		 */
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	   
		} catch (Exception ignored) {
		}
		try {
			icon = ImageIO.read(Main.class.getResource("Test.gif")); // Resource in fertiger .jar
//			icon = ImageIO.read(new File("src/main/resources/de/pylamo/spellmaker/arpg.png")); // Tests in Eclipse
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		try {
//			UIManager.setLookAndFeel(new LookAndFeel().laf);
//		} catch (UnsupportedLookAndFeelException e) {
//			e.printStackTrace();
//		}
		
//		SynthLookAndFeel laf = new SynthLookAndFeel();
//		try {
//			laf.load(Main.class.getResource("/laf/ancient.xml"));
//			UIManager.setLookAndFeel(laf);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		} catch (UnsupportedLookAndFeelException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		new Menu().setVisible(true);
	}
}