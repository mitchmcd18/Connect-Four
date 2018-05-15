package connectFourApp;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.concurrent.Callable;

public class ClientJoin {

	final int port = 6598;
	private Socket client = null;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	
	public ClientJoin(String ipAddress)
	{

		boolean connecting = true;
		int attempts = 0;
		while (connecting == true && attempts < 4) {
			try {
				System.out.println("Connecting...");
				
				client = new Socket(ipAddress, port);
				connecting = false;
			} catch (ConnectException e) {
				System.out.println("Server could not be reached");
				attempts += 1;
				try {
					Thread.sleep(3000);
				} catch (InterruptedException x) {
					x.printStackTrace();
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (attempts == 4)
		{
			System.out.println("Failed to connect after " + attempts + " attempts");
			return;
		} else
		{
			System.out.println("Connected");
		}
		
		try {
			output = new ObjectOutputStream(client.getOutputStream());
			input = new ObjectInputStream(client.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//terminate();
	}
	public void sendPosition(Point e) {
		try {
			output.writeObject(e);
			output.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void terminate() {
		try {
			client.close();
			System.out.println("Port Closed");
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public ObjectOutputStream getOutput() {
		return output;
	}

	public ObjectInputStream getInput() {
		return input;
	}

	public Socket getClient() {
		return client;
	}
	

}
