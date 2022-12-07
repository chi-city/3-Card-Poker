// ---------------------------------------------

/*
 * Authors: 
 * 			Zaid Awaidah
 * 			Alex Castillo
 * UIC, Fall 2022
 * CS 342
 * 
 * Dealer
 * 
*/

// ---------------------------------------------

import java.util.ArrayList;

// ---------------------------------------------

public class Dealer {
	//-----------------------------
	
	private Deck deck;
	private ArrayList<Card> dealersHand;
	private boolean dealer_hand = true;
	
	//-----------------------------
	
	/* default constructor */
	public Dealer() {
		this.deck = new Deck();
		this.dealersHand = new ArrayList<Card>();
	}

	/* getDealerHand
	 * returns player hand
	 * (used for testing)
	*/
	public ArrayList<Card> getDealerHand() { return this.dealersHand; }
	
	//-----------------------------

	/* addDealerHand
	 * adds a card to the dealers hand
	 * (used for testing)
	*/
	public void addDealerHand(Card card) {
		if (dealersHand.size() < 3) {
			this.dealersHand.add(card);
		}
	}
	
	//-----------------------------

	/* clearDealerHand -
	 * clears dealer hand for next round
	*/
	public void clearDealerHand() {
		if (this.dealersHand.size() >= 3)
			this.dealersHand.clear();

		// re-initialize player hand
		ArrayList<Card> n_hand = new ArrayList<Card>();
		this.dealersHand = n_hand;
	}
	
	//-----------------------------

	/*
	 * dealHand
	 * 
	 * deals the hand in each round
	 *  
	 */
	public ArrayList<Card> dealHand() {
		if (this.deck.getDeck().size() <= 34) {
			this.deck.newDeck();
			dealer_hand = true;
		}
		
		ArrayList<Card> hand = new ArrayList<Card>();
		
		if (dealer_hand) {
			for (int i = 0; i < 3; i++) {
				this.dealersHand.add(this.deck.getDeck().get(0));
				this.deck.getDeck().remove(0);
			}
			dealer_hand = false;
		}
		
		for (int i = 0; i < 3; i++) {
			hand.add(this.deck.getDeck().get(0));
			this.deck.getDeck().remove(0);
		}
		

		return hand;
	}
	
	//-----------------------------
	
	/* getDeck - returns deck */
	public ArrayList<Card> getDeck() {
		return this.deck.getDeck();
	}
	
	//-----------------------------
}

//---------------------------------------------