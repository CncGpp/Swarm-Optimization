package strategy;

import model.map.Map;

public interface PheromoneInitializer {
	public void initialize(final Map map);
}
