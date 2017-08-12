package strategy;

import java.util.ArrayList;
import java.util.Random;

import model.entity.Bot;
import model.map.Map;
import util.Coord;
import util.Node;

public class RandomSystem extends ColonyStrategy{
	private Random r = new Random();

	@Override
	public Coord selectNextMove(Map map, Bot bot) {
		ArrayList<Node> neighbors = bot.getNeighbors();

		if(neighbors.size() == 0) return bot.getCoordinate();
		else return neighbors.get(r.nextInt( neighbors.size() ));
	}

	@Override
	public void onlineUpdate(Map map, Bot bot) {
		map.dropPheromoneAt(bot.getRow(), bot.getCol(), 0.02d);
	}

	@Override
	public void offlineUpdate(Map map, ArrayList<Node> path) {
		// TODO Auto-generated method stub
	}

	@Override
	public void initialize(Map map) {
		// TODO Auto-generated method stub
	}

}
