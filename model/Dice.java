package model;

public class Dice {
	private int value;

	public Dice(int value) {
		this.value = value;
	}
	public Dice() {
		// TODO Auto-generated constructor stub
	}
	public int getValue() {
		return value;
	}
	public void roll() {
		this.value = 1 + (int)(Math.random() * 6);
	}
	public void setValue(int value) {
		this.value = value;

	}
}
