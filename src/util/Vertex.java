package util;

public class Vertex extends Coord {

	private double weight;

	public Vertex(int row, int col, double weight) {
		super(row, col);
		this.setWeight(weight);
	}

	public double getWeight() { return weight; }
	public void setWeight(double weight) { this.weight = weight; }

}
