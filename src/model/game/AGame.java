package model.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import application.Main;
import javafx.application.Platform;
import model.entity.AColony;
import model.entity.AColony.AColonyMemento;
import model.entity.AEnd;
import model.entity.AManhole;
import model.entity.AStart;
import model.map.AMap;
import model.map.AMap.AMapMemento;
import util.Coord;
import util.Global.Settings;
import util.Memento;

/**
 * La classe {@code AGame} modella una partita.
 * <p> Essa descrive le caratteristiche, funzionamento e comportamenti base che deve avere il gioco, come ad esempio
 * La mappa con relative entrate e uscite, la colonia e le botole.</p>
 * <p> La classe � stata implementata seguendo i principi SOLID essa dunque dipende dalle astrazioni e dunque pu� essere
 * estesa e modificata con facilit�. In oltre attraverso l'utilizzo del pattern 'Factory Method' la classe mette a disposizione
 * un'interfaccia per la creazione di della mappa e della colonia senza specificarne l'implementazione. In questo modo
 * � possibile lasciare alle sottoclassi la scelta di quale oggetto istanzire.</p>
 */
public abstract class AGame implements Observer, Memento<AGame.AGameMemento>{

	/** Il thread attraverso il quale verr� eseguito il gioco, in modo tale da non bloccare il thread principale */
	private Thread thread;

	/** Lo status attuale del gioco */
	private GameStatus gameStatus = GameStatus.NOTREADY;

	/** L'entit� di partenza da dove il gioco comincia, corrisponde all'entrata sulla mappa.*/
	protected AStart start;

	/** Le botole presenti sulla mappa */
	protected ArrayList<AManhole> manholes = new ArrayList<>();

	/** Le uscite presenti sulla mappa */
	protected ArrayList<AEnd> ends = new ArrayList<>();

	/** La colonia di microbots */
	protected AColony colony;

	/** La mappa di gioco */
	protected AMap map;

	/** Lo stage attuale del gioco */
	private Stage stage;

	/* 					+----------------------------------------------------------------------------+
	 * 					|        METODI CHE GESTISCONO LO STATO E FUNZIONAMENTO DELLA PARTITA        |
	 * 					+----------------------------------------------------------------------------+                */

	/**
	 * Inizializza una nuova partita partendo dallo stage iniziale.
	 * <p> Dopo la chiamata di questo metodo il gioco si trova nello stato {@code GameStatus.READY}</p>
	 */
	public final void init(){ init(new Stage()); }

	/**
	 * Inizializza una nuova partita partendo da uno stage fornito in input.
	 * <p> Dopo la chiamata di questo metodo il gioco si trova nello stato {@code GameStatus.READY}</p>
	 *
	 * @param stage lo stage dal quale si vuole cominciare la partita
	 */
	protected final void init(final Stage stage){
		this.stage = stage;

		//Rimuovo tutti gli eventuali elementi disegnati
		this.removeNode();

		//Costruisco il gioco
		this.buildGame();

		//Aggiungo i nuovi elementi appena creati
		this.addNode();

		this.setStatus(GameStatus.READY);
	}

	/**
	 *  Permette il ripristino della partita, inizializzandola a partire dallo stage corrente.
	 *  Utile quando il gioco viene ripristinato ed � necessario 'ricaricare' il gioco.
	 *  <p> Dopo la chiamata di questo metodo il gioco si trova nello stato {@code GameStatus.READY}</p>
	 */
	public void restore(){
		if(this.stage == null) init();
		else init(this.stage);
	}

	/**
	 * Avvia o riprende una partita assicurandosi che il gioco si trovi in uno stato idoneo.
	 * <p> Dopo la chiamata con successo di questo metodo il gioco si trova nello stato {@code GameStatus.RUNNING}</p>
	 */
	public void start(){
		if(getStatus() != GameStatus.READY && getStatus() != GameStatus.PAUSED) return;
		this.setStatus(GameStatus.RUNNING);

	   thread = new Thread() { public void run() {

		   	while(getStatus() == GameStatus.RUNNING){
		   		Platform.runLater( () -> { colony.update(); });	//Tutti gli aggiornamenti dell'UI di fx vanno fatti in un thread di FX
		   		try { Thread.sleep(Settings.UPDATE_DELAY); } catch (InterruptedException e) {}
		   	}

		} };
		thread.start();
	}

	/**
	 * Mette in pausa il gioco.
	 * <p> Dopo la chiamata di questo metodo il gioco si trova nello stato {@code GameStatus.PAUSED}</p>
	 */
	public void pause(){
		if(this.gameStatus == GameStatus.NOTREADY) return;
		this.setStatus(GameStatus.PAUSED);
		try {if(thread !=null) thread.join(); } catch (InterruptedException e) { e.printStackTrace();}
	}

	/**
	 * Riprende il gioco.
	 * <p> Dopo la chiamata con successo di questo metodo il gioco si trova nello stato {@code GameStatus.RUNNING}</p>
	 */
	public void restart(){ this.start();}

	/**
	 * Mette in pausa e subito dipo termina il gioco.
	 * <p> Dopo la chiamata di questo metodo il gioco si trova nello stato {@code GameStatus.PAUSED}</p>
	 */
	public void end(){ pause(); this.setStatus(GameStatus.ENDED);}



	/* 							+--------------------------------------------------------------+
	 * 							|        METODI CHE GESTISCONO LA COSTRUZIONE DEL GIOCO        |
	 * 							+--------------------------------------------------------------+          	          */

	/**
	 * Permette la creazione e la costruzione del gioco attraverso i factory Method.
	 */
	protected void buildGame(){
		start = null;
		manholes = new ArrayList<>();
		ends = new ArrayList<>();

		this.map = makeMap(stage);
		this.colony = makeColony(map, Settings.BOT_NUMBER, start);
		this.addObservers();
	}

	/**
	 * Factory Method che permette di costruire la mappa di gioco.
	 * <p> Il metodo � astratto lasciando dunque la sua implementazione alle sottoclassi di {@code AGame}.</p>
	 *
	 * @param stage lo stage relativo alla mappa che si vuole fabbricare
	 * @return la mappa di gioco.
	 */
	protected abstract AMap makeMap(final Stage stage);

	/**
	 * Factory Method che permette di costruire la Colonia.
	 * <p> Il metodo � astratto lasciando dunque la sua implementazione alle sottoclassi di {@code AGame}.</p>
	 *
	 * @param map la mappa di gioco sulla quale la colonia dovr� trovarsi
	 * @param botCount il numero di microbot che apparterranno alla colonia
	 * @param start il punto di partenza della colonia
	 * @return La colonia.
	 *
	 * @see AMap
	 * @see AColony
	 * @see AStart
	 */
	protected abstract AColony makeColony(final AMap map, final int botCount, final AStart start);

	protected abstract AStart makeStart(final AMap map, final Coord coord);
	protected abstract AEnd makeEnd(final AMap map, final Coord coord);
	protected abstract AManhole makeManhole(final AMap map, final Coord coord);

	/**
	 * Aggiunge gli observer delle uscite, delle botole e del gioco alla colonia
	 */
	protected void addObservers(){
		for(AEnd e : ends) colony.addObserver(e);
		for(AManhole m : manholes) colony.addObserver(m);
		colony.addObserver(this);
	}


	/* 									+--------------------------------------+
	 * 									|        METODI GETTER & SETTERS       |
	 * 									+--------------------------------------+          	                          */

	/**
	 * Metodo setter per settare lo stato di gioco
	 *
	 * @param gameStatus il nuovo stato di gioco.
	 */
	protected void setStatus(final GameStatus gameStatus){ if(gameStatus != null) this.gameStatus = gameStatus; }

	/**
	 * Metodo getter per ottenere lo stato attuale del gioco.
	 *
	 * @return lo status
	 */
	public GameStatus getStatus(){ return gameStatus; }

	/**
	 * Metodo getter per ottenere lo stage attuale
	 *
	 * @return the stage
	 */
	public Stage getStage(){ return stage;}

	/**
	 * Metodo setter per settare lo start
	 * <p> Crea la nuova entit� {@code AStart} usando il relativo factory Method</p>
	 * @param map la mappa di appartenenza
	 * @param startCoord le coordinate dell'entrate
	 * @see AStart
	 */
	public void setStart(final AMap map, final Coord startCoord){
		if(startCoord == null) throw new IllegalArgumentException("Le coordinate di Start non possono essere null.");
		this.start = makeStart(map, startCoord);
	}

	/**
	 * Permette di aggiungere un'ulteriore uscita alla lista di uscite del gioco.
	 * <p> Crea la nuova entit� {@code AEnd} da aggiungere usando il relativo factory Method</p>
	 * @param map la mappa di appartenenza
	 * @param endCoord le coordinate dell'uscita
	 */
	public void addEnd(final AMap map, final Coord endCoord){
		if(endCoord == null) throw new IllegalArgumentException("Le coordinate dell'usicta non possono essere null.");
		this.ends.add(makeEnd(map, endCoord));
	}

	/**
	 * Permette di aggiungere un'ulteriore botola alla lista di botole del gioco.
	 * <p> Crea la nuova entit� {@code AMahole} da aggiungere usando il relativo factory Method</p>
	 * @param map la mappa di appartenenza
	 * @param manholeCoord le coordinate della botola
	 */
	public void addManhole(final AMap map, final Coord manholeCoord){
		if(manholeCoord == null) throw new IllegalArgumentException("Le coordinate dell'botola non possono essere null.");
		this.manholes.add(makeManhole(map, manholeCoord));
	}


	/* 									+------------------------------------------------+
	 * 									|        METODI DI DISEGNO E AGGIORNAMENTO       |
	 * 									+------------------------------------------------+          	              */

	/**
	 * Disegna la partita a schermo aggiungendo tutti i nodi alla View della scena.
	 */
	public void addNode(){
		if(map != null) map.addNode();
		if(start != null) start.addNode();
		for(AManhole m : manholes) m.addNode();
		for(AEnd e : ends) e.addNode();
		if(colony != null) colony.addNode();
		Main.resizeGameStage();
	}

	/**
	 * Cancella la partita dallo schermo rimuovendo tutti i nodi dalla View della scena.
	 */
	public void removeNode(){
		if(map != null) map.removeNode();
		if(start != null) start.removeNode();
		for(AManhole m : manholes) m.removeNode();
		for(AEnd e : ends) e.removeNode();
		if(colony != null) colony.removeNode();
	}

	/**
	 * Quando tutti i microbot appartenenti alla colonia escono allora la partita finisce
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public synchronized void update(Observable o, Object arg) {
		if(!(o instanceof AColony)) return;
		if(getStatus()!= GameStatus.ENDED && ((AColony) o).isEmpty()) this.end();
	}





	/* 									+--------------------------------------------------+
	 * 									|        IMPLEMENTAZIONE DEL PATTERN MEMENTO       |
	 * 									+--------------------------------------------------+          	              */

	/**
	 * Modella il salvataggio dello stato del gioco.
	 * <p>Permette di salvare lo stato attuale della partita, in modo da essere successivamente ripristinato.</p>
	 */
	public static class AGameMemento implements Serializable{
		private static final long serialVersionUID = -7393205379191303676L;

	    private final int stage;
	    private final AMapMemento mapMemento;
	    private final AColonyMemento colonyMemento;

		public AGameMemento(Stage stage, AMap map, AColony colony) {
			this.stage = stage.getStageNumber();
			mapMemento = map.saveMemento();
			colonyMemento = colony.saveMemento();
		}
	}

	/**
	 * Salva lo stato attuale della partita.
	 * <p> Viene creato un nuovo memento il quale salver�, al suo interno, lo stato della partita </p>
	 * @return the memento
	 */
	@Override
	public AGameMemento saveMemento(){
		return new AGameMemento(stage, map, colony);
	}

	/**
	 * Ripristina lo stato salvato della partita
	 * @param memento Lo stato della partita da ripristinare
	 * */
	@Override
	public void restoreMemento(AGameMemento memento){
		removeNode();
		this.stage = new Stage(memento.stage);
		this.buildGame();
		this.map.restoreMemento(memento.mapMemento);
		colony.restoreMemento(memento.colonyMemento);
		addNode();
	}
	/*              ############################################################################                     */
}















