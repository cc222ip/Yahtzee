package model;

public abstract class BonusModel implements IVisitable {
	protected int sum = 0;

	public BonusModel(int sum) {
		this.sum = sum;
	}

	public int accept(IVisitor visitor) {
		return visitor.visit(this);

	}

	abstract int getSum();
}
