/**
 * This public subclass of Deck is used to model the deck of cards to be used in the BigTwo card game
 * @author Tanmay
 *
 */
public class BigTwoDeck extends Deck{
	
	private static final long serialVersionUID = 1L;
	
	/* 
	 * The subclass removes all previous cards and creates each card of a standard deck
	 */
	public void initialize()
	{
		removeAllCards();
		for(int i=0;i<4;i++)
		{
			for (int j=0;j<13;j++)
			{
				this.addCard(new BigTwoCard(i, j));
			}
		}
	}
	
}
