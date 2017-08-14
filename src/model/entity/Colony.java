package model.entity;

import java.util.ArrayList;
import java.util.Observable;

import model.map.Map;
import strategy.*;

public class Colony extends Observable{

	private Map map;
	private ArrayList<Bot> bots;
	private ArrayList<Bot> toRemove = new ArrayList<>();
	private ColonyStrategy strategy = new LDPAS();

	public Colony(final Map map, final int botCount, final Start start) {
		this.map = map;

		bots =  new ArrayList<>();
		for(int i = 0; i < botCount; i++){
			Bot b = new Bot(this, start.getRow(), start.getCol());
			bots.add(b);
		}
		strategy.initialize(map);
	}

	// METODI GETTER & SETTER
	public Map getMap() {return map;}
	public ColonyStrategy getStrategy(){ return strategy; }

	//MODIFICATORI
	public void update(){
		notifyObservers(bots);
		for(Bot b : bots) b.move();
		this.setChanged();

		bots.removeAll(toRemove);
		toRemove.clear();
	}

	public void hasLeft(Bot b){
		toRemove.add(b);
	}

	public void add(){ for(Bot b : bots) b.add(); }
	public void remove(){ for(Bot b : bots) b.remove();}
}
