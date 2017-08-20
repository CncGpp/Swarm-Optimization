package model.entity;

import java.util.ArrayList;
import java.util.Observable;

import javafx.scene.Node;
import model.Drawable;
import model.map.AMap;
import strategy.ColonyStrategy;
import util.Coord;
import util.Gloabal.Settings;

public abstract class AColony extends Observable implements Drawable{

	private AMap map;
	private ArrayList<ABot> bots;
	private ArrayList<ABot> toRemove = new ArrayList<>();
	private ColonyStrategy strategy;

	public AColony(final AMap map, final int botCount, final AStart start) {
		this.setMap(map);
		this.setStrategy(Settings.COLONY_STRATEGY.makeStrategy());

		bots =  new ArrayList<>();
		for(int i = 0; i < botCount; i++){
			addBot( makeBot(start.getCoordinate()) );
		}
		strategy.initialize(map);
	}

	protected abstract ABot makeBot(final Coord startPosition);
	protected void addBot(final ABot bot){
		try{
			if(bot == null) throw new IllegalArgumentException();
			bots.add(bot);
		} catch(IllegalArgumentException e){
			System.err.println("Inserimento nella colonai annullato: Non è possibile aggiungere Bot 'null'");
		}
	}

	protected abstract void moveBot();

	public final void update(){
		notifyObservers(bots);
		this.moveBot();
		this.setChanged();

		bots.removeAll(toRemove);
		toRemove.clear();
	}

	public void removeBot(final ABot bot){ toRemove.add(bot); }


	// METODI GETTER & SETTER
	public AMap getMap() {return map;}
	protected void setMap(final AMap map) {
		if(map == null) throw new IllegalArgumentException("La mappa della colonia non può essere 'null");
		else this.map = map;
	}

	public boolean isEmpty() {return bots.isEmpty();}

	public ColonyStrategy getStrategy(){ return strategy; }
	public void setStrategy(final ColonyStrategy strategy){
		if(strategy == null) throw new IllegalArgumentException("La strategia della colonia non può essere 'null");
		else this.strategy = strategy;
	}

	protected ArrayList<ABot> getBots(){return bots;}

	@Override
	public Node getNode() { return null; }
	@Override
	public void addNode(){ for(ABot b : bots) b.addNode(); }
	@Override
	public void removeNode(){ for(ABot b : bots) b.removeNode();}

}
