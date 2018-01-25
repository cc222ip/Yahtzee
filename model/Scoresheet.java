package model;

import java.util.ArrayList;

public class Scoresheet {

	private ArrayList<Integer[]> score;

	public enum Label {
		ONES("Ones"), TWOS("Twos"), THREES("Threes"), FOURS("Fours"), FIVES("Fives"), SIXES("Sixes"), SUM("Sum"), BONUS(
				"Bonus"), THREE_OF_A_KIND("Three of a kind"), FOUR_OF_A_KIND("Four of a kind"), FULL_HOUSE(
						"Full House"), SMALL_STRAIGHT("Small straight"), LARGE_STRAIGHT(
								"Large straight"), CHANCE("Chance"), YAHTZEE("Yahtzee"), TOTAL("Total");

		private String name = "";

		Label(String name) {
			this.name = name;
		}

		public String getLabel() {
			return name;
		}

	}

	public Scoresheet(int players) {
		score = new ArrayList<>();
		for (int i = 0; i < players; ++i) {
			Integer[] tmp = new Integer[16];
			for (int j = 0; j < 16; ++j)
				tmp[j] = -1;
			score.add(tmp);
		}

	}

	public void simulateScore(ArrayList<Dice> roll, int player) {
		// CHECK ROLL SIZE >=1

		forgeArray(generateUpperSec(roll), 0, 6, player);
		forgeArray(generateLowerSec(roll, player), 8, 7, player);
	}

	private int[] generateUpperSec(ArrayList<Dice> roll) {
		int[] res = new int[6];
		for (Dice dice : roll)
			res[dice.getValue() - 1]++;

		for (int i = 0; i < 6; ++i)
			res[i] *= (i + 1);
		return res;
	}

	private int[] generateLowerSec(ArrayList<Dice> roll, int player) {
		int[] res = new int[7];

		int same_kind = sameKind(roll, player);

		if (same_kind == 4)
			res[1] = diceSum(roll);
		else if (same_kind == 3) {
			if (fullHouse(roll))
				res[2] = 25;
			else
				res[0] = diceSum(roll);
		} else if (same_kind == 5)
			res[6] = 50;
		else {
			int largest = largestSequence(roll);
			if (largest == 4)
				res[3] = 30;
			else if (largest == 5)
				res[4] = 40;
		}

		res[5] = diceSum(roll);
		return res;
	}

	private int largestSequence(ArrayList<Dice> roll) {
		ArrayList<Dice> sorted = sortRoll(roll);
		int sequence_lenght = 1;
		int largest_lenght = 1;

		for (int i = 0; i < sorted.size() - 1; i++) {
			if ((sorted.get(i).getValue() + 1) == sorted.get(i + 1).getValue()) {
				sequence_lenght++;

			}

			else {
				if ((sorted.get(i).getValue()) != sorted.get(i + 1).getValue()) {
					if (sequence_lenght > largest_lenght)
						largest_lenght = sequence_lenght;
					sequence_lenght = 1;
				}
			}
		}

		return sequence_lenght > largest_lenght ? sequence_lenght : largest_lenght;
	}

	private boolean smallStraightDetect(ArrayList<Dice> roll) {
		int sequence_lenght = 1;
		ArrayList<Dice> sorted = sortRoll(roll);
		Dice last = sorted.get(0);

		if (sorted.get(0).getValue() >= 4 || sorted.get(sorted.size() - 1).getValue() <= 3)
			return false;
		for (int i = 1; i < 5; i++) {

			if (sorted.get(i).getValue() == last.getValue() + 1)
				sequence_lenght++;
			else
				sequence_lenght = 1;

			if (sequence_lenght >= 4)
				return true;
			last = sorted.get(i);
		}
		return false;
	}

	private ArrayList<Dice> sortRoll(ArrayList<Dice> roll) {
		ArrayList<Dice> tmp = new ArrayList<>();
		for (Dice dice : roll)
			tmp.add(new Dice(dice.getValue()));

		for (int i = 0; i < tmp.size(); ++i)
			for (int j = i; j < tmp.size(); ++j)
				if (tmp.get(i).getValue() > tmp.get(j).getValue()) {
					int _tmp_ = tmp.get(i).getValue();
					tmp.get(i).setValue(tmp.get(j).getValue());
					tmp.get(j).setValue(_tmp_);
				}
		return tmp;
	}

	private boolean fullHouse(ArrayList<Dice> roll) {
		ArrayList<Integer> different_numbers = new ArrayList<>();
		for (Dice dice : roll)
			if (!different_numbers.contains(dice.getValue()))
				different_numbers.add(dice.getValue());

		return different_numbers.size() == 2;
	}

	private int diceSum(ArrayList<Dice> roll) {
		int res = 0;
		for (Dice dice : roll)
			res += dice.getValue();
		return res;
	}

	private int sameKind(ArrayList<Dice> roll, int player) {
		int same = 0;

		for (int i = 0; i < 6; ++i) {
			int same_player = (score.get(player)[i] - 500) / (i + 1);
			same = same_player > same ? same_player : same;
		}

		return same;
	}

	private void forgeArray(int[] a, int begin, int size, int player) {
		for (int i = begin, j = 0; i < begin + size; i++, j++)
			if (score.get(player)[i] == -1 || score.get(player)[i] >= 500)
				score.get(player)[i] = 500 + a[j];
	}

	public void addScore(int player, Label label, int score) {
		this.score.get(player)[label.ordinal()] = score;
	}

	public String getLabelById(int id) {
		for (Label label : Label.values())
			if (label.ordinal() == id)
				return label.name;
		return "";
	}

	public String getScoreByPlayer(Player player) {
		String res = "";
		int ite = 0;

		res += "Player " + player.getName() + '\n';
		for (Integer y : score.get(player.getID()))
			res += getLabelById(ite++) + ":" + (y == -1 ? "" : (y >= 500 ? ">" + (y - 500) + "<" : y)) + '\n';
		res += '\n';

		return res;
	}

	public String getScoreByPlayerEdit(Player player) {
		String res = "";
		int ite = 0;

		res += "Player " + player.getName() + '\n';
		for (Integer y : score.get(player.getID()))
			res += (y >= 500 ? "[" + ite + "]" : "") + getLabelById(ite++) + ":"
					+ (y == -1 ? "" : (y >= 500 ? ">" + (y - 500) + "<" : y)) + '\n';

		res += '\n';

		return res;
	}

	public boolean lock(int score_id, int player) {
		if (score.get(player)[score_id] < 500)
			return false;

		for (int i = 0; i < 16; ++i)
			if (score.get(player)[i] >= 500)
				score.get(player)[i] = i != score_id ? -1 : score.get(player)[i] - 500;
		return true;
	}

	public void updateBonus(int player) {


		int sum = 0;
		for (int i = 0; i < 6; ++i) {
			if (score.get(player)[i] == -1)
				return;
			sum += score.get(player)[i];
		}


		ActionBonus visitor= new ActionBonus();

		Regular r = new Regular(sum);

		score.get(player)[6] = sum;
		score.get(player)[7] = r.accept(visitor);
	}

	public void updateTotal(int player) {
		int sum = 0;
		for (int i = 8; i < 15; ++i)
			sum += score.get(player)[i];

		sum += score.get(player)[6];
		sum += score.get(player)[7];

		score.get(player)[15] = sum;

	}

	public int getWinner() {
		int p_max = 0;
		for (int i = 1; i < score.size(); ++i)
			p_max = score.get(i)[15] > score.get(p_max)[15] ? i : p_max;
		return p_max;
	}

	public String display(ArrayList<Player> players) {
		String res = "";
		int ite, i_player = 0;
		for (Integer[] x : score) {
			ite = 0;
			res += "Player " +players.get(i_player++).getName() + '\n';
			for (Integer y : x)
				res += getLabelById(ite++) + ":" + (y == -1 ? "" : y) + '\n';

			res += '\n';
		}

		return res;
	}
}
