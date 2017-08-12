package model.entity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.map.Map;
import util.Coord;
import util.Gloabal.Controllers;

public class End extends Entity implements Observer{

	Rectangle r;

	public End(final Map map, final int row, final int col) {
		super(new Coord(row, col));
		r = new Rectangle(map.getTileSize(),map.getTileSize());
		r.setFill(Color.LIMEGREEN);
		r.relocate(getCol() * map.getTileSize(),  getRow() * map.getTileSize());
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
		for(Bot b : bots) if(this.isIntersect(b)) { b.leaved(); }
	}
}
