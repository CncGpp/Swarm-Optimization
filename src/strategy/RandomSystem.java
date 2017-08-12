package strategy;

import java.util.ArrayList;
import java.util.Random;

import model.entity.Bot;
import model.map.Map;
import util.Node;
import util.Path;

public class RandomSystem extends ColonyStrategy{
	private Random r = new Random();

	@Override
	public Node selectNextMove(Map map, Bot bot) {
		ArrayList<Node> neighbors = bot.getNeighbors();

		if(neighbors.size() == 0) return new Node(bot.getRow(), bot.getCol(), 0);
		else return neighbors.get(r.nextInt( neighbors.size() ));
	}

	@Override
	public void onlineUpdate(Map map, Bot bot) {
		map.dropPheromoneAt(bot.getRow(), bot.getCol(), 0.1d);
	}

	@Override
	public void offlineUpdate(Map map, final Path path) {
		// TODO Auto-generated method stub
	}

	@Override
	public void initialize(Map map) {
		// TODO Auto-generated method stub
	}

}
