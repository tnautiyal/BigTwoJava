/**
 * This public subclass of Hand models a pair hand and checks if it is a valid pair type or not
 * @author Tanmay
 *
 */
public class Pair extends Hand 
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * This constructor creates a pair object holding the pair hand of specific and card played by that player
	 * @param player
	 * 		The player to whom this hand belongs
	 * @param card
	 * 		The list of cards played by the player
	 */
	public Pair(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	/* (non-Javadoc)
	 * @see Hand#getTopCard()
	 */
	public Card getTopCard()
	{
		if(this.getCard(0).suit > this.getCard(1).suit)
		{
			return this.getCard(0);
		}
		return this.getCard(1);
	}
	
	/* (non-Javadoc)
	 * @see Hand#isValid()
	 */
	public boolean isValid()
	{
		if(this.size()==2 && this.getCard(0).rank == this.getCard(1).rank)
		{
			return true;
		}	
		return false;	
	}
	/* (non-Javadoc)
	 * @see Hand#getType()
	 */
	public String getType() {
		return "Pair";
	}
	
}