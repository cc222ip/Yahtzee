package view;

import java.util.ArrayList;

import model.Dice;

public class View {

	public void welcome() {
		System.out.println("Welcome to the Yathzee game");
		System.out.println();
		System.out.println("Please select the number of player(s): ");
	}

	public void displayTurn(int turn) {
		System.out.println("Turn " + turn);
	}

	public void displayPlayerScore(String info) {
		System.out.println("========== SCORE ==========");
		System.out.println(info);
		System.out.println("==========================");
	}

	public void displayRoll(int roll) {
		System.out.println("Roll " + (roll + 1) + "/3\nThat's your score:");

	}

	public void displayDice(int dice, int value) {
		System.out.println("Dice " + dice + ": " + value);
	}

	public void displayHandDice() {
		System.out.println("Do you want to keep some dices in your hand for the next throw?\nHow much: ");

	}

	public void displayWhichDice() {
		System.out.println("Let s choose the dices");
	}

	public void displayDices(ArrayList<Dice> arr, int on, int last) {
		System.out.println("Choose for dice " + on + "/" + last);
		for (int i = 0; i < arr.size(); i++)
			System.out.println("Dice [" + i + "] => " + arr.get(i).getValue());
	}

	public void displayLock() {
		System.out.println("Do you want to lock your result [yes/no]?");
	}

	public void displayPreview() {
		// TODO Auto-generated method stub

	}

	public void displayTableDice() {
		System.out.println("Do you want to throw some dices that are in your hand for the next throw?\nHow much: ");
	}

	public void displaySavedDices(ArrayList<Dice> hand) {
		System.out.println("You saved:");
		for (int i = 0; i < hand.size(); i++)
			System.out.println("Dice [" + i + "] => " + hand.get(i).getValue());

	}

	public void chooseLock(String score) {
		System.out.println("Please select which value that you want to keep:");
		System.out.println(score);
		System.out.print("> ");
	}

	public void displayWinner(int winner) {
		System.out.println("The winner is the player " + winner + "!");
	}
}
