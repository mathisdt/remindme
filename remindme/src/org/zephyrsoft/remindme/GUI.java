package org.zephyrsoft.remindme;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;
import javax.swing.*;

public class GUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	/** Default: trigger event every 30 minutes */
	private double minutes = 30;
	
	private final Timer timer = new Timer();
	
	private SystemTray tray;
	private TrayIcon trayIcon;
	private JLabel label = null;
	
	public GUI(Double minutes) {
		
		if (minutes != null) {
			this.minutes = minutes.doubleValue();
		}
		
		createTrayIcon();
		
		initialize();
		
		if (trayIcon == null) {
			setVisible(true);
		}
		
		startTimer("Drink a glass of water!");
	}
	
	private void startTimer(final String msg) {
		long interval = Math.round(minutes * 60 * 1000);
		
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				System.out.println("firing event \"" + msg + "\"");
				if (tray != null) {
					trayIcon.displayMessage("Reminder", msg, TrayIcon.MessageType.INFO);
				} else {
					JOptionPane.showMessageDialog(GUI.this, msg, "Reminder", JOptionPane.INFORMATION_MESSAGE);
				}

			}
		}, interval, interval);
		
		System.out.println("scheduling event \"" + msg + "\" in "
			+ minutes + " minutes");
	}
	
	/**
	 * This method initializes this class.
	 */
	private void initialize() {
		setTitle("RemindMe");
		JPanel content = new JPanel();
		label = new JLabel();
		content.add(label);
		setContentPane(content);
		setSize(new Dimension(557, 478));
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/clock.png")));
		
	}
	
	private void createTrayIcon() {
		if (SystemTray.isSupported()) {
			
			tray = SystemTray.getSystemTray();
			Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/images/clock.png"));
			
			MouseListener mouseListener = new MouseAdapter() {
				
				public void mouseClicked(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						setVisible(!isVisible());
					}
				}
			};
			
			ActionListener exitListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("Exiting...");
					System.exit(0);
				}
			};
			
			PopupMenu popup = new PopupMenu();
			MenuItem defaultItem = new MenuItem("Exit");
			defaultItem.addActionListener(exitListener);
			popup.add(defaultItem);
			
			trayIcon = new TrayIcon(image, "RemindMe", popup);
			
			trayIcon.setImageAutoSize(true);
			trayIcon.addMouseListener(mouseListener);
			
			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				System.err.println("TrayIcon could not be added.");
			}
		} else {
			System.err.println("TrayIcon could not be added (system tray is not supported).");
		}
	}
	
}