package model;

public class Regular extends BonusModel{

	public Regular(int sum) {
		super(sum);
	}

	@Override
	int getSum() {		
		return sum >= 63 ? 35 : 0;
	}
}
