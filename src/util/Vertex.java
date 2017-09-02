package util;

public class Vertex extends Coord {
	private static final long serialVersionUID = 5333808555167810911L;

	private double weight;

	public Vertex(int row, int col, double weight) {
		super(row, col);
		this.setWeight(weight);
	}

	public double getWeight() { return weight; }
	public void setWeight(double weight) { this.weight = weight; }

}
