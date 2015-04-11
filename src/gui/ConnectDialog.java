package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import command.CommandProxy;

public class ConnectDialog extends JDialog {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5630602954303110028L;
	private javax.swing.JComboBox<String> baudchosser;
	private javax.swing.JComboBox<String> channelchosser;
	private JLabel label;
	private JButton ok;
	
	/**
	 * Creates a new ConnectDialog with a designated parent frame.
	 * 
	 * @param parent
	 */
	public ConnectDialog(JFrame parent){
		super(parent,"Reconnect to Microcontroller");
		
		this.setLocationRelativeTo(null);
		JPanel msgpanel = new JPanel();
		JPanel msgpanelnorth = new JPanel();
		JPanel msgpanelsouth = new JPanel();
		JLabel blabel = new JLabel("The new baud-rate: ");
		baudchosser = new JComboBox<String>();
		baudchosser.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "1M", "800K", "500K", "250K", "125K", "100K", "95K", "83K", "50K", "47K", "33K", "20K", "10K", "5K" }));
		JLabel clabel = new JLabel("The new channel: ");
		channelchosser = new JComboBox<String>();
		channelchosser.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] {"1","2","3","4","5","6","7","8"}));
		label = new JLabel(" INFORMATION: Default channel is 1 and default baud-rate is 1M ");
		label.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

		msgpanel.setLayout(new BorderLayout());
		msgpanel.add("North",msgpanelnorth);
		msgpanel.add("South",msgpanelsouth);
		
		msgpanelnorth.add(blabel);
		msgpanelnorth.add(baudchosser);
		msgpanelsouth.add(clabel);
		msgpanelsouth.add(channelchosser);
		
		JPanel buttonpanel = new JPanel();
		JButton close = new JButton("Close");
		close.addActionListener(new ConnectDialogClose());
		ok = new JButton("Connect");
		ok.addActionListener(new ConnectDialogOk());
		buttonpanel.add(close, BorderLayout.WEST);
		buttonpanel.add(ok, BorderLayout.EAST);
		
		this.getContentPane().add(label, BorderLayout.PAGE_START);
		this.getContentPane().add(msgpanel);
		this.getContentPane().add(buttonpanel, BorderLayout.PAGE_END);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		this.setVisible(false);
	}
	
	/**
	 * Closes this dialog frame by disposing.
	 */
	private void dialogdispose(){
		this.dispose();
	}
	
	/**
	 * Returns the selected USBBUS channel.
	 * 
	 * @return USB BUS Channel
	 */
	private String getChannel(){
		return channelchosser.getItemAt(channelchosser.getSelectedIndex());
	}
	
	/**
	 * Returns the selected baud rate.
	 * @return baud-rate
	 */
	private String getBaudRate(){
		return baudchosser.getItemAt(baudchosser.getSelectedIndex());
	}
	
	/**
	 * Makes it unable to reconnect with the controller. This
	 * has to be done if the motors are still running.
	 */
	public void disableReconnect(){
		label.setText("You have to stop the motors before reconnecting!");
		ok.setEnabled(false);
	}

	
	/**
	 * Internal class for handling the action event of the close button.
	 * This button closes the ConnectDialog.
	 * 
	 * @author Iorgreths
	 *
	 */
	private class ConnectDialogClose implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			dialogdispose();
			
		}
		
	}
	
	/**
	 * Internal class for handling the action event of the ok button.
	 * This button sets the channel and baud-rate of the connection.
	 * 
	 * @author Iorgreths
	 *
	 */
	private class ConnectDialogOk implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String channel = getChannel();
			String baud = getBaudRate();
			CommandProxy.getInstance().changeChannelAndBaudRate(baud, channel);
			dialogdispose();
			
		}
		
	}
}
