package connectFourApp;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ConnectFourGUI extends JPanel {
	
	
	private JButton quit;
	private JLabel[][] grid;
	private JPanel main;
	private JLabel player1Name;
	private JLabel player2Name;
	private JLabel score1;
	private JLabel score2;
	private JLabel timerLabel;
	private JButton playAgain;
	private JLabel winner;
	private JButton multiplayer;
	

	private Player player1;
	private Player player2;
	
	private final int rows = 6; //Default size of board, should not be changed.
	private final int columns = 7;

	public ConnectFourGUI(Player player1, Player player2){
		
		this.setLayout(new GridBagLayout());
		this.player1 = player1;
		this.player2 = player2;
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(30,20,0,0);
		this.add(setMain(mainGame()), gbc);
		gbc.gridx = 1;
		this.add(sideBar(), gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(10,0,0,50);
		this.add(scoreBar(), gbc);
	}
	
	private JPanel mainGame() {
		
		main = new JPanel();
		
		main.setLayout(new GridLayout(rows,columns));
		main.setOpaque(false);
		try {
			BufferedImage mainGrid = ImageIO.read(new File("./connectFourGridSingle.png"));
			grid = new JLabel[rows][columns];
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					JLabel piece = new JLabel(new ImageIcon(mainGrid));
					piece.setOpaque(true);
					grid[i][j] = piece;
					main.add(piece);
				}
			}
			main.setSize(mainGrid.getWidth() * columns, mainGrid.getHeight() * rows);
		} catch (IOException e) {
			System.out.println(e);
		}

		return main;
	}

	private JPanel sideBar() {
		
		JPanel sideBar = new JPanel();

		sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));

		timerLabel = new JLabel();
		timerLabel.setAlignmentX(CENTER_ALIGNMENT);
		timerLabel.setFont(new Font(Font.SANS_SERIF, 1, 40));
		sideBar.add(timerLabel);

		

		sideBar.add(Box.createRigidArea(new Dimension(0, 100)));

		player1Name = new JLabel(player1.getName());
		player1Name.setAlignmentX(CENTER_ALIGNMENT);
		player1Name.setFont(new Font(Font.SANS_SERIF, 1, 20));
		sideBar.add(player1Name);

		score1 = new JLabel(Integer.toString(player1.getScore()));
		score1.setAlignmentX(CENTER_ALIGNMENT);
		score1.setFont(new Font(Font.SANS_SERIF, 1, 40));
		sideBar.add(score1);

		sideBar.add(Box.createRigidArea(new Dimension(0, 100)));

		player2Name = new JLabel(player2.getName());
		player2Name.setAlignmentX(CENTER_ALIGNMENT);
		player2Name.setFont(new Font(Font.SANS_SERIF, 0, 20));
		sideBar.add(player2Name);

		score2 = new JLabel(Integer.toString(player2.getScore()));
		score2.setAlignmentX(CENTER_ALIGNMENT);
		score2.setFont(new Font(Font.SANS_SERIF, 1, 40));
		sideBar.add(score2);
			
		
		
		sideBar.setPreferredSize(new Dimension(200,main.getHeight()));
		sideBar.setBackground(Color.LIGHT_GRAY);
		return sideBar;
	}
	
	private JPanel scoreBar() {

		JPanel scoreBar = new JPanel();
		
		scoreBar.setLayout(new BoxLayout(scoreBar, BoxLayout.X_AXIS));
		
		scoreBar.add(Box.createRigidArea(new Dimension (20,0)));
		
		this.winner = new JLabel();
		this.winner.setFont(new Font(Font.SANS_SERIF, 1,50));
		scoreBar.add(this.winner);
		
		scoreBar.add(Box.createHorizontalGlue());
		
		this.playAgain = new JButton("Play Again");
		this.playAgain.setVisible(false);
		scoreBar.add(this.playAgain);
			
		scoreBar.add(Box.createRigidArea(new Dimension(10,0)));
		
		multiplayer = new JButton("Multiplayer");
		scoreBar.add(multiplayer);
		
		scoreBar.add(Box.createRigidArea(new Dimension(10,0)));
		
		quit = new JButton("Quit");
		scoreBar.add(quit);


		scoreBar.setPreferredSize(new Dimension(900, 100));
		return scoreBar;
	}
	
	public void update() {
		this.score1.setText(Integer.toString(player1.getScore()));
		this.score2.setText(Integer.toString(player2.getScore()));
	}
	
	public JButton getQuit() {
		return quit;
	}


	public JPanel getMain() {
		return main;
	}


	public JPanel setMain(JPanel main) {
		this.main = main;
		return main;
	}

	public int getRows() {
		return rows;
	}
	

	public int getColumns() {
		return columns;
	}

	public JLabel getTimerLabel() {
		return timerLabel;
	}

	public JButton getPlayAgain() {
		return playAgain;
	}
	public JLabel getPlayer1() {
		return player1Name;
	}

	public JLabel getPlayer2() {
		return player2Name;
	}

	public JLabel getWinner() {
		return winner;
	}
	
	public JButton getMultiplayer() {
		return multiplayer;
	}
}

