package model;

import model.entity.AColony;
import model.entity.AStart;
import model.entity.Colony;
import model.map.AMap;
import model.map.Map;
import util.Gloabal.Controllers;

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
}
















