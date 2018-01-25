package model;

public class ActionBonus implements IVisitor{

	@Override
	public int visit(IVisitable o) {

		return 0;
	}

	@Override
	public int visit(Classic o) {
		return o.getSum();
	}

	@Override
	public int visit(Regular o) {
		return o.getSum();
	}

}
