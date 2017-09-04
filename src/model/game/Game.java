package model.game;

import model.entity.AColony;
import model.entity.AEnd;
import model.entity.AManhole;
import model.entity.AStart;
import model.entity.Colony;
import model.entity.End;
import model.entity.Manhole;
import model.entity.Start;
import model.map.AMap;
import model.map.Map;
import util.Coord;
import util.Global.Controllers;

/** Implementazione concreta della classe {@code AGame}.
 * <p> La classe ridefinisce i factory metodi per creare una proprie implementazione del gioco, in oltre si effettua
 * un override di end() per implementare il submit dei punteggi.</p>*/
public class Game extends AGame{

	@Override
	protected AMap makeMap(Stage stage) { return new Map(this, stage); }
	@Override
	protected AColony makeColony(AMap map, int botCount, AStart start) {return new Colony(map, botCount, start); }

	@Override
	public void end() {
		super.end();
		Controllers.bottomViewController.stageEnded();
	}
	@Override
	protected AStart makeStart(AMap map, Coord coord) {
		return new Start(map, coord.getRow(), coord.getCol());
	}
	@Override
	protected AEnd makeEnd(AMap map, Coord coord) {
		return new End(map, coord.getRow(), coord.getCol());
	}
	@Override
	protected AManhole makeManhole(AMap map, Coord coord) {
		return new Manhole(map, coord.getRow(), coord.getCol());
	}
}
















