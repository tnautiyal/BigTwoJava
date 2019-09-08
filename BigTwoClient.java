import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;


public class BigTwoClient implements CardGame, NetworkGame
{
	private int numOfPlayers;
	private Deck deck;
	private ArrayList<CardGamePlayer> playerList;
	private ArrayList<Hand> handsOnTable;
	private int playerID; 
	private String playerName; 
	private String serverIP; 
	private int serverPort; 
	private Socket sock;
	private ObjectOutputStream oos;
	private int currentIdx; 
	private BigTwoTable table; 
	private ObjectInputStream ois;
	
	public BigTwoClient()
	{
		playerList = new ArrayList<CardGamePlayer>();
		
		for (int i = 0; i < 4; i++)
		{
			playerList.add(new CardGamePlayer());
		}
			
		handsOnTable = new ArrayList<Hand>();
		table = new BigTwoTable(this);
		table.disable();
		
		playerName = (String) JOptionPane.showInputDialog("Player Name: ");
		
		if (playerName == null)
		{
			playerName = "Guest";
		}
			
		makeConnection();
		table.repaint();
	}
	
	public int getNumOfPlayers() 
	{
		return numOfPlayers;
	}

	
	public Deck getDeck()
	{
		return this.deck;
	}

	
	public ArrayList<CardGamePlayer> getPlayerList() 
	{
		return playerList;
	}

	public ArrayList<Hand> getHandsOnTable() 
	{
		return handsOnTable;
	}

	public int getCurrentIdx()
	{
		return currentIdx;
	}
	
	public void start(Deck deck) 
	{
		this.deck = deck;
		
		handsOnTable.clear();
		currentIdx = -1;
		
		for(int i = 0; i < 4; i++)
		{
			playerList.get(i).removeAllCards();
			for(int j = 0; j < 13; j++) 
			{

				getPlayerList().get(i).addCard(this.deck.getCard(13*i+j));
			
			}
			getPlayerList().get(i).sortCardsInHand();
			if(playerList.get(i).getCardsInHand().contains(new BigTwoCard(0,2)))
			{
				currentIdx = i;
			}
		}
		
		table.repaint();
		table.setActivePlayer(playerID);
	}
	
	public void makeMove(int playerID, int[] cardIdx) 
	{
		
		CardGameMessage message = new CardGameMessage(6, playerID, cardIdx);
		sendMessage(message);
	}
	
	public void checkMove(int playerID, int[] cardIdx)
	{
		int numOfHandsPlayed=handsOnTable.size();
		
		if(cardIdx==null)
		{
			if(numOfHandsPlayed==0 || handsOnTable.get(numOfHandsPlayed-1).getPlayer().getName()==playerList.get(currentIdx).getName() )
			{
				table.printMsg("not a legal move!!!\n");
			}

			else
			{
				table.printMsg(playerList.get(currentIdx).getName()+": "+"{pass}\n");
				currentIdx = (currentIdx+1)%4;
				table.printMsg(this.getPlayerList().get(currentIdx).getName()+" make a move.\n");
			}
		}
		else 
		{
			
				CardList playerSelectedCards =new CardList();
				Hand playerHand;
				
				for(int i=0; i<cardIdx.length; ++i)
				{
					playerSelectedCards.addCard(playerList.get(currentIdx).getCardsInHand().getCard(cardIdx[i]));	
				}
				
				playerHand=composeHand(playerList.get(currentIdx), playerSelectedCards);
				
				if(playerHand!=null)
				{
					playerHand.sort();
				
					if((numOfHandsPlayed==0 && playerHand.getCard(0).getRank()==2 && playerHand.getCard(0).getSuit()==0) || 
					   (numOfHandsPlayed!=0 && (handsOnTable.get(numOfHandsPlayed-1).getPlayer().getName()==playerList.get(currentIdx).getName()|| 
					   (playerHand.size()==handsOnTable.get(handsOnTable.size()-1).size() && handsOnTable.get(handsOnTable.size()-1).beats(playerHand)==false))) )
					{
						table.printMsg(playerList.get(currentIdx).getName()+": "+"{"+playerHand.getType()+"}");
						
						for (int j=0; j<playerHand.size(); ++j)
						{
							table.printMsg(" ["+playerHand.getCard(j).toString()+"]");
						}
						
						table.printMsg("\n");
						playerList.get(currentIdx).removeCards(playerHand);
						
						currentIdx = (currentIdx+1)%4;
						handsOnTable.add(playerHand);
						table.printMsg(this.getPlayerList().get(currentIdx).getName()+" make a move.\n");
					}
				}
				
				else 
				{
					table.printMsg("Illegal move!!!\n");
				}
				
			
			}
		 
		
		if(!endOfGame())
		{
			playerList.get(playerID).getCardsInHand().sort();
			table.resetSelected();
			
			if(this.playerID==currentIdx)
			{
				table.enable();
			}
			
			else
			{
				table.disable();
			}
			
			table.repaint();
		}
		
		else
		{
			table.repaint();
			table.printEndGameMsg();
			handsOnTable.clear();
			
			for (int i=0; i<4; ++i)
			{
				playerList.get(i).removeAllCards();
			}
			
			sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));
		} 
	}
	
	public boolean endOfGame()
	{
		// TODO Auto-generated method stub
		
		for(int i = 0; i < playerList.size();i++)
		{
			if(playerList.get(i).getCardsInHand().size() == 0)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static void main(String[] args)
	{
		BigTwoClient client = new BigTwoClient();
		
	}
	
	public static Hand composeHand(CardGamePlayer player, CardList cards)
	{
		Hand check;
		
		check = new StraightFlush(player, cards);
		
		if (check.isValid())
		{
			return check;
		}
		
		check = new Quad(player, cards);
		
		if (check.isValid())
		{
			return check;
		}
		
		check = new FullHouse(player, cards);
		
		if (check.isValid())
		{
			return check;
		}
		
		check = new Flush(player, cards);
		
		if (check.isValid())
		{
			return check;
		}
		
		check = new Straight(player, cards);
		
		if (check.isValid())
		{
			return check;
		}
		
		check = new Pair(player, cards);
		
		if (check.isValid())
		{
			return check;
		}
		
		check = new Triple(player, cards);
		
		if (check.isValid())
		{
			return check;
		}
		
		check = new Single(player, cards);
		
		if (check.isValid())
		{
			return check;
		}
			
		return null;
	}
	
	public int getPlayerID()
	{
		return playerID;
	}

	public void setPlayerID(int playerID)
	{
		this.playerID = playerID;
	}

	public String getPlayerName()
	{
		return playerName;
	}

	public void setPlayerName(String playerName)
	{
		playerList.get(playerID).setName(playerName);
	}

	public String getServerIP()
	{
		return serverIP;
	}

	public void setServerIP(String serverIP)
	{
		this.serverIP = serverIP;
	}

	public int getServerPort()
	{
		return serverPort;
	}
	
	public void setServerPort(int serverPort)
	{
		this.serverPort = serverPort;
	}
	
	public void makeConnection()
	{
		serverIP = "127.0.0.1";
		serverPort = 2396;
		
		try 
		{
			sock = new Socket(this.serverIP, this.serverPort);
			oos = new ObjectOutputStream(sock.getOutputStream());
			ois = new ObjectInputStream(sock.getInputStream());
		}
		
		catch (IOException exception)
		{
			exception.printStackTrace();
		}
		
		Runnable new_job = new ServerHandler();
		Thread new_thread = new Thread(new_job);
		new_thread.start();
		
		sendMessage(new CardGameMessage(1, -1, this.getPlayerName()));
		sendMessage(new CardGameMessage(4, -1, null));
		
		table.repaint();
	}
	
	public void parseMessage(GameMessage message)
	{
		
		if(message.getType() == CardGameMessage.PLAYER_LIST)
		{
			this.setPlayerID(message.getPlayerID());
			table.setActivePlayer(playerID);
			
			for (int i = 0; i < 4; i++)
			{
				if (((String[])message.getData())[i] != null)
				{
					this.playerList.get(i).setName(((String[])message.getData())[i]);
					table.setExistence(i);
				}
			}
			
			table.repaint();
		}
		
		else if(message.getType() == CardGameMessage.JOIN)
		{
			playerList.get(message.getPlayerID()).setName((String)message.getData());
			table.setExistence(message.getPlayerID());
			table.repaint();
			table.printMsg("Player " + playerList.get(message.getPlayerID()).getName() + " joined the game!\n");
		}
		
		else if(message.getType() == CardGameMessage.FULL)
		{
			playerID = -1;
			table.printMsg("The game is full now!\n");
			table.repaint();
		}
		
		else if(message.getType() == CardGameMessage.QUIT)
		{
			table.printMsg("Player " + message.getPlayerID() + " " + playerList.get(message.getPlayerID()).getName() + " left the game.\n");
			playerList.get(message.getPlayerID()).setName("");
			table.setNotExistence(message.getPlayerID());
			if (this.endOfGame() == false)
			{
				table.disable(); 
				this.sendMessage(new CardGameMessage(4, -1, null));
				for (int i = 0; i < 4; i++)
				{
					playerList.get(i).removeAllCards();
				}
					
				table.repaint();
			}
			
			table.repaint();
		}
		
		else if(message.getType() == CardGameMessage.READY)
		{
			table.printMsg("Player " + message.getPlayerID() + " is ready now!\n");
			handsOnTable = new ArrayList<Hand>();
			table.repaint();
		}
		
		else if(message.getType() == CardGameMessage.START)
		{
			start((BigTwoDeck)message.getData());
			table.printMsg("Game is started now!\n\n");
			table.enable();
			table.repaint();
		}
		
		else if(message.getType() == CardGameMessage.MOVE)
		{
			checkMove(message.getPlayerID(), (int[])message.getData());
			table.repaint();
		}
		
		else if(message.getType() == CardGameMessage.MSG)
		{
			table.printChatMsg((String)message.getData());
		}
		
		else
		{
			table.printMsg("Wrong message type: " + message.getType());
			table.repaint();
		}
		
	}
	
	public void sendMessage(GameMessage message)
	{
		try 
		{
			oos.writeObject(message);
		}
		
		catch (IOException exception)
		{
			exception.printStackTrace();
		}
	}
	
	class ServerHandler implements Runnable
	{	
		public void run() 
		{
			CardGameMessage message = null;
			
			try
			{
				while ((message = (CardGameMessage) ois.readObject()) != null)
				{
					parseMessage(message);
				}
			} 
			catch (Exception exception) 
			{
				exception.printStackTrace();
			}
			table.repaint();
		}
	}

}
