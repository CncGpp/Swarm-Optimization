package strategy;

import java.util.ArrayList;

import model.entity.ABot;
import model.map.AMap;
import util.Path;
import util.Vertex;

// ANT COLONY OPTIMIZATION
public class ACO extends AS {

	protected Path bestPath;
	private final double q0 = 0.02d;

	public ACO() {
		this.alpha = 4;
		this.beta = 2;
		this.phi = .05;
		this.rho = .80;
		this.tau = 0.2;
	}

	private double totalNeighborsGoodness(AMap map, ArrayList<Vertex> directions){
		double totalGoodness = 0;				//feromone TOTALE presente sui nodi considerati.
		for (Vertex n : directions)
			totalGoodness+=nodeGoodness(map, n);
		return totalGoodness;
	}

	private double nodeGoodness(final AMap map, final Vertex node){
		return Math.pow(map.getPheromoneAt(node.getRow(), node.getCol()) , alpha) * Math.pow( (1.0d)/node.getWeight(), beta);
	}

	private double mapDiameter(final AMap map){ return Math.sqrt(map.getRows()*map.getRows() + map.getCols()*map.getCols());}

	@Override
	public Vertex selectNextMove(final AMap map, final ABot bot) {

		ArrayList<Vertex> newDirections = new ArrayList<>();		//Array di nodi nell'intorno 3x3 DA VISITARE
		ArrayList<Vertex> oldDirections = new ArrayList<>();		//Array di nodi nell'intorno 3x3 GIA' VISITATI
		bot.getNeighbors(newDirections, oldDirections);

		ArrayList<Vertex> directions = newDirections;				//Inizialmente, i nodi da considerare sono quelli "nuovi" ma se ho
		if(newDirections.isEmpty()) directions = oldDirections; //Già visitato tutto l'intorno allora scelgo la direzione fra quelle visitate

		if(directions.isEmpty()) return null;				//Se non ci sono direzioni possibili allora ritorno.

		// CASO 0: Probabilità di esplorazione
		if(r.nextDouble() < q0){
			Vertex maxDir =  directions.get(0);
			for(Vertex v : directions) if(nodeGoodness(map, maxDir) < nodeGoodness(map, v)){ maxDir = v;}
			return maxDir;
		}

		final double totalPheromone = totalNeighborsGoodness(map, directions);
		//CASO 1: Se il feromone totale equivale a 0, allora scelgo una posizione casuale;
		if(totalPheromone == 0){ return directions.get(r.nextInt(directions.size())); }

		//CASO 2: Scelgo in maniera probabilistica la prossima posizione
		double max = totalPheromone*r.nextDouble();
		int i = 0;
		double sum = 0;

		while(sum < max){
			if(i == directions.size()) break;
			Vertex node = directions.get(i);
			sum+=nodeGoodness(map, node);
			i++;
		}

		if(i == 0) return directions.get(0);
		return directions.get(i-1);
	}

	@Override
	public void onlineUpdate(AMap map, ABot bot) {
		map.evaporatePheromoneAt(bot.getRow(), bot.getCol(), 1.0d-phi);
	}

	@Override
	public void offlineUpdate(AMap map, Path path) {
		map.evaporatePheromone(1.0d-rho);
		if(path.getLenght() < bestPath.getLenght() || bestPath.getPath().size() == 0) bestPath = path;

		for(Vertex n : bestPath.getPath()){
			map.dropPheromoneAt(n.getRow(), n.getCol(), mapDiameter(map)/bestPath.getLenght()*rho);
		}
	}


	@Override
	public void initialize(AMap map) {
		bestPath = new Path();
		map.setPheromone(tau);
	}

	@Override
	public String getStrategyName() { return "Ant Colony Optimization"; }

	@Override
	public String getStrategyDescriprion() {
		return  "E' stato uno dei primi tentativi di miglioramento dell'Ant System in cui il ferormone viene depositato "
			  + "solamente lungo il percorso migliore tovato fno a quel momeno. E' presente in oltre una variabile casuale "
			  + "'q0' che determina la probabilità della formica di muoversi non seguendo il ferormone in questo modo si "
			  + "favorisce l'esplorazione e la possibilità di ottenere soluzioni migliori. \n"
			  + "Tuttavia questo algoritmo per come è posto è estremamente inefficace per pianificare il percorso dei microbot"
			  + "soprattutto a causa delle botole possono falsare la ricerca del 'percorso migliore', in oltre quando i bot "
			  + "partono tutti insieme il percorso migliore coincide sempre con quello della prima formica che arriva.";
	}

	@Override
	public ColonyStrategy makeStrategy() {
		return new ACO();
	}

}
