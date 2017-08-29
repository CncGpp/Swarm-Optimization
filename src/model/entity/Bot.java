package model.entity;

import java.util.ArrayList;

import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.map.AMap;
import model.map.TileType;
import util.Coord;
import util.Global.C;
import util.Vertex;

/** Implementazione concreta della classe {@code ABot}.
 * <p> La classe implementa i metodi astratti della superclasse definendo cosi' il comportamento del Bot</p>
 * <p> La scelta della prossima posizione, della quantita' di ferormone da depositare localmente o globalmente dipendono dalla
 * strategia del Colonia di cui fa parte il bot</p>
 * */
public class Bot extends ABot{

	/**
	 * Istanzia un nuovo Bot.
	 *
	 * @param colony la colonia di appartenenza del bot
	 * @see AColony
	 */
	public Bot(final AColony colony) {
		this(colony, new Coord(-1,-1));
	}

	/**
	 * Istanzia un nuovo Bot.
	 *
	 * @param colony la colonia di appartenenza del bot
	 * @param coordinate le coordinate del bot sulla mappa
	 * @see AColony
	 * @see Coord */
	public Bot(final AColony colony, final Coord coordinate){
		super(colony, coordinate);
		this.colony = colony;

		visited = new boolean[colony.getMap().getRows()][colony.getMap().getCols()];
	}

	/* (non-Javadoc)
	 * @see model.entity.Entity#makeNode(model.map.AMap)
	 */
	@Override
	protected Node makeNode(AMap map) {
		final Circle c = new Circle(map.getTileSize()/2);
		c.setCache(true);
		c.setCacheHint(CacheHint.SPEED);
		c.setFill(C.MICROBOT_COLOR);
		c.setStrokeWidth(0.3);
		c.setStroke(Color.BLACK);
		c.relocate(getCol() * map.getTileSize(),  getRow() * map.getTileSize());
		return c;
	}

	/* (non-Javadoc)
	 * @see model.entity.ABot#move()
	 */
	@Override
	public boolean move(){
		colony.getStrategy().onlineUpdate(colony.getMap(), this);						//Effettuo l'onlineUpdate del ferormone
		Vertex nextPos = colony.getStrategy().selectNextMove(colony.getMap(), this);	//Determino il prossimo spostamento
		return moveTo(nextPos);															//Muovo il bot
	}

	/* (non-Javadoc)
	 * @see model.entity.ABot#moveTo(util.Vertex)
	 */
	@Override
	protected boolean moveTo(final Vertex coord){
		if(colony.getMap().getTileTypeAt(getRow(), getCol()) == TileType.WALL) return false;	//Se la coordinata è impraticabile ritorno

		if(!visited[coord.getRow()][coord.getCol()]) path.addNode(coord);   //Se le coordinate non sono state visitate le aggiungo al percorso

		visited[this.getRow()][this.getCol()] = true;						//Marco le coordinate come visitate

		setCoordinate(coord);												//Sposto il Bot settando le nuove coordinate
		this.draw();														//e riloco la sua rappresentazione alle nuove coordinate
		return true;
	}

	/* (non-Javadoc)
	 * @see model.entity.ABot#getNeighbors(java.util.ArrayList, java.util.ArrayList)
	 */
	@Override
	public void getNeighbors(ArrayList<Vertex> newDirections, ArrayList<Vertex> oldDirections){
		final AMap map = colony.getMap();
		//Il bot può muoversi secondo un intorno 8-connesso di dimensione 3x3
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){

				if(i == 1 && j == 1) continue;					//Questo è il centro, ovvero la posizione del bot
																//quindi non deve essere considerata.
				final int _row = getRow() + i -1;				//Calcolo le coordinate dei nodi vicini rispetto a quella attuale.
				final int _col = getCol() + j -1;

				//Se la coordinate ricade fuori la mappa, non la considero.
				if(_row < 0 || _col < 0 || _row >= map.getRows() || _col >= map.getCols()) continue;

				/* Determino il peso fra i due nodi*/
				final double peso = map.getWeight(new Coord(getRow(), getCol()), new Coord(_row,_col) );

				//Se l'ho gia' visitato, lo aggiungo a oldDirections, altrimenti se è una direzione
				//possibile lo aggiungo a newDirections
				if(visited[_row][_col]) oldDirections.add(new Vertex(_row, _col, peso));
				else if(map.getTileTypeAt(_row, _col) != TileType.WALL) newDirections.add(new Vertex(_row, _col, peso));
			}
		}
	}

	/* (non-Javadoc)
	 * @see model.entity.ABot#leaved()
	 */
	@Override
	public void leaved(){
		colony.getStrategy().offlineUpdate(colony.getMap(), path);		//Effettuo l'offlineUpdate del ferormone
		colony.removeBot(this);											//Rimuovo il bot dalla colonia
		this.removeNode();												//Cancello il bot dalla scena rimuovendo il nodo dalla View
	}

	/**
	 * Metodo privato utilizzato solo per ridisegnare il bot dopo un suo spostamento.
	 */
	private void draw(){
		final AMap map = colony.getMap();
		this.getNode().relocate(getCol() * map.getTileSize(),  getRow() * map.getTileSize());
	}

}
