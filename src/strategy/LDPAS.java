package strategy;

import model.entity.Bot;
import model.map.Map;
import util.Vertex;
import util.Path;


// LINEAR DECREASING PATH ANT SYSTEM
public class LDPAS extends DPAS{

	public LDPAS() {
		this.alpha = 16.0d;
		this.beta = 1.0d;
		this.rho = 0.1;
	}

	@Override
	public void onlineUpdate(Map map, Bot bot) {
		super.onlineUpdate(map, bot);
	}

	@Override
	public void offlineUpdate(Map map, final Path path) {
		map.evaporatePheromone(1 - phi);

		double sum = 0;
		for (Vertex node : path.getPath()) {
			map.dropPheromoneAt(node.getRow(), node.getCol(), sum/(Q * path.getLenght()));
			sum+=node.getWeight();
		}
	}

	@Override
	public void initialize(Map map) {
		Q = mapDiameter(map)/(alpha+beta);
		phi = alpha/mapDiameter(map);
		map.setPheromone(tau);
	}

}