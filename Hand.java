/**
 * This public subclass of CardList models the hand of cards. 
 * It stores the cards played by the player and the topcard and checks if moves are valid or not
 *
 */
public class Hand extends CardList{

	private static final long serialVersionUID = 1L;
	
	private CardGamePlayer player;

	/**
	 * This constructor builds the hand of a specific player and list of cards
	 * @param player
	 * 		A list of players in the game
	 * @param cards
	 * 		A list of cards played by the active player
	 */
	
	public Hand(CardGamePlayer player, CardList cards)
	{
		this.player = player;
		
		for(int i = 0; i < cards.size();i++)
		{
			this.addCard(cards.getCard(i));
		}
	}
	
	/** A method for checking who the player of the current hand is
	 * @return returns the player of this hand
	 */
	public CardGamePlayer getPlayer()
	{
		return this.player;
	}

	/** A method for checking the top card of the  current hand is
	 * @return returns the top card of this hand
	 */
	public Card getTopCard()
	{
		Card topCard = this.getCard(0);
		for(int i=0; i< this.size();i++)
		{
			if(topCard.compareTo(this.getCard(i))==-1)
				topCard = getCard(i);
		}
		return topCard;
	}
	
	/** A method for checking if this hand beats a given input hand. 
	 * @param hand The hand to which the current hand is compared
	 * @return true if current hand is better, false otherwise
	 */
	public boolean beats(Hand hand)
	{
		if(this.size() <= 3 &&  this.size()==hand.size())
		{		
			if(this.getType() == hand.getType() && this.getTopCard().compareTo(hand.getTopCard()) == 1)
			{
				return true;
			}
		}
		
		else if (this.size() == 5 && this.size()==hand.size())
		{
			if(this instanceof StraightFlush)
			{
				if(this.getType()==hand.getType() && this.getTopCard().compareTo(hand.getTopCard())==1)
					return true;
				if(this.getType()!= hand.getType())
					return true;
			}
			
			if(this instanceof Quad) 
			{
				if(this.getType()==hand.getType() && this.getTopCard().compareTo(hand.getTopCard())==1)
					return true;
				if(this.getType()!= hand.getType() && hand.getType()!="Straight Flush")
					return true;
			}
			
			if(this instanceof FullHouse) 
			{
				if(this.getType()==hand.getType() && this.getTopCard().compareTo(hand.getTopCard())==1)
					return true;
				if(this.getType()!= hand.getType() && hand.getType()!="Straight Flush" && hand.getType()!="Quad")
					return true;
			}
			
			if(this instanceof Flush) 
			{
				if(this.getType()==hand.getType() && this.getTopCard().compareTo(hand.getTopCard())==1)
					return true;
				if(this.getType()!= hand.getType() && hand.getType()!="Straight Flush" && hand.getType()!="Quad" && hand.getType()!="Full House")
					return true;
			}
			
			if(this instanceof Straight) 
			{
				if(this.getType()==hand.getType() && this.getTopCard().compareTo(hand.getTopCard())==1)
					return true;
			}
							
		}
		return false;
	}
	
	public  boolean isValid() {return false;}
	public  String getType() {return null;}
}
