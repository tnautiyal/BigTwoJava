
/**
 * This public subclass of Hand models a quad hand and checks if it is a valid quad type or not
 * @author Tanmay
 *
 */
public class Quad extends Hand{

	private static final long serialVersionUID = 1L;


	/**
	 * This constructor creates a quad object holding the pair hand of specific and card played by that player
	 * @param player The player to whom this hand belongs
	 * @param card The list of cards played by the player
	 */
	
	public Quad(CardGamePlayer player, CardList card)
	{
		super(player,card);
	}
	
	/* (non-Javadoc)
	 * @see Hand#getTopCard()
	 */
	
	public Card getTopCard()
	{
		this.sort();
		if(this.getCard(3).getRank()==this.getCard(4).getRank())
			return this.getCard(4);
		return this.getCard(3);
	}
	
	/* (non-Javadoc)
	 * @see Hand#isValid()
	 */
	
	public boolean isValid()
	{
		if(this.size() != 5)
			return false;
		
		this.sort();
		if(this.getCard(1).rank == this.getCard(2).rank && this.getCard(2).rank == this.getCard(3).rank)
		{
			if(this.getCard(0).rank == this.getCard(1).rank || this.getCard(1).rank == this.getCard(4).rank)
				return true;
		}
		
		return false;
	}
	
	
	/* (non-Javadoc)
	 * @see Hand#getType()
	 */
	
	public String getType()
	{
		return "Quad";
	}
}