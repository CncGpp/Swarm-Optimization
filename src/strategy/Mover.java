package strategy;

import model.entity.Bot;
import model.map.Map;
import util.Node;

public interface Mover {
	public Node selectNextMove(final Map map, final Bot bot);
}
