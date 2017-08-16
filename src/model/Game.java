package model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import application.Main;
import javafx.application.Platform;
import model.entity.AColony;
import model.entity.AEnd;
import model.entity.AManhole;
import model.entity.AStart;
import model.entity.Bot;
import model.entity.Colony;
import model.map.AMap;
import model.map.Map;
import util.Memento;
import util.Chronometer;
import util.Gloabal.Controllers;
import util.Gloabal.Settings;

public class Game implements Observer{

	private GameStatus gameStatus = GameStatus.NOTREADY;

	private AStart start;
	private ArrayList<AManhole> manholes = new ArrayList<>();
	private ArrayList<AEnd> ends = new ArrayList<>();

	private AColony colony;
	private AMap map;

	public Game(){}

	/// METODI CHE GESTISCONO LO STATO DELLA PARTITA
	public void newGame(){ newGame(new Stage()); }

	public void newGame(final Stage stage){
		this.remove();
		// Inizializzo di nuovo gli elementi del gioco
		start = null;
		manholes = new ArrayList<>();
		ends = new ArrayList<>();

		//Carico la mappa
		map = new Map(this, stage);

		//Creo la colonia sulla mappa
		colony = new Colony(map, Settings.BOT_NUMBER, start);

		//AGGIUNGO GLI OBSERVER!
		for(AEnd e : ends) colony.addObserver(e);
		for(AManhole m : manholes) colony.addObserver(m);
		colony.addObserver(this);

		// Il gioco è pronto per partire!
		gameStatus = GameStatus.READY;
		this.add();
	}


	public void startGame(){
		if(gameStatus != GameStatus.READY && gameStatus != GameStatus.PAUSED) return;
		gameStatus = GameStatus.RUNNING;

	   ( new Thread() { public void run() {

		   	while(gameStatus == GameStatus.RUNNING){
		   		Platform.runLater( () -> { colony.update(); });	//Tutti gli aggiornamenti dell'UI di fx vanno fatti in un thread di FX
		   		try { Thread.sleep(Settings.UPDATE_DELAY); } catch (InterruptedException e) {}
		   	}

		   	if(gameStatus == GameStatus.ENDED){
		   		System.out.println("Il labirinto è stato completato.");
		   	}

		} } ).start();

	}

	public void pauseGame(){
		gameStatus = GameStatus.PAUSED;
		Chronometer.pause();
	}

	public void endGame(){
		pauseGame();
		gameStatus = GameStatus.ENDED;
		Controllers.bottomViewController.stageEnded();
	}


	// METODI GETTER & SETTER
	public GameStatus getStatus(){ return gameStatus; }
	public void setStart(final AStart start){this.start = start;}
	public void addEnd(final AEnd end){this.ends.add(end);}
	public void addManhole(final AManhole manhole){this.manholes.add(manhole);}


	public void add(){
		if(map != null) map.addNode();
		if(start != null) start.addNode();
		for(AManhole m : manholes) m.addNode();
		for(AEnd e : ends) e.addNode();
		if(colony != null) colony.addNode();
		Main.stage.sizeToScene();
	}

	public void remove(){
		if(map != null) map.removeNode();
		if(start != null) start.removeNode();
		for(AManhole m : manholes) m.removeNode();
		for(AEnd e : ends) e.removeNode();
		if(colony != null) colony.removeNode();
	}

	@Override
	public void update(Observable o, Object arg) {
		if(!(o instanceof Colony)) return;

		@SuppressWarnings("unchecked")
		ArrayList<Bot> bots = (ArrayList<Bot>)arg;
		if(bots.isEmpty()) this.endGame();
	}

	///////////////////////////
	public class GameMemento implements Memento{
		private AMap _map = map;
		private AColony _colony = colony;
		private AStart _start;
		private ArrayList<AManhole> _manholes = manholes;
		private ArrayList<AEnd> _ends = ends;
		@Override public void restoreMemento(){
			Game.this.remove();
			map = _map;
			colony = _colony;
			start = _start;
			manholes = _manholes;
			ends = _ends;
			Game.this.add();
		}
	}
	public Memento getMemento(){return new GameMemento();}
	///////////////////////////


}
















