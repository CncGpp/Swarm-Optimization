package strategy;

import model.entity.Bot;
import model.map.Map;
import util.Vertex;

public interface Mover {
	public Vertex selectNextMove(final Map map, final Bot bot);
}
