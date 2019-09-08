/**
 * This public subclass of Card allows us to model the cards to be used in the BigTwo card game. 
 * @author Tanmay
 *
 */
public class BigTwoCard extends Card{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * This constructor builds a card with the input suit and rank
	 * @param suit an int value between 0 and 3 representing the suit of a card
	 * 
	 * @param rank an int value between 0 and 12 representing the rank of a card:
	 */
	public BigTwoCard(int suit, int rank)
	{
		super(suit,rank);
	}
	
	/**
	 * Function compares a card with the input card to see which one has a higher rank
	 * @param  card the card to which the current card is compared
	 * 
	 * @return 1 if true, -1 if false and 0 if they are the same card
	 */
	
	public int compareTo(Card card)
	{
		int thisCardRank = this.rank;
		int thatCardRank = card.rank;
		
		
		
		if(thisCardRank <= 1)
		{
			thisCardRank += 13;
		}
		
		
		if(thatCardRank <= 1)
		{
			thatCardRank += 13;
		}
		
		if(thisCardRank>thatCardRank)
			return 1;
		else if(thisCardRank<thatCardRank)
			return -1;
		else if(this.suit>card.suit)
			return 1;
		else if(this.suit<card.suit)
			return -1;
		else return 0;
	}

}
