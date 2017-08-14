package strategy;

import java.util.ArrayList;
import java.util.Random;

import model.entity.Bot;
import model.map.Map;
import util.Node;
import util.Path;

// True Random Ant System - Senza ferormone ne nulla
public class TRAS extends ColonyStrategy{

	protected Random r =  new Random();

	@Override
	public Node selectNextMove(Map map, Bot bot) {
		ArrayList<Node> newDirections = new ArrayList<>();		//Array di nodi nell'intorno 3x3 DA VISITARE
		ArrayList<Node> oldDirections = new ArrayList<>();		//Array di nodi nell'intorno 3x3 GIA' VISITATI
		bot.getNeighbors(newDirections, oldDirections);

		ArrayList<Node> directions = newDirections;				//Inizialmente, i nodi da considerare sono quelli "nuovi" ma se ho
		if(newDirections.isEmpty()) directions = oldDirections; //Già visitato tutto l'intorno allora scelgo la direzione fra quelle visitate

		if(directions.isEmpty()) return null;					//Se non ci sono direzioni possibili allora ritorno.
		else
		return directions.get(r.nextInt(directions.size()));	//Altrimenti ritorno una posizione causale
	}

	@Override
	public void onlineUpdate(Map map, Bot bot) {}
	@Override
	public void offlineUpdate(Map map, Path path) {}
	@Override
	public void initialize(Map map) { map.setPheromone(0); }

}
