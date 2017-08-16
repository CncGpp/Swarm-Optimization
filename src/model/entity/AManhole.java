package model.entity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import model.map.AMap;
import model.map.TileType;
import util.Coord;
import util.Vertex;

public abstract class AManhole extends Entity implements Observer{

	public AManhole(final AMap map, final Coord coordinate) {
		super(map, coordinate);
	}
	public AManhole(final AMap map, final int row, final int col) {
		this(map, new Coord(row, col));
	}

	public boolean teleport(final AMap map, final ABot b){
		final Random r = new Random();

		//Ho 20 possibilita' di trovare una cella libera per il teletrasporto.
		for(int i = 0; i < 20; i++){
			final int _row = r.nextInt(map.getRows());
			final int _col = r.nextInt(map.getCols());
			if(map.getTileTypeAt(_row, _col) != TileType.WALL) {
				b.moveTo( new Vertex(_row,_col, 0) );
				return true;
			}
		}
		return false;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(!(o instanceof AColony)) return;

		@SuppressWarnings("unchecked")
		ArrayList<ABot> bots = (ArrayList<ABot>)arg;
		for(ABot b : bots)
			if(this.isIntersect(b))
				this.teleport(((AColony)o).getMap(), b);
	}

}
