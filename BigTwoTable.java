import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;

import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.Image;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.text.DefaultCaret;

import java.util.Arrays;

/**
 * This public class implements the GUI for the BigTwo game using the CardGameTable
 * @author Tanmay
 *
 */

public class BigTwoTable implements CardGameTable 
{

	private BigTwoClient game;
	private boolean[] selected;
	private int activePlayer;
	private JFrame frame;
	private JPanel bigTwoPanel;
	private JButton playButton;
	private JButton passButton;
	private JTextArea msgArea;
	private Image[][] cardImages;
	private Image cardBackside;
	private Image [] avatars;
	
	private JTextArea chatArea; 
	private boolean[] presence;
	private JTextField message_box;
	
	/**
	 * This constructor creates a new basic layout for the game
	 * @param game
	 */
	
	public BigTwoTable(BigTwoClient game){
		this.game = game;
		this.setup();
		frame.setSize(1250, 925);
		frame.setVisible(true);
	}
	/*
	 * {@inheritDoc}
	 */
	
	@Override
	public void setActivePlayer(int activePlayer)
	{
		// TODO Auto-generated method stub
		this.activePlayer = activePlayer;	
	}

	/* 
	 * {@inheritDoc}
	 */
	
	@Override
	public int[] getSelected() 
	{
		// TODO Auto-generated method stub
		int counter = 0;
		int [] inputs = new int[13];
		for(int i = 0; i < 13;i++)
		{
			if(selected[i] == true)
			{
				inputs[counter] = i;
				counter++;
			}	
		}
		if(counter>0)
			return Arrays.copyOfRange(inputs,0, counter);
		
		return null;
	}

	/* 
	 * {@inheritDoc}
	 */
	
	@Override
	public void resetSelected() 
	{
		// TODO Auto-generated method stub
		
		for(int i = 0; i < 13;i++)
		{
			selected[i] = false;
		}
		
	}

	/* 
	 * {@inheritDoc}
	 */
	
	@Override
	public void repaint() 
	{
		// TODO Auto-generated method stub
		this.resetSelected();
		bigTwoPanel.repaint();
		bigTwoPanel.revalidate();
	}

	/* 
	 * {@inheritDoc}
	 */
	
	@Override
	public void printMsg(String msg) 
	{
		
		msgArea.append(msg + "\n");
		
	}

	/*
	 *  {@inheritDoc}
	 */
	
	@Override
	public void clearMsgArea()
	{
		// TODO Auto-generated method stub
		msgArea.setText("");
	}
	
	public void printEndGameMsg()
	{
		String endOfGame="";
		
		for(int i=0; i<game.getNumOfPlayers(); ++i)
		{
			endOfGame+=game.getPlayerList().get(i).getName()+": ";
			
			if(game.getPlayerList().get(i).getNumOfCards()!=0)
			{
				for(int j=0; j<game.getPlayerList().get(i).getNumOfCards(); ++j)
				{
					endOfGame+=" ["+game.getPlayerList().get(i).getCardsInHand().getCard(j).toString()+"]";
				}
				endOfGame+="\n";
			}
			
			else
			{
				endOfGame+=" wins\n";
			}
		}
		JOptionPane.showMessageDialog(null, "GameOver\n"+endOfGame);
	}
	
	public void printChatMsg(String msg) 
	{
		chatArea.append(msg+"\n");
	}
	
	public void clearChatMsgArea() 
	{
		this.chatArea.setText("");
	}
	
	public void setExistence(int playerID)
	{
		presence[playerID] = true;
	}
	
	public void setNotExistence(int playerID)
	{
		presence[playerID] = false;
	}


	/* 
	 * {@inheritDoc}
	 */
	
	@Override
	public void reset() 
	{
		// TODO Auto-generated method stub
		this.resetSelected();
		this.clearMsgArea();
		this.enable();
	}

	/* 
	 * {@inheritDoc}
	 */
	
	@Override
	public void enable() 
	{
		// TODO Auto-generated method stub
		playButton.setEnabled(true);
		passButton.setEnabled(true);
		bigTwoPanel.setEnabled(true);
	}

	/* 
	 * * {@inheritDoc}
	 */
	
	@Override
	public void disable() {
		// TODO Auto-generated method stub
		playButton.setEnabled(false);
		passButton.setEnabled(false);
		bigTwoPanel.setEnabled(false);
	}
	
	public void quit() 
	{
		System.exit(0);
	}
	
	/**
	 * This function sets up the basic GUI for the game like JPanel, reading images, text area, buttons.
	 */
	
	public void setup()
	{
		setActivePlayer(game.getPlayerID()); 
		bigTwoPanel = new BigTwoPanel();
		
		selected = new boolean[13];
		resetSelected();
		
		cardImages = new Image [4][13];
		cardBackside = new ImageIcon("src/cards/backside.jpeg").getImage();
		avatars = new Image[4];
		
		char[] suit = {'d','c','h','s'};
		char[] rank = {'a', '2', '3', '4', '5', '6', '7', '8', '9', 't', 'j', 'q', 'k'};
		
		for (int i = 0; i < 4; i++)
		{
			avatars[i] = new ImageIcon("src/avatars/"+i+".png").getImage();
			for(int j = 0 ; j < 13;j++)
			{
		        cardImages[i][j] = new ImageIcon("src/cards/" + rank[j] + suit[i] + ".gif").getImage();
			}
	    }
		
		presence = new boolean[4];
		for (int i = 0; i < 4; i++)
			presence[i] = false;
		
		frame = new JFrame();
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Big Two");
		frame.getContentPane().setBackground(Color.RED);
		
		final JSplitPane leftPane = new JSplitPane();
		leftPane.setDividerLocation(820);
		leftPane.setDividerSize(3);
		frame.add(leftPane, BorderLayout.CENTER);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu Game = new JMenu("Game");
		JMenuItem Connect = new JMenuItem("Connect");
		Connect.addActionListener(new ConnectMenuItemListener());
		Game.add(Connect);
		
		JMenuItem Quit = new JMenuItem("Quit");
		Quit.addActionListener(new QuitMenuItemListener());
		Game.add(Quit);
		
		JMenu Message = new JMenu("Message");
		JMenuItem ClearInformation = new JMenuItem("Clear Message Board");
		ClearInformation.addActionListener(new ClearMenuItemListener());
		Message.add(ClearInformation);
		
		JMenuItem ClearChatBoard = new JMenuItem("Clear Chat Board");
		ClearChatBoard.addActionListener(new ClearChatMenuItemListener());
		Message.add(ClearChatBoard);
	
		menuBar.add(Game);
		menuBar.add(Message);
		frame.setJMenuBar(menuBar);
		
		msgArea = new JTextArea(100, 30);
		DefaultCaret caret = (DefaultCaret)msgArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		msgArea.append("Message Board:\n");
		msgArea.setEditable(false);
		
		JScrollPane scroll = new JScrollPane(msgArea);   
		msgArea.setLineWrap(true); 
		scroll.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);    
		
		JPanel message = new JPanel();
		message.setLayout(new BoxLayout(message, BoxLayout.Y_AXIS));
		chatArea = new JTextArea(100, 30);
		DefaultCaret caret2 = (DefaultCaret)chatArea.getCaret();
		caret2.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		chatArea.append("Chat area:\n");
		chatArea.setEditable(false);
		
		JScrollPane scroll2 = new JScrollPane(chatArea);   
		chatArea.setLineWrap(true); 
		scroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		JLabel label = new JLabel("Message: ");
		message_box = new MyTextField(30);
		message_box.setMinimumSize(new Dimension(30, 10));
		JPanel messageInput = new JPanel();
		messageInput.setLayout(new FlowLayout(FlowLayout.LEFT));
		messageInput.add(label);
		messageInput.add(message_box);
		
		message.add(scroll);
		message.add(scroll2);
		message.add(messageInput);
		
		leftPane.setRightComponent(message);
		leftPane.getRightComponent().setMinimumSize(new Dimension(100, 60));
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		playButton = new JButton(" Play ");
		playButton.addActionListener(new PlayButtonListener());
		bottomPanel.add(playButton);
		
		passButton = new JButton(" Pass ");
		passButton.addActionListener(new PassButtonListener());
		bottomPanel.add(passButton);
	
		bottomPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		bottomPanel.setSize(500, 35);
		
		if (game.getCurrentIdx() == activePlayer)
		{
			bottomPanel.setEnabled(true);
			playButton.setEnabled(true);
			passButton.setEnabled(true);
		}
		else
		{
			bottomPanel.setEnabled(false);
			playButton.setEnabled(false);
			passButton.setEnabled(false);
		} 
		
		final JSplitPane rightPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		rightPane.setDividerLocation(800);;
		rightPane.setTopComponent(bigTwoPanel);
		rightPane.setBackground(Color.green.darker().darker());
		rightPane.setBottomComponent(bottomPanel);
		rightPane.getBottomComponent().setMinimumSize(new Dimension(800, 35));
		
		leftPane.setLeftComponent(rightPane);
	}
	
	
	/**
	 * This class inside BigTwoTable extends JPanel and implements the MouseListener Interface. 
	 * @author Tanmay
	 *
	 */
	
	class BigTwoPanel extends JPanel implements MouseListener{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		/**
		 * This is a BigTwoPanel constructor which adds Mouse Listener to the panels.
		 */
		
		public BigTwoPanel()
		{
			this.addMouseListener(this);
		}

		/*  
		 * {@inheritDoc}
		 */
		
		@Override
		public void mouseClicked(MouseEvent m) 
		{
			// TODO Auto-generated method stub

			boolean flag = false; 
			int startingPoint = game.getPlayerList().get(activePlayer).getNumOfCards()-1;
			
			
			if (m.getX() >= (155+startingPoint*40) && m.getX() <= (155+startingPoint*40+73)) 
			{
				if(selected[startingPoint] == false && m.getY() >= 43 + 160 * activePlayer && m.getY() <= 43 + 160 * activePlayer+97) 
				{
					selected[startingPoint] = true;
					flag = true;
			
				} 
				
				else if (selected[startingPoint] == true && m.getY() >= 43 + 160 * activePlayer-20 && m.getY() <= 43 + 160 * activePlayer+97-20)
				{
					selected[startingPoint] = false;
					flag = true;
				}
			}
			
			for (startingPoint = game.getPlayerList().get(activePlayer).getNumOfCards()-2; startingPoint >= 0 && !flag; startingPoint--) 
			{
				if (m.getX() >= (155+startingPoint*40) && m.getX() <= (155+startingPoint*40+40)) 
				{
					if(selected[startingPoint] == false && m.getY() >= 43 + 160 * activePlayer && m.getY() <= 43 + 160 * activePlayer+97) 
					{
						selected[startingPoint] = true;
						flag = true;
					} 
					
					else if (selected[startingPoint] == true && m.getY() >= 43 + 160 * activePlayer-20 && m.getY() <= 43 + 160 * activePlayer+97-20)
					{
						selected[startingPoint] = false;
						flag = true;
					}
				}
				
				else if (m.getX() >= (155+startingPoint*40+40) && m.getX() <= (155+startingPoint*40+73) && m.getY() >= 43 + 160 * activePlayer && m.getY() <= 43 + 160 * activePlayer+97) 
				{
					if (selected[startingPoint+1] == true && selected[startingPoint] == false) 
					{
						selected[startingPoint] = true;
						flag = true;
					}
				}
				
				else if (m.getX() >= (155+startingPoint*40+40) && m.getX() <= (155+startingPoint*40+73) && m.getY() >= 43 + 160 * activePlayer-20 && m.getY() <= 43 + 160 * activePlayer+97-20)
				{
					if (selected[startingPoint+1] == false && selected[startingPoint] == true)
					{
						selected[startingPoint] = false;
						flag = true;
					}
				}
			}
			
			this.repaint();		
		}

		/*  
		 * {@inheritDoc}
		 */
		
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			this.setBackground(Color.GREEN.darker());
			int x=0;
			for(int j=0; j<4;j++)
		    {//
				g2.drawLine(0, 160+x, 1600, 160+x);
		    
		    if (presence[j])
		    {
			    if (game.getCurrentIdx() == j && game.getPlayerList().get(j).getNumOfCards() != 0)
			    {
			    		g.setColor(Color.BLUE);
			    }
			    		
		        	if (activePlayer == j)
		        	{
		        		g.drawString("You", 10, 30+x);
		        	}
		        		
		        else
		        {
		        		g.drawString(game.getPlayerList().get(j).getName(), 10, 30+x);
		        }
		        	
		        	g.setColor(Color.BLACK);
        			g.drawImage(avatars[j], 10, 25+x, this);
		        		
			}
		    
		    
		    if (activePlayer == j) 
		    {
		    	for (int i = 0; i < game.getPlayerList().get(j).getNumOfCards(); i++) 
		    	{
		    		if (!selected[i])
		    		{
		    			g.drawImage(cardImages[game.getPlayerList().get(j).getCardsInHand().getCard(i).getSuit()][game.getPlayerList().get(j).getCardsInHand().getCard(i).getRank()], 155+40*i, 43+x, this);
		    		}
		    			
		    		else
		    		{
		    			g.drawImage(cardImages[game.getPlayerList().get(j).getCardsInHand().getCard(i).getSuit()][game.getPlayerList().get(j).getCardsInHand().getCard(i).getRank()], 155+40*i, 43-20+x, this);
		    		}
		    			
		    	}
		    } 
		    
		    else
		    {
		    	for (int i = 0; i < game.getPlayerList().get(j).getCardsInHand().size(); i++)
		    	{
		    		g.drawImage(cardBackside, 155 + 40*i, 43+x, this);
		    	}
		    }
		    	x+=160;
		    }
		    g.drawString("Table:", 10, 660);
		     
		    if (!game.getHandsOnTable().isEmpty())
		    {
		    	Hand handOnTable = game.getHandsOnTable().get(game.getHandsOnTable().size()-1);
			    g.drawString("Hand Type:\n ", 10, 700);
			   	g.drawString(game.getHandsOnTable().get(game.getHandsOnTable().size()-1).getType() , 10, 725);
			   	for (int i = 0; i < handOnTable.size(); i++)
		  		{
	    			g.drawImage(cardImages[handOnTable.getCard(i).getSuit()][handOnTable.getCard(i).getRank()], 160 + 40*i, 690, this);
	    		}  	
		    }
		    
		    repaint();
		}

		/*  
		 * {@inheritDoc}
		 */
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		/* 	
		 * {@inheritDoc}
		 */
		
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		/*  
		 * {@inheritDoc}
		 */
		
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		/*  
		 * {@inheritDoc}
		 */
		
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	/**
	 * This class inside BigTwoTable handles the click events for the PlayButton
	 * @author Tanmay
	 *
	 */
	
	class PlayButtonListener implements ActionListener
	{
		
		@Override
		public void actionPerformed(ActionEvent event)
		{
			if (game.getCurrentIdx() != activePlayer)
			{ 
				printMsg("Not your turn\n");		
			}
			else if (getSelected().length == 0) 
				{	
					int [] cardIdx = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
					game.makeMove(activePlayer, cardIdx);
				}
				
				else
				{
					game.makeMove(activePlayer, getSelected());
				}
					
				resetSelected();
				repaint();
			
		}
	}
	
	/**
	 * This  class inside BigTwoTable handles the click events for the PauseButton
	 * @author Tanmay
	 *
	 */
	
	class PassButtonListener implements ActionListener
 	{
	

	public void actionPerformed(ActionEvent e)
	{
		if (game.getCurrentIdx() != activePlayer)
		{ 
			printMsg("Not your turn!\n");
			resetSelected();
			repaint();
			
		} 
		else 
		{
			int[] cardIdx = null;
			game.makeMove(activePlayer, cardIdx);
			resetSelected();
			repaint();
		}
	}
}
	class ConnectMenuItemListener implements ActionListener
	{
		
		public void actionPerformed(ActionEvent e) 
		{
			
			
			if (game.getPlayerID() >= 0 && game.getPlayerID() <= 3)
			{
				printMsg("Connection already established!\n");
			}
			
			else if (game.getPlayerID() == -1) 
			{
				game.makeConnection();
			} 
				
		}
	}
	
	/**
	 * This  class implements handles the click events on the RestartMenuItem 
	 * @author Tanmay
	 *
	 */
	
	class RestartMenuItemListener implements ActionListener
	{

		/*  
		 * {@inheritDoc}
		 */
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			BigTwoDeck deck = new BigTwoDeck();
			deck.shuffle();
			reset();
			game.start(deck);
			
			
		}
	}
	
	/**
	 * This class handles the click events on the QuitMenuItem 
	 * @author Tanmay
	 *
	 */
	
	class QuitMenuItemListener implements ActionListener{

		/*  
		 * {@inheritDoc}
		 */
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.exit(0);	
		}
		
		
	}
	
	class ClearMenuItemListener implements ActionListener
	{
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			clearMsgArea();
		}
	}
	
	
	class ClearChatMenuItemListener implements ActionListener
	{
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			clearChatMsgArea();
		}
	}
	
	class MyTextField extends JTextField implements ActionListener
	{

		
		private static final long serialVersionUID = 1L;

		public MyTextField(int x)
		{
			super(x);
			addActionListener(this);
		}

		public void actionPerformed(ActionEvent event)
		{
			String in = getText();
			if (in.trim().isEmpty() != true) 
			{
				CardGameMessage message = new CardGameMessage(7, activePlayer, in);
				game.sendMessage(message);
			}
			
			this.setText("");
		}
		
	}
	
	

}