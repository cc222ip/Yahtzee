package model;

public class Classic extends BonusModel {

	public Classic(int sum) {
		super(sum);
	}

	@Override
	int getSum() {
		return sum >= 60 ? 30 + sum - 60 : sum;
	}

}
