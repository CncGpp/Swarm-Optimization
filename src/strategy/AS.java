package strategy;

import java.util.ArrayList;
import java.util.Random;

import model.entity.Bot;
import model.map.Map;
import util.Node;
import util.Path;

public class AS extends ColonyStrategy{

	private Random r = new Random();

	protected double alpha = 6;
	protected double beta = 2;

	protected double phi = 0.9;
	protected double rho = 0.75;

	protected double tau = .2;


	private double totalNeighborsGoodness(Map map, ArrayList<Node> directions){
		double totalGoodness = 0;				//feromone TOTALE presente sui nodi considerati.
		for (Node n : directions)
			totalGoodness+=nodeGoodness(map, n);
		return totalGoodness;
	}

	private double nodeGoodness(final Map map, final Node node){
		return Math.pow(map.getPheromoneAt(node.getRow(), node.getCol()) , alpha) * Math.pow( (1.0d)/node.getWeight(), beta);
	}

	private double mapDiameter(final Map map){ return Math.sqrt(map.getRows()*map.getRows() + map.getCols()*map.getCols());}


	@Override
	public Node selectNextMove(final Map map, final Bot bot) {

		ArrayList<Node> newDirections = new ArrayList<>();		//Array di nodi nell'intorno 3x3 DA VISITARE
		ArrayList<Node> oldDirections = new ArrayList<>();		//Array di nodi nell'intorno 3x3 GIA' VISITATI
		bot.getNeighbors(newDirections, oldDirections);

		ArrayList<Node> directions = newDirections;				//Inizialmente, i nodi da considerare sono quelli "nuovi" ma se ho
		if(newDirections.isEmpty()) directions = oldDirections; //Già visitato tutto l'intorno allora scelgo la direzione fra quelle visitate

		if(directions.isEmpty()) return null;				//Se non ci sono direzioni possibili allora ritorno.


		final double totalPheromone = totalNeighborsGoodness(map, directions);
		//CASO 1: Se il feromone totale equivale a 0, allora scelgo una posizione casuale;
		if(totalPheromone == 0){ return directions.get(r.nextInt(directions.size())); }

		//CASO 2: Scelgo in maniera probabilistica la prossima posizione
		double max = totalPheromone*r.nextDouble();
		int i = 0;
		double sum = 0;

		while(sum <= max){
			if(i == directions.size()) break;
			Node node = directions.get(i);
			sum+=nodeGoodness(map, node);
			i++;
		}

		return directions.get(i-1);
	}

	@Override
	public void onlineUpdate(Map map, Bot bot) {
		map.evaporatePheromoneAt(bot.getRow(), bot.getCol(), phi);
	}

	@Override
	public void offlineUpdate(Map map, Path path) {
		map.evaporatePheromone(rho);

		for(Node n : path.getPath()){
			map.dropPheromoneAt(n.getRow(), n.getCol(), mapDiameter(map)/path.getLenght());
		}
	}
	@Override
	public void initialize(Map map) {
		map.setPheromone(tau);
	}

}
