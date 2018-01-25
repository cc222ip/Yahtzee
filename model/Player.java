package model;

import java.util.ArrayList;

import controller.Controller;

public class Player {

	private String name;
	private int score_sheet_id;

	private ArrayList<Dice> hand;
	private ArrayList<Dice> table;

	public Player(String name, int score_sheet_id) {
		this.name = name;
		this.score_sheet_id = score_sheet_id;
	}

	public void initStorage() {
		hand = new ArrayList<>();
		table = new ArrayList<>();
	}

	public void addDice(Dice dice) {
		this.table.add(dice);
	}

	public ArrayList<Dice> getHand() {
		return this.hand;
	}

	public ArrayList<Dice> getTable() {
		return this.table;
	}

	public boolean getChoice(final Controller controller) {
		return controller.processChoiceRequest();
	}

	public void chooseLockID(final Controller controller) {
		controller.chooseLockID(this);
	}

	public int getID() {
		return this.score_sheet_id;
	}

	public void rollTurn(final Controller controller, int roll) {
		controller.rollTurn(this, roll);
	}

	public String getName() {
		return this.name;
	}

}
