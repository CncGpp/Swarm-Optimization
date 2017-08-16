package model.entity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import model.map.Map;
import util.Coord;
import util.Gloabal.C;

public class End extends Entity implements Observer{

	Rectangle r;

	public End(final Map map, final int row, final int col) {
		super(new Coord(row, col));
		r = new Rectangle(map.getTileSize(),map.getTileSize());
		r.setFill(C.END_COLOR);
		r.relocate(getCol() * map.getTileSize(),  getRow() * map.getTileSize());
	}

	@Override
	public Node getNode(){ return r;}

	@Override
	public void update(Observable o, Object arg) {
		if(!(o instanceof Colony)) return;

		@SuppressWarnings("unchecked")
		ArrayList<Bot> bots = (ArrayList<Bot>)arg;
		for(Bot b : bots) if(this.isIntersect(b)) { b.leaved(); }
	}
}
