/**
 * This public subclass of Hand models a single hand and checks if it is a valid single type or not
 * @author Tarun
 *
 */
public class Single extends Hand {
	private static final long serialVersionUID = 1L;
	
	/**
	 * This constructor creates a Single object holding the single hand of specific and card played by that player
	 * @param player
	 * 		The player to whom this hand belongs
	 * @param card
	 * 		The list of cards played by the player
	 */
	public Single(CardGamePlayer player, CardList cards)
	{
		super(player, cards);	
	}
	
	/* (non-Javadoc)
	 * @see Hand#getTopCard()
	 */
	public Card getTopCard()
	{
		return this.getCard(0);
	}
	/* (non-Javadoc)
	 * @see Hand#isValid()
	 */
	public boolean isValid() 
	{
		if(this.size()==1)
			return true;
		else
			return false;
	}
	/* (non-Javadoc)
	 * @see Hand#getType()
	 */
	public String getType() 
	{
		return "Single";
	}
}