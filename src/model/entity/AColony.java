package model.entity;

import java.util.ArrayList;
import java.util.Observable;

import javafx.scene.Node;
import model.AGame;
import model.Drawable;
import model.map.AMap;
import strategy.ColonyStrategy;
import util.Coord;
import util.Gloabal;
import util.Gloabal.Settings;

/**
 * La classe <code>AColony</code> modella una colonia/sciame di microbot.
 * <p>Si tratta di un classe che rappresenta un'intera colonia di formica nel algoritmo <tt>AS - Ant System</tt> e come
 * tale mantiene informazioni su i suoi microbot e su come essi si devono comportare.</p>
 *
 * @author Cianci Giuseppe Pio
 * @see ABot
 * @see AGame
 * */
public abstract class AColony extends Observable implements Drawable{

	/**  La mappa di gioco su cui si trova la colonia. */
	private AMap map;

	/** La lista dei bot che fanno parte della colonia e che si trovano ancora sulla mappa.*/
	private ArrayList<ABot> bots;

	/** La lista dei bot che sono usciti dalla mappa.
	 * <p> Poiche' non è direttamente possibile rimuovere un elemento da una lista che si sta scorrendo è necessario
	 * farlo in un secondo momento, salverò dunque quì i bot da rimuovere.</p>*/
	private ArrayList<ABot> toRemove = new ArrayList<>();

	/** La strategia utilizzata dai bot della colonia per muoversi, seguire il ferormone e uscire dalla mappa.
	 * <p> Di default la strategia è definita nella classe {@code Global.Settings}.</p>
	 * @see Gloabal.Settings
	 * @see ColonyStrategy
	 * */
	private ColonyStrategy strategy;

	/**
	 * Istanzia una nuova colonia di bot.
	 *
	 * @param map l'istanza della mappa a cui la colonia deve appartenere.
	 * @param botCount il numero di bot che devono far parte della colonia.
	 * @param start l'entita' {@code AStart} da dove la colonia dovra' cominciare.
	 *
	 * @see AGame
	 * @see AStart
	 */
	public AColony(final AMap map, final int botCount, final AStart start) {
		this.setMap(map);
		this.setStrategy(Settings.COLONY_STRATEGY.makeStrategy());

		bots =  new ArrayList<>();									//Creo i bot della colonia con il factory method
		for(int i = 0; i < botCount; i++){
			addBot( makeBot(start.getCoordinate()) );
		}
		strategy.initialize(map);									//Inizializzo il feromone della mappa secondo la strategia
	}

	/**
	 *  Factory Method per la creazione dei bot della colonia
	 * <p> Il metodo è astratto dunque saranno le sottclassi a scegliere come implementare il bot e le sue caratteristiche </p>.
	 *
	 * @param startPosition posizione di partenza del bot da creare
	 * @return ritorna un bot della colonia
	 */
	protected abstract ABot makeBot(final Coord startPosition);

	/**
	 *  Metodo che permette di aggiungere i bot alla colonia.
	 *
	 * @param bot il bot da aggiungere alla lista dei bot della colonia
	 */
	protected void addBot(final ABot bot){
		try{
			if(bot == null) throw new IllegalArgumentException();
			else bots.add(bot);
		} catch(IllegalArgumentException e){
			System.err.println("Inserimento nella colonai annullato: Non è possibile aggiungere Bot 'null'");
		}
	}

	/** Metodo che permette di rimuovere un bot dalla lista dei bot
	 * <p> Poichè non è possibile rimuovere un elemento da una lista che si sta iterando per una maggiore compatibilita'
	 * si è scelto di aggiungere i bot da rimuove alla lista {@code toRemove} per poi rimuoverli successivamente.</p>
	 * @param bot il bot da rimuovere
	 * @see AColony#toRemove
	 * */
	public void removeBot(final ABot bot){ toRemove.add(bot); }

	/** Metodo astratto che definisce il movimento dei bot
	 * <p> Implementando questo metodo si definisce come i bot debbano muoversi, se insieme, uno alla volta ecc... </p>
	 * */
	protected abstract void moveBot();

	/**
	 *  Metodo principale della classe che effettua l'aggiornamento dello stato interno della colonia
	 * <p>In questo metodo vengono mossi i bot, notificato il cambiamento agli observer della classe ed in fine rimossi
	 * dalla colonia i bot che si trovano nella lista {@code toRemove}</p>.
	 */
	public final void update(){
		notifyObservers(bots);			// Notifico gli observer il cambiamento (in questo caso dello stato precedente..)
		this.moveBot();					// Muovo i bot della colonia
		this.setChanged();				// Setto lo stato dell'observable a modificato in modo da notificare il cambiamento

		bots.removeAll(toRemove);		// Rimuovo i bot che sono usciti dalla mappa
		toRemove.clear();				// pulisco la lista toRemove
	}


	/* 									+--------------------------------------+
	 * 									|        METODI GETTER & SETTERS       |
	 * 									+--------------------------------------+          	                          */

	/**
	 * Metodo getter per ottenere la mappa su cui si trova la colonia.
	 *
	 * @return la mappa di appartenenza della colonia
	 */
	public AMap getMap() {return map;}

	/**
	 * Metodo setter per settare la mappa di gioco
	 * @param map la mappa di gioco
	 */
	protected void setMap(final AMap map) {
		if(map == null) throw new IllegalArgumentException("La mappa della colonia non può essere 'null");
		else this.map = map;
	}

	/**
	 * Controlla se la lista di bot presenti nella colonia è vuota o meno
	 * @return true, se è vuota
	 */
	public boolean isEmpty() { return bots.isEmpty(); }

	/**
	 * Metodo getter per ottenere la strategia utilizzata dalla colonia
	 * @return la strategia
	 */
	public ColonyStrategy getStrategy(){ return strategy; }

	/**
	 * Metodo setter per settare la strategia
	 * @param strategy la strategia della colonia
	 */
	public void setStrategy(final ColonyStrategy strategy){
		if(strategy == null) throw new IllegalArgumentException("La strategia della colonia non può essere 'null");
		else this.strategy = strategy;
	}

	/**
	 * Metodo getter per ottenere la lista dei bot presenti nella colonia
	 * @return la lista dei bot
	 */
	protected ArrayList<ABot> getBots(){return bots;}

	/* (non-Javadoc)
	 * @see model.Drawable#getNode()
	 */
	@Override
	public Node getNode() { return null; }

	/* (non-Javadoc)
	 * @see model.Drawable#addNode()
	 */
	@Override
	public void addNode(){ for(ABot b : bots) b.addNode(); }

	/* (non-Javadoc)
	 * @see model.Drawable#removeNode()
	 */
	@Override
	public void removeNode(){ for(ABot b : bots) b.removeNode();}

}
