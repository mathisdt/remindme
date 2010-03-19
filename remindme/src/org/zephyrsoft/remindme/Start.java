package org.zephyrsoft.remindme;

import org.apache.commons.cli.*;


public class Start {
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		
		// define the options
		Options options = new Options();
		Option interval = OptionBuilder
			.withArgName("minutes")
			.hasArg()
			.withDescription("interval between events - default: " + GUI.DEFAULT_INTERVAL)
			.withLongOpt("interval")
			.create("i");
		options.addOption(interval);
		Option message = OptionBuilder
			.withArgName("text")
			.hasArg()
			.withDescription("event text - default: \"" + GUI.DEFAULT_MESSAGE + "\"")
			.withLongOpt("text")
			.create("t");
		options.addOption(message);
		Option boxOpt = new Option("b", "box", false, "use a message box instead of a tray notification");
		options.addOption(boxOpt);
		Option help = new Option("h", "help", false, "print this message");
		options.addOption(help);
		
		Double min = null;
		String msg = null;
		boolean box = false;
		
		CommandLineParser parser = new PosixParser();
		try {
			// parse the command line arguments
			CommandLine line = parser.parse(options, args);
			
			if (line.hasOption("help")) {
				// automatically generate the help statement
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("remindme", options, true);
				System.exit(0);
			}
			if (line.hasOption("interval")) {
				try {
					String arg = line.getOptionValue("interval").replaceAll(",", ".");
					min = Double.valueOf(Double.parseDouble(arg));
				} catch (NumberFormatException nfe) {
					System.err.println("interval could not be parsed, ensure it is a number - using default: " + GUI.DEFAULT_INTERVAL);
				}
			}
			if (line.hasOption("text")) {
				msg = line.getOptionValue("text");
			}
			if (line.hasOption("box")) {
				box = true;
			}
		} catch (ParseException exp) {
			System.err.println("argument parsing failed - reason: " + exp.getMessage());
		}

		new GUI(min, msg, box);
		
	}
	
}
