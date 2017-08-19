package strategy;

import model.entity.ABot;
import model.map.AMap;
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
	public void onlineUpdate(AMap map, ABot bot) {super.onlineUpdate(map, bot); }

	@Override
	public void offlineUpdate(AMap map, final Path path) {
		map.evaporatePheromone(1 - phi);

		double sum = 0;
		for (Vertex node : path.getPath()) {
			map.dropPheromoneAt(node.getRow(), node.getCol(), sum/(Q * path.getLenght()));
			sum+=node.getWeight();
		}
	}

	@Override
	public void initialize(AMap map) {
		Q = mapDiameter(map)/(alpha+beta);
		phi = alpha/mapDiameter(map);
		map.setPheromone(tau);
	}

	@Override
	public String getStrategyName() { return "Linear Decreasing Path Ant-System"; }

	@Override
	public String getStrategyDescriprion() {
		return  "Una variante del Decreasing Path Ant-System. Ogni volta che una soluzione è stata trovata Il "
			  + "ferormone viene distribuito descrescentemente e linearmente a retroso sul percorso effettuato dalla "
		      + "formica. Le fasi di aggiornamento, online resta invariata, mentre la fase offline è effettuata utilizzando "
		      + "una funzione lineare ottenendo un tasso di decadenza lineare."
			  + "In questo modo si favorisce il concetramento di feromone intorno all'uscita che decresce allontanandosi "
			  + "portando i microbot 'risalire' il feromone fino all'uscita.";
	}

	@Override
	public ColonyStrategy makeStrategy() { return new LDPAS(); }

}