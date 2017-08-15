package strategy;

import model.entity.Bot;
import model.map.Map;
import util.Coord;
import util.Node;
import util.Path;

public class AtanAS extends AS{

	protected double lambda = 2.0d;
	protected double omega = 1.5d;

	public AtanAS() {
		super.alpha = 10;
		super.beta = 2;
	}

	protected double func(final double pLevel, final double k){
		//Il decremento � minore per valori alti di feromone e maggiore per valori pi� bassi cos� si favorisce il concentramento
		//livello attuale         decremento : \in [0:1)
		//      |						|
		//      V						V
		return pLevel * Math.atan(pLevel + k)/(Math.PI/2);	// Maggiore � il k minore � il decremento!
	}

	@Override
	public void onlineUpdate(Map map, Bot bot) {
		final Coord pos = bot.getCoordinate();
		double pLevel = map.getPheromoneAt(pos.getRow(), pos.getCol());
		map.setPheromoneAt(pos.getRow(), pos.getCol(), func(pLevel, lambda));
	}

	@Override
	public void offlineUpdate(Map map, Path path) {

		double sum = 0;
		for (Node n : path.getPath()) {
			map.dropPheromoneAt(n.getRow(), n.getCol(), sum/path.getLenght());
			sum+=n.getWeight();
		}

		for(int i = 0; i < map.getRows(); i++){
			for(int j = 0; j < map.getCols(); j++){
				double pLevel = map.getPheromoneAt(i, j);
				map.setPheromoneAt(i, j, func(pLevel, omega));
			}
		}

	}

	@Override
	public void initialize(Map map) {
		super.initialize(map);
	}

}
