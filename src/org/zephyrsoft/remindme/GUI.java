package org.zephyrsoft.remindme;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import java.util.Timer;
import javax.swing.*;
import javax.swing.border.*;
import de.jutzig.jnotification.*;
import de.jutzig.jnotification.animation.*;

public class GUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public static final double DEFAULT_INTERVAL = 30;
	public static final String DEFAULT_MESSAGE = "drink water";
	
	private double minutes = DEFAULT_INTERVAL;
	private String msg = DEFAULT_MESSAGE;
	
	private boolean box = false;
	
	private static PopupManager manager;
	private static JButton button;
	
	private final Timer timer = new Timer();
	private TimerTask eventTask = null;
	
	private SystemTray tray;
	private TrayIcon trayIcon;
	private JLabel label = null;
	
	public GUI(Double minutes, String msg, boolean box) {
		
		if (minutes != null) {
			this.minutes = minutes.doubleValue();
		}
		if (msg != null) {
			this.msg = msg;
		}
		this.box = box;
		
		createTrayIcon();
		
		initialize();
		
		if (trayIcon == null) {
			setVisible(true);
		}
		
		startTimer();
		
		startCountdown();
	}
	
	private void startTimer() {
		long interval = Math.round(minutes * 60 * 1000);
		
		eventTask = new TimerTask() {
			public void run() {
				System.out.println("firing event \"" + msg + "\"");
				if (!box && tray != null) {
					trayIcon.displayMessage("Reminder", msg, TrayIcon.MessageType.INFO);
				} else {
//					JOptionPane.showMessageDialog(GUI.this, msg, "Reminder", JOptionPane.INFORMATION_MESSAGE);
					manager = new PopupManager(9000, Corner.UPPER_RIGHT, new Point(40, 30));
					JNotificationPopup popup = new JNotificationPopup(createComponent(msg));
					button.addActionListener(new Dispose(popup, manager));
					popup.setAnimator(new FadeIn(popup, 50));
					manager.enqueuePopup(popup);
				}

			}
		};
		timer.scheduleAtFixedRate(eventTask, interval, interval);
		
		System.out.println("scheduling recurring event \"" + msg + "\" with interval of "
			+ minutes + " minutes");
	}
	
	private Component createComponent(final String message)
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JLabel msglabel = new JLabel(message);
		msglabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		msglabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(msglabel, BorderLayout.CENTER);
		button = new JButton("Close");
		JPanel buttonpanel = new JPanel();
		buttonpanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonpanel.add(button);
		panel.add(buttonpanel, BorderLayout.SOUTH);
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.RED, 4), BorderFactory.createEmptyBorder(20, 20, 20, 20)));
		return panel;
		
	}
	
	private void startCountdown() {
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				long togo = Math.abs(Math.round(minutes * 60) - Math.abs((eventTask.scheduledExecutionTime() - System.currentTimeMillis()) / 1000));
				// togo in seconds
				String text = (togo % 60) + " seconds";
				togo = togo / 60;
				// togo in minutes
				text = (togo % 60) + " minutes, " + text;
				togo = togo / 60;
				// togo in hours
				text = (togo % 24) + " hours, " + text;
				togo = togo / 24;
				// togo in days
				text = togo + " days, " + text;
				
				label.setText(text);
			}
		}, 1000, 1000);
		
		System.out.println("starting countdown");
	}
	
	/**
	 * This method initializes this class.
	 */
	private void initialize() {
		setTitle("RemindMe");
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());
		label = new JLabel();
		label.setHorizontalAlignment(SwingConstants.CENTER);
		content.add(label, BorderLayout.CENTER);
		setContentPane(content);
		setSize(new Dimension(350, 100));
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
	
	class Dispose implements ActionListener{

		JNotificationPopup popup;
		PopupManager manager;
		
		public Dispose(JNotificationPopup popup, PopupManager manager) {
			super();
			this.popup = popup;
			this.manager=manager;
		}

		public void actionPerformed(ActionEvent e) {
			
			manager.dequeuePopup(popup);
		}
		
	}
}