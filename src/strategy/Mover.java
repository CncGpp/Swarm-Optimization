package strategy;

import model.entity.ABot;
import model.map.AMap;
import util.Vertex;

public interface Mover {
	public Vertex selectNextMove(final AMap map, final ABot bot);
}
