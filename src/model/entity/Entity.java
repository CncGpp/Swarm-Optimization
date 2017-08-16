package model.entity;

import model.Drawable;
import util.Coord;
import util.Gloabal.Controllers;

public abstract class Entity implements Drawable{

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

	@Override
	public void addNode(){ Controllers.rootView.addNode(this); }

	@Override
	public void removeNode(){ Controllers.rootView.removeNode(this);}
}
