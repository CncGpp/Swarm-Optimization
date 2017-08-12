package model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import model.entity.Bot;
import model.entity.Colony;
import model.entity.End;
import model.entity.Manhole;
import model.entity.Start;
import model.map.Map;
import util.Memento;
import util.Timer;
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
	public void createNewGame(){
		manholes.clear();
		ends.clear();
		map = new Map(this);
		colony = new Colony(map, Settings.BOT_NUMBER, start);

		//AGGIUNGO GLI OBSERVER!
		for(End e : ends) colony.addObserver(e);
		for(Manhole m : manholes) colony.addObserver(m);
		colony.addObserver(this);

		Timer.reset();
		gameStatus = GameStatus.READY;
	}


	public void startGame(){
		if(gameStatus != GameStatus.READY && gameStatus != GameStatus.PAUSED) return;
		gameStatus = GameStatus.RUNNING;

		Timer.start();
	   ( new Thread() { public void run() {

		   	while(gameStatus == GameStatus.RUNNING){
		   		Platform.runLater( () -> { colony.update(); });	//Tutti gli aggiornamenti dell'UI di fx vanno fatti in un thread di FX
		   		Platform.runLater( () -> { Controllers.bottomViewController.updateTimer(Timer.getElapsedTime());});
		   		try { Thread.sleep(Settings.UPDATE_DELAY); } catch (InterruptedException e) {}
		   	}

		   	if(gameStatus == GameStatus.ENDED){
		   		//SUBMIT SCORE
		   		//Controllers.bottomViewController.updateTimer(Timer.getElapsedTime());
		   	}

		} } ).start();

	}

	public void pauseGame(){
		gameStatus = GameStatus.PAUSED;
		Timer.pause();
	}

	public void endGame(){
		pauseGame();
		gameStatus = GameStatus.ENDED;
	}


	// METODI GETTER & SETTER
	public GameStatus getStatus(){ return gameStatus;}
	public void setStart(final Start start){this.start = start;}
	public void addEnd(final End end){this.ends.add(end);}
	public void addManhole(final Manhole manhole){this.manholes.add(manhole);}


	public void add(){
		map.add();
		start.add();
		for(Manhole m : manholes) m.add();
		for(End e : ends) e.add();
		colony.add();
	}

	public void remove(){
		map.remove();
		start.remove();
		for(Manhole m : manholes) m.remove();
		for(End e : ends) e.remove();
		colony.remove();
	}

	///////////////////////////
	public class GameMemento implements Memento{
		Map _map = map;
		Colony _colony = colony;
		long currTime = Timer.getElapsedTime();
		@Override public void restoreMemento(){
			map = _map;
			colony = _colony;
			Timer.set(currTime);
			Controllers.bottomViewController.updateTimer(currTime);
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
















