package util;

public class Coord {
	private int row = -1;
	private int col = -1;

	public Coord(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}

	public int getRow() { return row; }
	public void setRow(int row) { this.row = row; }
	public int getCol() { return col; }
	public void setCol(int col) { this.col = col; }

}
