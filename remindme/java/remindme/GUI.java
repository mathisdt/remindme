package remindme;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.tiling.scheduling.examples.*;

public class GUI extends JFrame {
	
	private TrayIcon trayIcon;
	private JPanel jPanel = null;
	private JButton jButton = null;
	private JTextField jTextField = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JTextField jTextField1 = null;
	public GUI() {
		createTrayIcon();
		
		initialize();
		
		if (trayIcon==null) {
			setVisible(true);
		}
	}
		
	
	
	
	
	/**
	 * This method initializes this class.
	 * 
	 */
	private void initialize() {
		this.setTitle("RemindMe");
		this.setResizable(false);
		this.setContentPane(getJPanel());
		this.setSize(new Dimension(557, 478));
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/clock.png")));
		
	}




	private void createTrayIcon() {
		if (SystemTray.isSupported()) {

		    SystemTray tray = SystemTray.getSystemTray();
		    Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/resources/clock.png"));

		    MouseListener mouseListener = new MouseAdapter() {
		                
		        public void mouseClicked(MouseEvent e) {
		            setVisible(!isVisible());
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





	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints5.gridy = 0;
			gridBagConstraints5.weightx = 1.0;
			gridBagConstraints5.gridx = 3;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 2;
			gridBagConstraints4.gridy = 0;
			jLabel1 = new JLabel();
			jLabel1.setText("Minuten: ");
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 0;
			jLabel = new JLabel();
			jLabel.setText("In ");
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.gridx = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 4;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.gridy = 0;
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.add(getJButton(), gridBagConstraints);
			jPanel.add(getJTextField(), gridBagConstraints2);
			jPanel.add(jLabel, gridBagConstraints3);
			jPanel.add(jLabel1, gridBagConstraints4);
			jPanel.add(getJTextField1(), gridBagConstraints5);
		}
		return jPanel;
	}





	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("Hinzufügen");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					(new EggTimer(Integer.parseInt(getJTextField().getText()), getJTextField1().getText(), GUI.this)).start();
					System.out.println("scheduling event \"" + getJTextField1().getText() + "\" in " + getJTextField().getText() + " minutes");
				}
			});
		}
		return jButton;
	}





	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setPreferredSize(new Dimension(40, 19));
		}
		return jTextField;
	}





	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setPreferredSize(new Dimension(200, 19));
		}
		return jTextField1;
	}
	
}  //  @jve:decl-index=0:visual-constraint="10,10"