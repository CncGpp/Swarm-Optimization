package model.entity;

import util.Coord;

public abstract class Entity {

	private Coord coordinate = new Coord(-1, -1);

	// COSTRUTTORI
	public Entity() {}
	public Entity(final Coord coordinate) {this.setCoordinate(coordinate);}

	// METODI GETTER & SETTER
	public Coord getCoordinate(){ return coordinate; }
	public void setCoordinate(final Coord coordinate) {this.coordinate = coordinate;}
	public void setCoordinate(final int row, final int col){
		this.coordinate.setRow(row);
		this.coordinate.setCol(col);
	}

	public int getRow(){ return coordinate.getRow(); }
	public int getCol(){ return coordinate.getCol(); }


	//METODI DI CLASSE
	public boolean isIntersect(final Entity e){
		return this.getRow() == e.getRow() && this.getCol() == e.getCol();
	}

	public abstract void add();
	public abstract void remove();
}
