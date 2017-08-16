package strategy;

import java.util.ArrayList;
import java.util.Random;

import model.entity.Bot;
import model.map.Map;
import util.Vertex;
import util.Path;

// True Random Ant System - Senza ferormone ne nulla
public class TRAS extends ColonyStrategy{

	protected Random r =  new Random();

	@Override
	public Vertex selectNextMove(Map map, Bot bot) {
		ArrayList<Vertex> newDirections = new ArrayList<>();		//Array di nodi nell'intorno 3x3 DA VISITARE
		ArrayList<Vertex> oldDirections = new ArrayList<>();		//Array di nodi nell'intorno 3x3 GIA' VISITATI
		bot.getNeighbors(newDirections, oldDirections);

		ArrayList<Vertex> directions = newDirections;				//Inizialmente, i nodi da considerare sono quelli "nuovi" ma se ho
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
