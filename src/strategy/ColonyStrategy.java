package strategy;

public abstract class ColonyStrategy implements Mover, PheromoneUpdater, PheromoneInitializer{
	public abstract String getStrategyName();
	public abstract String getStrategyDescriprion();
	public abstract ColonyStrategy makeStrategy();
}
