package strategy;

import model.entity.ABot;
import model.map.AMap;
import util.Path;
/**
 * Un interfaccia alle funzioni da chiamare per l'aggiornamento del ferormone
 * */
public interface PheromoneUpdater {
	public void onlineUpdate(final AMap map, final ABot bot);
	public void offlineUpdate(final AMap map, final Path path);
}
