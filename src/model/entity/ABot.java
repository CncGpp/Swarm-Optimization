package model.entity;

import java.util.ArrayList;

import util.Coord;
import util.Path;
import util.Vertex;

public abstract class ABot extends Entity {

	protected AColony colony;
	protected boolean visited[][];
	protected Path path = new Path();

	public ABot(final AColony colony) {
		this(colony, new Coord(-1,-1));
	}
	public ABot(final AColony colony, final Coord coordinate){
		super(colony.getMap(), coordinate);
		this.setColony(colony);
	}

	private void setColony(final AColony colony){
		if(colony == null) throw new IllegalArgumentException("La colonia non può essere 'null'");
		else this.colony = colony;
	}

	public boolean isVisited(final Coord coord){return visited[coord.getRow()][coord.getCol()];}

	public abstract boolean move();
	public abstract boolean moveTo(final Vertex coord);
	public abstract void getNeighbors(ArrayList<Vertex> newDirections, ArrayList<Vertex> oldDirections);
	public abstract void leaved();
}
