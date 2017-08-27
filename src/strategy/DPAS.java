package strategy;

import model.entity.ABot;
import model.map.AMap;
import util.Vertex;
import util.Path;

//Decreasing Pheromone Ant System
public class DPAS extends AS{

	protected double Q = 3;

	public DPAS() {
		alpha = 10;
		beta = 3;
	    rho = .1;
		phi = .20;
		tau = .2;
	}

	@Override
	public void onlineUpdate(AMap map, ABot bot) {
		map.evaporatePheromoneAt(bot.getRow(), bot.getCol(), 1-rho);
	}

	@Override
	public void offlineUpdate(AMap map, Path path) {
		map.evaporatePheromone(1 - phi);

		double sum = 0;
		for (Vertex node : path.getPath()) {
			map.dropPheromoneAt(node.getRow(), node.getCol(), Math.log1p(sum/path.getLenght())/Q);
			sum+=node.getWeight();
		}
	}

	@Override
	public void initialize(AMap map) {
		Q = Math.log1p(mapDiameter(map)/alpha);
		map.setPheromone(tau);
	}

	protected double mapDiameter(final AMap map){ return Math.sqrt(map.getRows()*map.getRows() + map.getCols()*map.getCols());}

	@Override
	public String getStrategyName() {
		return "Decreasing Path Ant-System";
	}

	@Override
	public String getStrategyDescriprion() {
		return  "Un'evoluzione dell'Ant-System. Ogni volta che una soluzione è stata trovata Il "
			  + "ferormone viene distribuito descrescentemente a retroso sul percorso effettuato dalla "
		      + "formica. Le fasi di aggiornamento, online resta invariata, mentre la fase offline è effettuata utilizzando "
		      + "la funzione logaritmo ottenendo un tasso di decadenza logaritmico, favorendo i valori alti a discapito "
		      + "di quelli bassi.\n"
			  + "In questo modo si favorisce il concetramento di feromone intorno all'uscita che decresce allontanandosi "
			  + "portando i microbot 'risalire' il feromone fino all'uscita.";
	}

	@Override
	public ColonyStrategy makeStrategy() { return new DPAS(); }



}