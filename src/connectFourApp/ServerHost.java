package connectFourApp;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;

public class ServerHost{
	
	final int port = 6598;
	private ServerSocket host;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private DataInputStream input2;
	public ServerHost() {
		try {
			host = new ServerSocket(port);
		} catch (IOException e)
		{
			System.out.println(e);
		}
		
		Socket clientSocket = null;
		try {
			clientSocket = host.accept();
		}
		catch (IOException e) {
			System.out.println(e);
		}
		try {
			output = new ObjectOutputStream(clientSocket.getOutputStream());
			input = new ObjectInputStream(clientSocket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	/*	
		try {
			output = new ObjectOutputStream(clientSocket.getOutputStream());
			output.writeBoolean(true);
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
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
			host.close();
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

	
	public ServerSocket getHost() {
		return host;
	}

}
