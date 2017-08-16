package model.entity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import model.map.Map;
import util.Coord;

public abstract class AEnd extends Entity implements Observer{

	public AEnd(final Map map, final Coord coordinate) {
		super(map, coordinate);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(!(o instanceof Colony)) return;

		@SuppressWarnings("unchecked")
		ArrayList<ABot> bots = (ArrayList<ABot>)arg;
		for(ABot b : bots) if(this.isIntersect(b)) { b.leaved(); }
	}


}
