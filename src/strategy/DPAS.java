package strategy;

import model.entity.ABot;
import model.map.AMap;
import util.Vertex;
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
	public void onlineUpdate(AMap map, ABot bot) {
		map.evaporatePheromoneAt(bot.getRow(), bot.getCol(), 1-rho);
	}

	@Override
	public void offlineUpdate(AMap map, Path path) {
		map.evaporatePheromone(1 - phi);

		double sum = 0;
		for (Vertex node : path.getPath()) {
			map.dropPheromoneAt(node.getRow(), node.getCol(), Math.log1p(sum/path.getLenght())/Math.log(Q));
			sum+=node.getWeight();
		}
	}

	@Override
	public void initialize(AMap map) {
		Q = Math.log10(mapDiameter(map))*(alpha+beta);
		map.setPheromone(tau);
	}

	protected double mapDiameter(final AMap map){ return Math.sqrt(map.getRows()*map.getRows() + map.getCols()*map.getCols());}



	@Override
	public String getStrategyName() {
		return "Decreasing Path Ant-System";
	}

	@Override
	public String getStrategyDescriprion() {
		return  "nmncmn sfdlhsd,fsd fl jshdfjshdfj jsdhf sdjhf sj dhfhjjh jsoipoi poie opadpo asdj "
			  + "Non so cosa scrivere blabla poi si vede jsodfho sjdhfksdh lhflsdjhfs lore ipsum";
	}

	@Override
	public ColonyStrategy makeStrategy() { return new DPAS(); }



}