// ---------------------------------------------

/*
 * Authors: 
 * 			Zaid Awaidah
 * 			Alex Castillo
 * UIC, Fall 2022
 * CS 342
 * 
 * Player
 * 
*/

// ---------------------------------------------

import java.util.ArrayList;

//---------------------------------------------

public class Player {
	// -----------------------------

	private ArrayList<Card> hand;
	private int anteBet;
	private int playBet;
	private int pairPlusBet;
	private int totalWinnings;

	// -----------------------------

	/* Default Constructor */
	public Player() {
		this.hand = new ArrayList<Card>();
		this.anteBet = 0;
		this.playBet = 0;
		this.pairPlusBet = 0;
		this.totalWinnings = 0;
	}

	// -----------------------------

	/* getPlayerHand - returns player hand */
	public ArrayList<Card> getPlayerHand() {
		return this.hand;
	}

	// -----------------------------

	/* getAnteBet - returns AnteBet */
	public int getAnteBet() {
		return this.anteBet;
	}

	// -----------------------------

	/* getPlayBet - returns playBet */
	public int getPlayBet() {
		return this.playBet;
	}

	// -----------------------------

	/* getPPBet - returns pairPlusBet */
	public int getPPBet() {
		return this.pairPlusBet;
	}

	// -----------------------------

	/* getTotalWinnings - returns totalWinnings */
	public int getTotalWinnings() {
		return this.totalWinnings;
	}

	// -----------------------------

	/* setAnteBet - set AnteBet */
	public void setAnteBet(int AnteBet) {
		this.anteBet = AnteBet;
	}

	// -----------------------------

	/* setPlayBet - sets playBet */
	public void setPlayBet(int playBet) {
		this.playBet = playBet;
	}

	// -----------------------------

	/* setPPBet - sets pairPlusBet */
	public void setPPBet(int PPBet) {
		this.pairPlusBet = PPBet;
	}

	// -----------------------------

	/* setTotalWinnings - sets totalWinnings */
	public void setTotalWinnings(int TotalWinnings) {
		this.totalWinnings = TotalWinnings;
	}

	// -----------------------------

	/* setTotalWinnings - adds card to player deck */
	public void addPlayerHand(Card card) {
		this.hand.add(card);
	}

	// -----------------------------

	/* clearPlayerHand - clears player deck for next round */
	public void clearPlayerHand() {
		if (this.hand.size() >= 3)
			this.hand.clear();

		// re-initialize player hand
		ArrayList<Card> n_hand = new ArrayList<Card>();
		this.hand = n_hand;
	}

	// -----------------------------
}

//---------------------------------------------
