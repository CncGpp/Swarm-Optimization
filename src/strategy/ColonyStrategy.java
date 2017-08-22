package strategy;

/**
 * The Class ColonyStrategy modella la strategia utilizzata dai microbot
 * <p>Essa definisce come i bot si devono muovere e come il ferormone deve essere rilasciato o aggiornato</p>
 */
public abstract class ColonyStrategy implements Mover, PheromoneUpdater, PheromoneInitializer{

	/**
	 * Permette di ottenere il nome della strategia
	 * @return the strategy name
	 */
	public abstract String getStrategyName();

	/**
	 * Permette di ottenere una descrizione della strategia
	 * @return the strategy descriprion
	 */
	public abstract String getStrategyDescriprion();

	/**
	 * Crea una nuova istanza della strategia
	 * @return the colony strategy
	 */
	public abstract ColonyStrategy makeStrategy();
}
