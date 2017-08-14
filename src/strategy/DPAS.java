package strategy;

import model.entity.Bot;
import model.map.Map;
import util.Node;
import util.Path;

//Decreasing Pheromone Ant System
public class DPAS extends AS{

	protected double alpha = 10;
	protected double beta = 2;
	protected double rho = .1;
	protected double phi = .30;
	protected double tau = .2;
	protected double Q = 3;

	//@Override public ColonyStartType getColonyStartType() { return ColonyStartType.MULTIPLE; }

	@Override
	public void onlineUpdate(Map map, Bot bot) {
		map.evaporatePheromoneAt(bot.getRow(), bot.getCol(), 1-rho);
	}

	@Override
	public void offlineUpdate(Map map, Path path) {
		map.evaporatePheromone(1 - phi);

		double sum = 0;
		for (Node node : path.getPath()) {
			map.dropPheromoneAt(node.getRow(), node.getCol(), Math.log1p(sum/path.getLenght())/Math.log(Q));
			sum+=node.getWeight();
		}
	}

	@Override
	public void initialize(Map map) {
		Q = Math.log10(mapDiameter(map))*(alpha+beta);
		map.setPheromone(tau);
	}

	protected double mapDiameter(final Map map){ return Math.sqrt(map.getRows()*map.getRows() + map.getCols()*map.getCols());}
}