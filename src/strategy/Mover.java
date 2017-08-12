package strategy;

import model.entity.Bot;
import model.map.Map;
import util.Coord;

public interface Mover {
	public Coord selectNextMove(final Map map, final Bot bot);
}
