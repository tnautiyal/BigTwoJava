/**
 * This public subclass of Hand models a triple hand and checks if it is a valid triple type or not
 * @author Tanmay
 *
 */
public class Triple extends Hand{

	private static final long serialVersionUID = 1L;
	
	/**
	 * This constructor creates a triple object holding the pair hand of specific and card played by that player
	 * @param player
	 * 		The player to whom this hand belongs
	 * @param card
	 * 		The list of cards played by the player
	 */
	public Triple(CardGamePlayer player, CardList card)
	{
		super(player,card);
	}
	
	/* (non-Javadoc)
	 * @see Hand#getTopCard()
	 */
	
	public Card getTopCard()
	{
		if(this.getCard(0).suit > this.getCard(1).suit && this.getCard(0).suit > this.getCard(2).suit)
			return this.getCard(0);
		else if(this.getCard(1).suit > this.getCard(0).suit && this.getCard(1).suit > this.getCard(2).suit)
			return this.getCard(1);
		else
			return this.getCard(2);	
	}
	
	/* (non-Javadoc)
	 * @see Hand#isValid()
	 */
	
	public boolean isValid()
	{
		if(this.size()==3 && this.getCard(0).rank == this.getCard(1).rank && this.getCard(1).rank == this.getCard(2).rank )
		{
			return true;
		}	
		return false;
	}
	
	/* (non-Javadoc)
	 * @see Hand#getType()
	 */
	
	public String getType() 
	{
		return "Triple";
	}

}