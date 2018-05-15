package connectFourApp;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;

public class MultiplayerWindow extends JFrame {

	private ConnectFourApp mainApp;
	private MultiplayerGUI view;
	private Thread server;
	
	public MultiplayerWindow(String name, ConnectFourApp mainApp)
	{
		super(name);
		this.mainApp = mainApp;
		
		setSize(400,350);
		this.setResizable(false);
		this.view = new MultiplayerGUI();
		this.getContentPane().add(this.view);
		
		this.view.getQuit().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				handleCancelButton();
				
			}
			
		});
		
		this.view.getHost().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				handleHostButton();
				
			}
			
		});
		
		this.view.getJoin().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				handleJoinButton();
				
			}
			
		});
		
		this.view.getConnect().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String ipAddress = view.getIpAddress().getText();
				handleConnectButton(ipAddress);
				
			}
			
		});
	}
	
	public void handleConnectButton(String ipAddress){
		this.mainApp.createClient();
	}
	public void handleHostButton() {
		this.mainApp.createServer();
		/*
		server = new Thread(host);
		server.start();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (host.getHost().isClosed() == true) {
			this.view.getLabel().setText("Connection Established on port " + host.port);
		} */
		//host.terminate();
	}
	
	public void handleJoinButton() {
		this.view.getConnect().setVisible(true);
		this.view.getIpAddress().setVisible(true);
	}
	
	public void handleCancelButton() {
		
		/*if (server != null) {
			server.interrupt();
		}*/
		
		this.dispose();
	}
}
