package strategy;

import model.entity.Bot;
import model.map.Map;
import util.Path;

public interface PheromoneUpdater {
	public void onlineUpdate(final Map map, final Bot bot);
	public void offlineUpdate(final Map map, final Path path);
}
