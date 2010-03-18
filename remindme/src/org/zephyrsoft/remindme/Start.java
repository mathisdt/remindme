package org.zephyrsoft.remindme;


public class Start {
	
	public static void main(String[] args) {
		Double minutes = null;
		if (args.length>0) {
			try {
				String arg = args[0].replaceAll(",", ".");
				minutes = Double.valueOf(Double.parseDouble(arg));
			} catch (NumberFormatException nfe) {
				// number could not be parsed
			}
		}
		new GUI(minutes);
		
	}
	
}
