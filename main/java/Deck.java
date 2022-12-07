// ---------------------------------------------

/*
 * Authors: 
 * 			Zaid Awaidah
 * 			Alex Castillo
 * UIC, Fall 2022
 * CS 342
 * 
 * Deck
 * 
 * Creates a new decks of 52 cards
 * 
*/

// ---------------------------------------------

import java.util.*;

//---------------------------------------------

public class Deck extends ArrayList<Card> {
	// -----------------------------

	private ArrayList<Card> deck = new ArrayList<Card>();

	// -----------------------------

	/* default constructor */
	public Deck() {
		this.deck = this.shuffle(this.deck);
	}

	// -----------------------------

	/*
	 * newDeck
	 * 
	 * clear all cards, and creates new deck
	 */
	public void newDeck() {
		ArrayList<Card> n_deck = new ArrayList<Card>();
		n_deck = this.shuffle(n_deck);
		this.deck = n_deck;
	}

	// -----------------------------

	/*
	 * getDeck
	 * 
	 * returns deck
	 */
	public ArrayList<Card> getDeck() {
		return this.deck;
	}

	// -----------------------------

	/*
	 * shuffle
	 * 
	 * returns deck randomly shuffled
	 */
	private ArrayList<Card> shuffle(ArrayList<Card> deck) {
		char[] suits = new char[4];
		suits[0] = 'D';
		suits[1] = 'C';
		suits[2] = 'S';
		suits[3] = 'H';

		// sort randomly
		for (int j = 2; j < 15; j++) {
			for (char c : suits) {
				deck.add(new Card(c, j));
			}
		}
		Collections.shuffle(deck, new Random(System.currentTimeMillis()));

		return deck;
	}

	// -----------------------------

	/* For Testing Purposes */
	public void printDeck() {
		if (deck.size() == 0)
			System.out.println("Empty deck");

		for (Card c : deck) {
			System.out.println(c.getSuit() + "," + c.getValue());

		}
	}

	// -----------------------------
}

//---------------------------------------------
