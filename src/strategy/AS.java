package strategy;

import java.util.ArrayList;
import java.util.Random;

import model.entity.ABot;
import model.map.AMap;
import util.Vertex;
import util.Path;

public class AS extends ColonyStrategy{

	private Random r = new Random();

	protected double alpha = 8;
	protected double beta = 2;

	protected double phi = 0.95;
	protected double rho = 0.10;

	protected double tau = .2;


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
		//FIXME: Porcodio perchè non funziona? chi lo sa...
		if(i == 0) return directions.get(0);
		return directions.get(i-1);
	}

	@Override
	public void onlineUpdate(AMap map, ABot bot) {
		map.evaporatePheromoneAt(bot.getRow(), bot.getCol(), phi);
	}

	@Override
	public void offlineUpdate(AMap map, Path path) {
		map.evaporatePheromone(1.0d - rho);
		map.dropPheromone(tau*rho);

		for(Vertex n : path.getPath()){
			map.dropPheromoneAt(n.getRow(), n.getCol(), mapDiameter(map)/path.getLenght());
		}
	}
	@Override
	public void initialize(AMap map) {
		map.setPheromone(tau);
	}

}
