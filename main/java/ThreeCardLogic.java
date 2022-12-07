// ---------------------------------------------

/*
 * Authors: 
 * 			Zaid Awaidah
 * 			Alex Castillo
 * UIC, Fall 2022
 * CS 342
 * 
 * ThreeCardLogic
 * 
*/

// ---------------------------------------------

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

//---------------------------------------------

public class ThreeCardLogic {
	// -------------------------
	
	/*
	 * evalHand
	 * 
	 */
	public static int evalHand(ArrayList<Card> hand) {
		// check for straight flush
		if ((hand.get(0).getSuit() == hand.get(1).getSuit()) && (hand.get(1).getSuit() == hand.get(2).getSuit())) {
			Integer valuesArr[] = {hand.get(0).getValue(), hand.get(1).getValue(), hand.get(2).getValue()};
			Arrays.sort(valuesArr, Collections.reverseOrder());
			if ((valuesArr[0] - 1 == valuesArr[1]) && (valuesArr[1] - 1 == valuesArr[2])) {
				return 1;
			}
		}
		// check for three of a kind
		if ((hand.get(0).getValue() == hand.get(1).getValue()) && (hand.get(0).getValue() == hand.get(2).getValue())) {
			return 2;
		}
		// check for a straight
		Integer valuesArr[] = {hand.get(0).getValue(), hand.get(1).getValue(), hand.get(2).getValue()};
		Arrays.sort(valuesArr, Collections.reverseOrder());
		if ((valuesArr[0] - 1 == valuesArr[1]) && (valuesArr[1] - 1 == valuesArr[2])) {
			return 3;
		}
		// check for a flush
		if ((hand.get(0).getSuit() == hand.get(1).getSuit()) && (hand.get(1).getSuit() == hand.get(2).getSuit())){
			return 4;
		}
		// check for a pair
		if ((hand.get(0).getValue() == hand.get(1).getValue()) || (hand.get(0).getValue() == hand.get(2).getValue()) || (hand.get(1).getValue() == hand.get(2).getValue())) {
			return 5;
		}
		// nothing found, only has a high card
		return 0;
	}
	
	// -------------------------

	/*
	 * evalPPWinnings
	 * 
	 */
	public static int evalPPWinnings(ArrayList<Card> hand, int bet) {
		int handResult = evalHand(hand);
		if (handResult == 1) {
			return ((bet * 40) + bet);
		} else if (handResult == 2){
			return ((bet * 30) + bet);
		} else if (handResult == 3) {
			return ((bet * 6) + bet);
		} else if (handResult == 4) {
			return ((bet * 3) + bet);
		} else if (handResult == 5) {
			return ((bet * 1) + bet);
		} else {
			return 0;
		}
	}
	
	// -------------------------
	
	/* breakTie
	 * Breaks a tie if both players have the same type of hand
	 * Returns 0 no one won the tie, returns 1 if dealer won the tie,
	 * returns 2 if player won the tie
	 */
	private static int breakTie(ArrayList<Card> dealer, int dealerResults, ArrayList<Card> player) {
		// storing values from both dealer and player hands in descending order...
		Integer dealerValuesArr[] = {dealer.get(0).getValue(), dealer.get(1).getValue(), dealer.get(2).getValue()};
		Arrays.sort(dealerValuesArr, Collections.reverseOrder());
		Integer playerValuesArr[] = {player.get(0).getValue(), player.get(1).getValue(), player.get(2).getValue()};
		Arrays.sort(playerValuesArr, Collections.reverseOrder());

		if (dealerResults == 0) {
			// The high card of the dealer is less than a queen... tie...
			if (dealerValuesArr[0] < 12) {
				return 0;
			}
		}
		// both have straight flushes
		// both have three of a kind
		// both have a straight
		// both have a flush
		if (dealerResults >= 0 || dealerResults <= 4) {
			if (dealerValuesArr[0] > playerValuesArr[0]) {
				return 1;
			} else if (dealerValuesArr[0] < playerValuesArr[0]) {
				return 2;
			} else {
				return 0;
			}
		}
		// both have a pair
		else if (dealerResults == 5) {
			// looking at the middle of the hand because the array is sorted,
			// thus one of the pair cards will always be in the middle
			// this can then be used to compare the highest pair
			if (dealerValuesArr[1] > playerValuesArr[1]) {
				return 1;
			} else if (dealerValuesArr[1] < playerValuesArr[1]) {
				return 2;
			} else {
				return 0;
			}
		}
		// something went wrong...
		return -1;
	}
	
	// -------------------------

	/*
	 * compareHands
	 */
	public static int compareHands(ArrayList<Card> dealer, ArrayList<Card> player) {
		int dealerResults = evalHand(dealer);
		int playerResults = evalHand(player);

		// Handles edge case if one of the results are 0
		if (dealerResults != 0 && playerResults == 0) {
			return 1;
		} else if (dealerResults == 0 && playerResults != 0) {
			return 2;
		}

		// Both the dealer and the player have the same type of hand... need to determine the highest card
		if (dealerResults == playerResults) {
			return(breakTie(dealer, dealerResults, player));
		}

		if (dealerResults < playerResults) {
			return 1;
		} else if (dealerResults > playerResults) {
			return 2;
		} else {
			return 0;
		}
	}
	
	// -------------------------
	
}

//---------------------------------------------
