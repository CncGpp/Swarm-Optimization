package model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import application.Main;
import javafx.application.Platform;
import model.entity.Bot;
import model.entity.Colony;
import model.entity.End;
import model.entity.Manhole;
import model.entity.Start;
import model.map.Map;
import util.Memento;
import util.Stage;
import util.Chronometer;
import util.Gloabal.Controllers;
import util.Gloabal.Settings;

public class Game implements Observer{

	private GameStatus gameStatus = GameStatus.NOTREADY;

	private Start start;
	private ArrayList<Manhole> manholes = new ArrayList<>();
	private ArrayList<End> ends = new ArrayList<>();

	private Colony colony;
	private Map map;

	public Game(){}

	/// METODI CHE GESTISCONO LO STATO DELLA PARTITA
	public void newGame(){ newGame(new Stage()); }

	public void newGame(final Stage stage){
		this.remove();
		// Inizializzo di nuovo gli elementi del gioco
		start = null;
		manholes.clear();
		ends.clear();

		//Carico la mappa
		map = new Map(this, stage);

		//Creo la colonia sulla mappa
		colony = new Colony(map, Settings.BOT_NUMBER, start);

		//AGGIUNGO GLI OBSERVER!
		for(End e : ends) colony.addObserver(e);
		for(Manhole m : manholes) colony.addObserver(m);
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
	public GameStatus getStatus(){ return gameStatus;}
	public void setStart(final Start start){this.start = start;}
	public void addEnd(final End end){this.ends.add(end);}
	public void addManhole(final Manhole manhole){this.manholes.add(manhole);}


	public void add(){
		if(gameStatus == GameStatus.NOTREADY) return;	//Significa che non è istanziato un cazzo
		if(map != null) map.add();
		if(start != null) start.add();
		for(Manhole m : manholes) m.add();
		for(End e : ends) e.add();
		if(colony != null) colony.add();
		Main.stage.sizeToScene();
	}

	public void remove(){
		if(gameStatus == GameStatus.NOTREADY) return;
		if(map != null) map.remove();
		if(start != null) start.remove();
		for(Manhole m : manholes) m.remove();
		for(End e : ends) e.remove();
		if(colony != null) colony.remove();
	}

	///////////////////////////
	public class GameMemento implements Memento{
		private Map _map = map;
		private Colony _colony = colony;
		private Start _start;
		private ArrayList<Manhole> _manholes = manholes;
		private ArrayList<End> _ends = ends;
		long currTime = Chronometer.getElapsedTime();
		@Override public void restoreMemento(){
			Game.this.remove();
			map = _map;
			colony = _colony;
			start = _start;
			manholes = _manholes;
			ends = _ends;
			Game.this.add();
			Chronometer.set(currTime);
		}
	}
	public Memento getMemento(){return new GameMemento();}
	///////////////////////////

	@Override
	public void update(Observable o, Object arg) {
		if(!(o instanceof Colony)) return;

		@SuppressWarnings("unchecked")
		ArrayList<Bot> bots = (ArrayList<Bot>)arg;
		if(bots.isEmpty()) this.endGame();
	}
}
















