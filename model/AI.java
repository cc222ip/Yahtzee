package model;

import controller.Controller;

public class AI extends Player {

	public AI(String name, int score_sheet_id) {
		super(name, score_sheet_id);
	}

	public boolean getChoice(final Controller controller) {
		return randInt(1, 100) <= 30;
	}

	private int randInt(int min, int max) {
		return (min + (int) (Math.random() * max));
	}

	@Override
	public void chooseLockID(final Controller controller) {
		int score_id;
		do {
			do {
				score_id = randInt(0, 14);
			} while (score_id < 0 || score_id >= 15 || score_id == 6 || score_id == 7);
		}  while (!controller.scoreLockValidate(score_id, this));
	}

	@Override
	public void rollTurn(final Controller controller, int roll) {
	}
}
