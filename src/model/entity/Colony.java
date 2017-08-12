package model.entity;

import java.util.ArrayList;

import model.map.Map;
import strategy.*;

public class Colony {

	private Map map;
	private ArrayList<Bot> bots;
	private ColonyStrategy strategy = new RandomSystem();

	public Colony(final Map map, final int botCount, final Start start) {
		this.map = map;

		bots =  new ArrayList<>();
		for(int i = 0; i < botCount; i++){
			Bot b = new Bot(this, start.getRow(), start.getCol());
			bots.add(b);
		}
	}

	// METODI GETTER & SETTER
	public Map getMap() {return map;}
	public ColonyStrategy getStrategy(){ return strategy; }

	//MODIFICATORI
	public void update(){
		for(Bot b : bots) b.move();
	}

	public void add(){ for(Bot b : bots) b.add(); }
	public void remove(){ for(Bot b : bots) b.remove();}
}
