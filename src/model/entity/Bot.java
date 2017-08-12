package model.entity;

import java.util.ArrayList;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import model.map.Map;
import model.map.TileType;
import util.Coord;
import util.Gloabal.Controllers;
import util.Gloabal.Settings;
import util.Node;

public class Bot extends Entity{

	private Circle c;
	private Colony colony;
	private Map map;

	public Bot(final Colony colony) {
		this(colony, -1, -1);
	}

	public Bot(final Colony colony, final int row, final int col){
		super(new Coord(row,col));
		this.colony = colony;
		this.map = colony.getMap();
		c = new Circle(25);
		c.setFill(Color.DARKGREY);
		c.setStrokeWidth(1);
		c.setStroke(Color.BLACK);
		draw();
	}

	public void move(){
		colony.getStrategy().onlineUpdate(map, this);

		Coord nextPos = colony.getStrategy().selectNextMove(map, this);
		translateTo(nextPos);
		setCoordinate(nextPos);
	}

	private void translateTo(final Coord coordinate){
		TranslateTransition translation = new TranslateTransition(Duration.millis(Settings.UPDATE_DELAY/2), c);
		translation.setByX((coordinate.getCol() - this.getCoordinate().getCol()) * map.getTileSize());
		translation.setByY((coordinate.getRow() - this.getCoordinate().getRow()) * map.getTileSize());
		translation.play();
	}


	/**
	 * La funzione permette di ottenere le possibili che si trovano in un 8-connesso.
	 * <p> tali direzioni verranno in seguito utilizzate per pianificare il movimento del bot. </p>
	 *
	 * @param newDirections � un parametro di <code>in/out</code> dove verranno ritornate le nuove direzioni, ovvero
	 * quelle praticabili e che nell'intorno non sono state ancora visitate dal bot.
	 * @param oldDirections � un parametro di <code>in/out</code> dove verranno ritornate le vecchie direzioni, ovvero
	 * quelle che nell'intorno sono state gi� visitate dal bot.
	 */
	public ArrayList<Node> getNeighbors(){
		ArrayList<Node> newDirections = new ArrayList<Node>();

		//Il bot pu� muoversi secondo un intorno 8-connesso di dimensione 3x3
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){

				if(i == 1 && j == 1) continue;					//Questo � il centro, ovvero la posizione del bot
																//quindi non deve essere considerata.
				final int _row = getRow() + i -1;				//Calcolo le coordinate dei nodi vicini rispetto a quella attuale.
				final int _col = getCol() + j -1;

				//Se la coordinate ricade fuori la mappa, non la considero.
				if(_row < 0 || _col < 0 || _row >= map.getRows() || _col >= map.getCols()) continue;

				/** Essendo la map basata su griglia quadrata le direzioni diagonali avranno peso sqrt(2), le restanti 1*/
				final double peso = (_row == getRow() || _col == getCol()) ? 1 : 1.4142135623;

				//Se � una direzione possibile lo aggiungo a newDirections
				if(map.getTileTypeAt(_row, _col) != TileType.WALL) newDirections.add(new Node(_row, _col, peso));
			}
		}

		return newDirections;
	}






	public void draw(){ c.relocate(getCol() * map.getTileSize(),  getRow() * map.getTileSize());}

	@Override
	public void add(){ Controllers.entityMap.add(c); }

	@Override
	public void remove(){ Controllers.entityMap.remove(c);}

}
