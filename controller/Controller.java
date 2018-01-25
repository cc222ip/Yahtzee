package controller;

import java.util.ArrayList;
import java.util.Scanner;

import model.AI;
import model.Dice;
import model.Player;
import model.Scoresheet;
import view.View;

public class Controller {
	private View view;
	private Scoresheet score;
	private ArrayList<Player> players = new ArrayList<>();
	private Scanner scan = new Scanner(System.in);

	public Controller(View view) {
		this.view = view;
	}

	public void newGame() {

		int number_of_player = 0;

		view.welcome();

		do {
			number_of_player = scan.nextInt();
		} while (number_of_player <= 0 || number_of_player >= 5);

		for (int player = 0; player < number_of_player; player++) {
			System.out.print("Name: ");
			players.add(new Player(scan.next(), player));
		}

		if (number_of_player == 1)
			players.add(new AI("AI", number_of_player++));

		score = new Scoresheet(number_of_player);

		// a game 13*numbers

		for (int turn = 0; turn < 13; ++turn) {
			view.displayTurn(turn);
			for (int player = 0; player < number_of_player; ++player) {
				Player current_player = players.get(player);
				boolean AI = current_player.getClass().getName().equals("model.AI");

				current_player.initStorage();
				boolean continue_turn = true;

				for (int dice = 0; dice < 5; dice++)
					current_player.addDice(new Dice());

				for (int roll = 0; roll < 3 && continue_turn; ++roll) {

					if (roll != 0)
						current_player.rollTurn(this, roll);

					for (int dice = 0; dice < current_player.getTable().size(); ++dice) {
						current_player.getTable().get(dice).roll();
						if (!AI)
							view.displayDice(dice, current_player.getTable().get(dice).getValue());
					}

					score.simulateScore(mergeArray(current_player.getHand(), current_player.getTable()), player);

					if (!AI)
						view.displayPlayerScore(score.getScoreByPlayer(current_player));

					if (roll < 2)
						continue_turn = lock(current_player);

				}
				if (!AI)
					view.chooseLock(score.getScoreByPlayerEdit(current_player));

				chooseLock(current_player);
				score.updateBonus(player);
				if (turn == 12)
					score.updateTotal(player);
			}
			view.displayPlayerScore(score.display(players));
		}
		view.displayPlayerScore(score.toString());
		view.displayWinner(score.getWinner());
	}

	private void chooseLock(Player current_player) {
		current_player.chooseLockID(this);
	}

	private ArrayList<Dice> mergeArray(ArrayList<Dice> hand, ArrayList<Dice> table) {
		ArrayList<Dice> res = new ArrayList<>();

		for (Dice dice : hand)
			res.add(dice);
		for (Dice dice : table)
			res.add(dice);

		return res;
	}

	private boolean lock(Player player) {
		return player.getChoice(this);
	}

	private void handDices(ArrayList<Dice> hand, ArrayList<Dice> table, boolean hand_to_table) {
		if (!hand_to_table)
			view.displayHandDice();
		else
			view.displayTableDice();
		int number_of_dice = -1, dice_id = -1;

		do {
			number_of_dice = scan.nextInt();
		} while (number_of_dice < 0 || number_of_dice > table.size());

		view.displayWhichDice();
		for (int i = 0; i < number_of_dice; i++) {
			view.displayDices(table, i, number_of_dice);
			do {
				dice_id = scan.nextInt();
			} while (dice_id < 0 || dice_id > table.size() - 1);

			hand.add(table.get(dice_id));
			table.remove(dice_id);
		}

	}

	public boolean processChoiceRequest() {
		view.displayLock();
		String choice = "";

		do {
			choice = scan.next();
		} while (!choice.equals("yes") && !choice.equals("no"));

		return choice.equals("no");
	}

	public void chooseLockID(Player player) {
		int score_id;
		do {
			score_id = -1;
			do {
				score_id = scan.nextInt();
			} while (score_id < 0 || score_id >= 15 || score_id == 6 || score_id == 7);
		} while (!scoreLockValidate(score_id, player));

	}

	public boolean scoreLockValidate(int score_id, Player player) {
		return score.lock(score_id, player.getID());
	}

	public void rollTurn(Player player, int roll) {
		view.displayRoll(roll);
		view.displayPlayerScore(score.getScoreByPlayer(player));
		view.displaySavedDices(player.getHand());

		handDices(player.getHand(), player.getTable(), false);

		if (roll == 2 && player.getHand().size() > 0)
			handDices(player.getTable(), player.getHand(), true);
	}
}
