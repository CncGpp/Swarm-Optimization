package model.entity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javafx.scene.shape.Rectangle;
import model.map.Map;
import model.map.TileType;
import util.Coord;
import util.Gloabal.C;
import util.Gloabal.Controllers;
import util.Node;

public class Manhole extends Entity implements Observer{

	Rectangle r;

	public Manhole(final Map map, final int row, final int col) {
		super(new Coord(row, col));
		r = new Rectangle(map.getTileSize(),map.getTileSize());
		r.setFill(C.MANHOLE_COLOR);
		r.relocate(getCol() * map.getTileSize(),  getRow() * map.getTileSize());
	}


	public boolean teleport(final Map map, final Bot b){
		final Random r = new Random();

		//Ho 20 possibilita' di trovare una cella libera per il teletrasporto.
		for(int i = 0; i < 20; i++){
			final int _row = r.nextInt(map.getRows());
			final int _col = r.nextInt(map.getCols());
			if(map.getTileTypeAt(_row, _col) != TileType.WALL) {
				b.moveTo( new Node(_row,_col, 0) );
				return true;
			}
		}
		return false;
	}


	@Override
	public void add() { Controllers.entityMap.add(r); }

	@Override
	public void remove() { Controllers.entityMap.remove(r); }

	@Override
	public void update(Observable o, Object arg) {
		if(!(o instanceof Colony)) return;

		@SuppressWarnings("unchecked")
		ArrayList<Bot> bots = (ArrayList<Bot>)arg;
		for(Bot b : bots)
			if(this.isIntersect(b))
				this.teleport(((Colony)o).getMap(), b);
	}
}