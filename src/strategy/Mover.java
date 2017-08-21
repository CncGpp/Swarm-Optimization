package strategy;

import model.entity.ABot;
import model.map.AMap;
import util.Vertex;

/** Un interfaccia per la scelta della prossima posizione del bot*/
public interface Mover {
	public Vertex selectNextMove(final AMap map, final ABot bot);
}
