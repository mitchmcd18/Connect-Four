package connectFourApp;

import java.awt.Dimension;
import java.awt.GridBagConstraints;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MultiplayerGUI extends JPanel {

	private JButton host;
	private JButton join;
	private JButton cancel;
	private JButton connect;
	private JLabel label;

	private JTextField ipAddress;
	
	

	public MultiplayerGUI() {
		GridBagConstraints gbc = new GridBagConstraints();
		//this.setBackground(Color.BLUE);
		gbc.gridx = 0;
		this.add(leftSideBar(), gbc);
		gbc.gridx= 1;
		this.add(rightOptions(), gbc);
	}
	
	private JPanel leftSideBar() {
		JPanel leftSideBar = new JPanel();
		leftSideBar.setLayout(new BoxLayout(leftSideBar, BoxLayout.Y_AXIS));
		
		host = new JButton("Host");
		host.setAlignmentX(CENTER_ALIGNMENT);
		leftSideBar.add(host);
		
		leftSideBar.add(Box.createRigidArea(new Dimension (0,10)));
		
		join = new JButton("Join");
		join.setAlignmentX(CENTER_ALIGNMENT);
		leftSideBar.add(join);
		
		leftSideBar.add(Box.createVerticalGlue());
		
		cancel = new JButton("Cancel");
		cancel.setAlignmentX(CENTER_ALIGNMENT);
		leftSideBar.add(cancel);
		
		leftSideBar.setPreferredSize(new Dimension(80, 300 ));
	//	leftSideBar.setBackground(Color.RED);
		return leftSideBar;
	}

	private JPanel rightOptions()
	{
		JPanel rightOptions = new JPanel();
		rightOptions.setLayout(new BoxLayout(rightOptions, BoxLayout.Y_AXIS));
		
		ipAddress = new JTextField();
		ipAddress.setPreferredSize(new Dimension(300,30));
		ipAddress.setMaximumSize(ipAddress.getPreferredSize());
		ipAddress.setVisible(false);
		
		rightOptions.add(ipAddress);
		
		connect = new JButton("Connect");
		connect.setVisible(false);
		rightOptions.add(connect);
		
		label = new JLabel();
		rightOptions.add(label);
		rightOptions.setPreferredSize(new Dimension(300,300));
		//rightOptions.setBackground(Color.YELLOW);
		
		return rightOptions;
	}
	public JButton getQuit() {
		return cancel;
	}

	public JButton getJoin() {
		return join;
	}

	public JButton getHost() {
		return host;
	}

	public JButton getConnect() {
		return connect;
	}
	public JTextField getIpAddress() {
		return ipAddress;
	}

	public JLabel getLabel() {
		return label;
	}

}
