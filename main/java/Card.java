// ---------------------------------------------

/*
 * Authors: 
 * 			Zaid Awaidah
 * 			Alex Castillo
 * UIC, Fall 2022
 * CS 342
 * 
 * Card
 * 
*/

// ---------------------------------------------

public class Card {
	// -----------------------------

	private char suit;
	private int value;

	// -----------------------------

	/* Parameterized Constructor */
	public Card(char suit, int value) {
		this.suit = suit;
		this.value = value;
	}

	// -----------------------------

	/*
	 * getSuit - return card suit
	 */
	public char getSuit() {
		return this.suit;
	}

	// -----------------------------

	/*
	 * getValue - return card value
	 */
	public int getValue() {
		return this.value;
	}

	// -----------------------------

}

//---------------------------------------------
