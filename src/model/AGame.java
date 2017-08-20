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
import model.map.AMap;
import util.Memento;
import util.Gloabal.Settings;

public abstract class AGame implements Observer{

	private Thread thread;
	private GameStatus gameStatus = GameStatus.NOTREADY;

	protected AStart start;
	protected ArrayList<AManhole> manholes = new ArrayList<>();
	protected ArrayList<AEnd> ends = new ArrayList<>();

	protected AColony colony;
	protected AMap map;

	private Stage stage;

	//Metodi che gestiscono lo stato della partita
	public final void init(){ init(new Stage()); }

	public final void init(final Stage stage){
		this.stage = stage;

		//Rimuovo tutti gli eventuali elementi disegnati
		this.removeNode();

		//Costruisco il gioco
		this.buildGame();

		//Aggiungo i nuovi elementi appena creati
		this.addNode();

		this.setStatus(GameStatus.READY);
	}

	public void restore(){
		if(this.stage == null) init();
		else init(this.stage);
	}

	public void start(){
		if(getStatus() != GameStatus.READY && getStatus() != GameStatus.PAUSED) return;
		this.setStatus(GameStatus.RUNNING);

	   thread = new Thread() { public void run() {

		   	while(getStatus() == GameStatus.RUNNING){
		   		Platform.runLater( () -> { colony.update(); });	//Tutti gli aggiornamenti dell'UI di fx vanno fatti in un thread di FX
		   		try { Thread.sleep(Settings.UPDATE_DELAY); } catch (InterruptedException e) {}
		   	}

		   	if(getStatus() == GameStatus.ENDED){
		   		System.out.println("Il labirinto è stato completato.");
		   	}

		} };// ).start();
		thread.start();
	}

	public void pause(){
		this.setStatus(GameStatus.PAUSED);
		try {if(thread !=null) thread.join(); } catch (InterruptedException e) { e.printStackTrace();}
	}
	public void restart(){ this.start();}
	public void end(){ pause(); this.setStatus(GameStatus.ENDED);}




	// METODI PER LA COSTRUZIONE DEL GIOCO
	protected void buildGame(){
		start = null;
		manholes = new ArrayList<>();
		ends = new ArrayList<>();

		this.map = makeMap(stage);
		this.colony = makeColony(map, Settings.BOT_NUMBER, start);
		this.addObservers();
	}
	protected abstract AMap makeMap(final Stage stage);
	protected abstract AColony makeColony(final AMap map, final int botCount, final AStart start);

	protected void addObservers(){
		for(AEnd e : ends) colony.addObserver(e);
		for(AManhole m : manholes) colony.addObserver(m);
		colony.addObserver(this);
	}

	// GETTERS & SETTERS
	protected void setStatus(final GameStatus gameStatus){ if(gameStatus != null) this.gameStatus = gameStatus; }
	public GameStatus getStatus(){ return gameStatus; }
	public Stage getStage(){ return stage;}
	public void setStart(final AStart start){this.start = start;}
	public void addEnd(final AEnd end){this.ends.add(end);}
	public void addManhole(final AManhole manhole){this.manholes.add(manhole);}


	public void addNode(){
		if(map != null) map.addNode();
		if(start != null) start.addNode();
		for(AManhole m : manholes) m.addNode();
		for(AEnd e : ends) e.addNode();
		if(colony != null) colony.addNode();
		Main.stage.sizeToScene();
	}

	public void removeNode(){
		if(map != null) map.removeNode();
		if(start != null) start.removeNode();
		for(AManhole m : manholes) m.removeNode();
		for(AEnd e : ends) e.removeNode();
		if(colony != null) colony.removeNode();
	}

	@Override
	public synchronized void update(Observable o, Object arg) {
		if(!(o instanceof AColony)) return;
		if(getStatus()!= GameStatus.ENDED && ((AColony) o).isEmpty()) this.end();
	}

	/* #########################################################################
	 * ###...................Implementazione del MEMENTO.....................###
	 * ######################################################################### */
	public class AGameMemento implements Memento{
		private AMap _map = map;
		private AColony _colony = colony;
		private AStart _start;
		private ArrayList<AManhole> _manholes = manholes;
		private ArrayList<AEnd> _ends = ends;
		private Stage _stage = stage;

		@Override public void restoreMemento(){
			AGame.this.removeNode();
			map = _map;
			colony = _colony;
			start = _start;
			manholes = _manholes;
			ends = _ends;
			stage = _stage;
			AGame.this.addNode();
		}
	}
	public Memento getMemento(){return new AGameMemento();}
	/*############################################################################*/

}
