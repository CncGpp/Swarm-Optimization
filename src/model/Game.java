package model;

import java.util.ArrayList;

import javafx.application.Platform;
import model.entity.Colony;
import model.entity.End;
import model.entity.Start;
import model.map.Map;
import util.Memento;
import util.Timer;
import util.Gloabal.Controllers;
import util.Gloabal.Settings;

public class Game {

	private GameStatus gameStatus = GameStatus.NOTREADY;

	private Start start;
	private ArrayList<End> ends = new ArrayList<>();
	private Colony colony;

	private Map map;

	public Game(){}

	/// METODI CHE GESTISCONO LO STATO DELLA PARTITA
	public void createNewGame(){
		ends.clear();
		map = new Map(this);
		colony = new Colony(map, 20, start);
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
		   		Controllers.bottomViewController.updateTimer(Timer.getElapsedTime());
		   	}

		} } ).start();

	}

	public void pauseGame(){
		gameStatus = GameStatus.PAUSED;
		Timer.pause();
	}


	// METODI GETTER & SETTER
	public GameStatus getStatus(){ return gameStatus;}
	public void setStart(final Start start){this.start = start;}
	public void addEnd(final End end){this.ends.add(end);}


	public void add(){
		map.add();
		start.add();
		for(End e : ends) e.add();
		colony.add();
	}

	public void remove(){
		map.remove();
		start.remove();
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
}
















