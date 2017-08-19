package strategy;

import java.util.ArrayList;
import java.util.Random;

import model.entity.ABot;
import model.map.AMap;
import util.Vertex;
import util.Path;

// True Random Ant System - Senza ferormone ne nulla
public class TRAS extends ColonyStrategy{

	protected Random r =  new Random();

	@Override
	public Vertex selectNextMove(AMap map, ABot bot) {
		ArrayList<Vertex> newDirections = new ArrayList<>();		//Array di nodi nell'intorno 3x3 DA VISITARE
		ArrayList<Vertex> oldDirections = new ArrayList<>();		//Array di nodi nell'intorno 3x3 GIA' VISITATI
		bot.getNeighbors(newDirections, oldDirections);

		ArrayList<Vertex> directions = newDirections;			//Inizialmente, i nodi da considerare sono quelli "nuovi" ma se ho
		if(newDirections.isEmpty()) directions = oldDirections; //Già visitato tutto l'intorno allora scelgo la direzione fra quelle visitate

		if(directions.isEmpty()) return null;					//Se non ci sono direzioni possibili allora ritorno.
		else
		return directions.get(r.nextInt(directions.size()));	//Altrimenti ritorno una posizione causale
	}

	@Override
	public void onlineUpdate(AMap map, ABot bot) {}
	@Override
	public void offlineUpdate(AMap map, Path path) {}
	@Override
	public void initialize(AMap map) { map.setPheromone(0); }

	@Override
	public String getStrategyName() { return "True Random"; }

	@Override
	public String getStrategyDescriprion() {
		return  "In questa strategia non viene applicato nessun algoritmo basato sulle formiche "
			  + "i movimenti dei microbot sono pseudocasuali e non dipendenti dal feromone. "
			  + "Questa strategia piò essere utilizzata come base di paragone.";
	}

	@Override
	public ColonyStrategy makeStrategy() {return new TRAS(); }

}
