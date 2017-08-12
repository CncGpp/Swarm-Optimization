package strategy;

import java.util.ArrayList;

import model.entity.Bot;
import model.map.Map;
import util.Node;

public interface PheromoneUpdater {
	public void onlineUpdate(final Map map, final Bot bot);
	public void offlineUpdate(final Map map, ArrayList<Node> path);
}
