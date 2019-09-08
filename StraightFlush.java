import java.util.Arrays;

/**
 * This public subclass of Hand models a straight flush hand and checks if it is a valid straight flush type or not
 * @author Tanmay
 *
 */

public class StraightFlush extends Hand 
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * This constructor creates a straight flush object holding the pair hand of specific and card played by that player
	 * @param player The player to whom this hand belongs
	 * @param card The list of cards played by the player
	 */
	
	
	public StraightFlush(CardGamePlayer player, CardList card)
	{
		super(player,card);
	}
	
	/* (non-Javadoc)
	 * @see Hand#getTopCard()
	 */
	
	public Card getTopCard()
	{
		int []ranks =new int[5];
		int index = 0;
		
		for(int i = 0; i < 5;i++)
		{
			ranks[i]=this.getCard(i).getRank();
			if(ranks[i] <= 1)
			{
				ranks[i] += 13;
			}
		}	
		Arrays.sort(ranks);
		for(int i = 0; i < ranks.length;i++)
		{
			if(this.getCard(i).rank == ranks[4])
			{
				index = i;
			}
		}
		
		return this.getCard(index);
	}
	
	/* (non-Javadoc)
	 * @see Hand#isValid()
	 */
	public boolean isValid()
	{
		if(this.size() != 5 || this.getCard(0).suit != this.getCard(1).suit || this.getCard(0).suit != this.getCard(2).suit || this.getCard(0).suit != this.getCard(3).suit || this.getCard(0).suit != this.getCard(4).suit)
		{
			return false;
		}
		
		int []ranks =new int[5];
		
		for(int i = 0; i < 5;i++)
		{
			ranks[i]=this.getCard(i).getRank();
			if(ranks[i] <= 1)
			{
				ranks[i] += 13;
			}
		}	
		
		Arrays.sort(ranks);
		
		for (int i = 0; i < ranks.length-1; i++) 
		{
		    if (ranks[i+1] != ranks[i] + 1) 
		    {	
		      return false;
		    }
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see Hand#getType()
	 */
	
	public String getType()
	{
		return "StraightFlush";
	}
	

}