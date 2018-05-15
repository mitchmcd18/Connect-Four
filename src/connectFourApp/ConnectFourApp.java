package connectFourApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ConnectFourApp extends JFrame {

	private ConnectFourGUI view;
	private Player player1;
	private Player player2;
	private Timer timer;
	private MouseListener ml;
	private int timerCount = -1;
	
	//Multiplayer variables. Only instantiated if user clicks on multiplayer button.
	private ClientJoin join = null;
	private ServerHost host = null;
	private boolean multiplayerActive = false;
	private boolean wait = false;
	
	public ConnectFourApp(String name) {
		super(name);
		
		this.player1 = new Yellow("Yellow");
		this.player2 = new Red("Red");
		this.view = new ConnectFourGUI(player1, player2);
		
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.getContentPane().add(this.view);
		this.setSize(1000, 700);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.view.getQuit().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				System.exit(0);
			}
		});
		
		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				timerCount++;
				if (timerCount < 60) {
					view.getTimerLabel().setText(Integer.toString(timerCount));
				} else {
					view.getTimerLabel().setText(timerCount / 60 + ":" + String.format("%02d", (timerCount % 60))) ;
				}

			}
		});
		timer.setInitialDelay(0);
		timer.start();
		

		this.view.getMain().addMouseListener(ml = new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				if (wait == true) {
					return;
				} else {
					JPanel grid = (JPanel)e.getSource();
					handleGridClick(grid, e.getPoint());
					if (multiplayerActive == true && host != null)
					{
						host.sendPosition(e.getPoint());
						wait = true;

					}
					else if (multiplayerActive == true && join != null)
					{
						join.sendPosition(e.getPoint());		
						wait = true;
					}
					return;
				}
			}
			
		});
		this.view.getPlayAgain().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				handlePlayAgainButton();
			}
		});
		
		this.view.getMultiplayer().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				handleMultiplayerButton();
				
			}

		});
	}
	private void handleGridClick(JPanel grid, Point position) {
		
		JLabel square = (JLabel) grid.getComponentAt(position.x,85 * view.getRows() - 1); //85 is the size of each individual square in pixels
		
		for (int i = 85 * view.getRows() - 1; i > 0; i -= 85)
		{
			if (square.getBackground() != Color.YELLOW && square.getBackground() != Color.RED) {
				
				if (player1.isActive() == true)
				{
					square.setBackground(Color.YELLOW);
					checkWin(square, grid);
					player1.setActive(false);
					player2.setActive(true);
				}
				else {
					square.setBackground(Color.RED);
					checkWin(square, grid);
					player2.setActive(false);
					player1.setActive(true);
				}
				
				break;
			}
			else {
				square = (JLabel) grid.getComponentAt(position.x, i - 85);
			}		
		}
	}
	private void handleMultiplayerButton() {
		timer.stop();
		multiplayerActive = true;
		MultiplayerWindow multiplayer = new MultiplayerWindow("Multiplayer", this);
		multiplayer.setVisible(true);
	}
	
	/**
	 * <br><i>private void checkWin(JLabel position, JPanel grid) </b></i><br>
	 * 
	 * Checks all possible directions to see if anyone has won the game. If not the next player will be able to make a move.
	 * @param position - Position the player played their move
	 * @param grid - JPanel grid containing all moves done this game
	 */
	//
	private void checkWin(JLabel position, JPanel grid) {
		int yellowInLine = 0;
		int redInLine = 0;
		JLabel current = (JLabel) grid.getComponentAt(position.getLocation().x, 85 * view.getRows() - 1);
		
		//Checks columns for any winners
		for (int i = 85 * view.getRows() - 1; i > 0; i -= 85)
		{
			if (current.getBackground() == Color.YELLOW) {
				yellowInLine += 1;
				redInLine = 0;
			}
			else if (current.getBackground() == Color.RED) {
				redInLine += 1;
				yellowInLine = 0;
			}
			else {
				yellowInLine = 0;
				redInLine = 0;
			}
			
			if (redInLine == 4) {
				winner(Color.RED);
				break;
			}
			else if (yellowInLine == 4)
			{
				winner(Color.YELLOW);
				break;
			}
			current = (JLabel) grid.getComponentAt(position.getLocation().x, i - 85);
		}
		
		//Checks rows for any winners
		current = (JLabel) grid.getComponentAt( 85 * view.getColumns() - 1,position.getLocation().y);
		yellowInLine = 0;
		redInLine = 0;
		for (int i = 85 * view.getColumns() - 1; i > 0; i -= 85)
		{
			if (current.getBackground() == Color.YELLOW) {
				yellowInLine += 1;
				redInLine = 0;
			}
			else if (current.getBackground() == Color.RED) {
				redInLine += 1;
				yellowInLine = 0;
			}
			else {
				yellowInLine = 0;
				redInLine = 0;
			}
			
			if (redInLine == 4) {
				winner(Color.RED);
				break;
			}
			else if (yellowInLine == 4)
			{
				winner(Color.YELLOW);
				break;
			}
			current = (JLabel) grid.getComponentAt(i - 85, position.getLocation().y);
		}
		
		//Checks diagonal for any winners
		yellowInLine = 0;
		redInLine = 0;
		current = (JLabel) grid.getComponentAt(position.getLocation().x,position.getLocation().y);
		Color colourToCheck;
		if (player1.isActive() == false) 
		{
			colourToCheck = Color.RED;
		}
		else
		{
			colourToCheck = Color.YELLOW;
		}
		
		//Checks top left to bottom right of current position
		while (current != null && current.getBackground() == colourToCheck)
		{
			if (current.getBackground() == colourToCheck) {
				if(colourToCheck == Color.YELLOW) {
					yellowInLine += 1;
				}
				else if (colourToCheck == Color.RED) {
					redInLine += 1;
				}
			}
			current = (JLabel) grid.getComponentAt(current.getLocation().x + 85,current.getLocation().y + 85); //Sets it to the next diagonal space
		}
		current = (JLabel) grid.getComponentAt(position.getLocation().x - 85,position.getLocation().y - 85);
		while (current != null && current.getBackground() == colourToCheck)
		{
			if (current.getBackground() == colourToCheck) {
				if(colourToCheck == Color.YELLOW) {
					yellowInLine += 1;
				}
				else if (colourToCheck == Color.RED) {
					redInLine += 1;
				}
			}
			current = (JLabel) grid.getComponentAt(current.getLocation().x - 85,current.getLocation().y - 85); //Checks the diagonal space in the opposite direction
		}
		
		if (redInLine == 4) {
			winner(Color.RED);
		}
		else if (yellowInLine == 4)
		{
			winner(Color.YELLOW);
		}
		//Checks top right to bottom left of current position
		current = (JLabel) grid.getComponentAt(position.getLocation().x,position.getLocation().y);
		yellowInLine = 0;
		redInLine = 0;
		while (current != null && current.getBackground() == colourToCheck)
		{
			if (current.getBackground() == colourToCheck) {
				if(colourToCheck == Color.YELLOW) {
					yellowInLine += 1;
				}
				else if (colourToCheck == Color.RED) {
					redInLine += 1;
				}
			}
			current = (JLabel) grid.getComponentAt(current.getLocation().x + 85,current.getLocation().y - 85);
		}
		current = (JLabel) grid.getComponentAt(position.getLocation().x - 85,position.getLocation().y + 85);
		while (current != null && current.getBackground() == colourToCheck)
		{
			if (current.getBackground() == colourToCheck) {
				if(colourToCheck == Color.YELLOW) {
					yellowInLine += 1;
				}
				else if (colourToCheck == Color.RED) {
					redInLine += 1;
				}
			}
			current = (JLabel) grid.getComponentAt(current.getLocation().x - 85,current.getLocation().y + 85);
		}
		
		if (redInLine == 4) {
			winner(Color.RED);
		}
		else if (yellowInLine == 4)
		{
			winner(Color.YELLOW);
		}
		
	}
	
	//Runs when someone has won the game
	private void winner(Color winner) {
		if (winner == Color.YELLOW) {
			this.player1.addScore();
			this.view.getWinner().setText("YELLOW WINS!");
			this.view.getWinner().setForeground(Color.YELLOW);
			this.view.update();
		}
		if (winner == Color.RED) {
			this.player2.addScore();
			this.view.getWinner().setText("RED WINS!");
			this.view.getWinner().setForeground(Color.RED);
			this.view.update();
		}
		this.view.getMain().removeMouseListener(ml);
		//Alternative way to prevent clicking after winner
		/*Component[] components = this.view.getMain().getComponents();
		for (Component component : components)
		{
			component.setEnabled(false);
		} */
		timer.stop();
		this.view.getPlayAgain().setVisible(true);
	}
	
	
	//Runs when someone clicks the button to play again
	private void handlePlayAgainButton() {
		Component[] components = this.view.getMain().getComponents();
		for (Component component : components)
		{
			component.setBackground(null);
		} 
		this.view.getMain().addMouseListener(ml);
		this.view.getWinner().setVisible(false);
		this.view.getPlayAgain().setVisible(false);
		this.timerCount = -1;
		this.timer.start();
	}
	
	public static void main(String[] args)
	{
		ConnectFourApp application = new ConnectFourApp("Connect Four");
		application.setVisible(true);
	}

	public void createServer() {
		host = new ServerHost();
		Thread test = new Thread(new Runnable() {

			@Override
			public void run() {
				Point event = null;
				while (true)
				{
					try { 
						event = (Point) host.getInput().readObject();
						handleGridClick(view.getMain(), event);
						wait = false;
						
					} catch (IOException | ClassNotFoundException e1) {
						e1.printStackTrace();
						break;
					}
				}
			}
			
		});
		test.start();
	//	player1.setActive(true);
	//	player2.setActive(false);
	}
	
	public void createClient() {
		join = new ClientJoin("localhost");
		Thread test = new Thread(new Runnable() {

			@Override
			public void run() {
				Point event = null;
				while (true) {
					try { 
							event = (Point) join.getInput().readObject();
							handleGridClick(view.getMain(), event);
							wait = false;
					} catch (IOException | ClassNotFoundException e1) {
						e1.printStackTrace();
						break;
					}
				}
			}
			
		});
		test.start();

	}
	public boolean isMultiplayerActive() {
		return multiplayerActive;
	}

	public void setMultiplayerActive(boolean multiplayerActive) {
		this.multiplayerActive = multiplayerActive;
	}
}
