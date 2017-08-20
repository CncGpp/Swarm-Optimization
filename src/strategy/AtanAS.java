package strategy;

import model.entity.ABot;
import model.map.AMap;
import util.Coord;
import util.Vertex;
import util.Path;

public class AtanAS extends AS{

	protected double lambda = 3.0d;
	protected double omega = 1.5d;

	public AtanAS() {
		super.alpha = 10;
		super.beta = 5;
	}

	protected double func(final double pLevel, final double k){
		//Il decremento è minore per valori alti di feromone e maggiore per valori più bassi così si favorisce il concentramento
		//livello attuale         decremento : \in [0:1)
		//      |						|
		//      V						V
		return pLevel * Math.atan(pLevel + k)/(Math.PI/2);	// Maggiore è il k minore è il decremento!
	}

	@Override
	public void onlineUpdate(AMap map, ABot bot) {
		final Coord pos = bot.getCoordinate();
		double pLevel = map.getPheromoneAt(pos.getRow(), pos.getCol());
		map.setPheromoneAt(pos.getRow(), pos.getCol(), func(pLevel, lambda));
	}

	@Override
	public void offlineUpdate(AMap map, Path path) {
		double sum = 0;
		for (Vertex n : path.getPath()) {
			map.dropPheromoneAt(n.getRow(), n.getCol(), sum/path.getLenght());
			sum+=n.getWeight();
		}

		for(int i = 0; i < map.getRows(); i++)
			for(int j = 0; j < map.getCols(); j++){
				double pLevel = map.getPheromoneAt(i, j);
				map.setPheromoneAt(i, j, func(pLevel, omega));
			}
	}

	@Override
	public void initialize(AMap map) { super.initialize(map); }

	@Override
	public String getStrategyName() { return "Arctan Ant-System"; }

	@Override
	public String getStrategyDescriprion() {
		return  "Un'evoluzione dell'Ant-System particolarmente efficace. Ogni volta che una soluzione è stata trovata Il "
			  + "ferormone viene distribuito descrescentemente a retroso sul percorso effettuato dalla "
			  + "formica. Le fasi di aggiornamento, online e offline sono effettuate utilizzando la funzione arcotangente "
			  + "essa permette di ridurre in maniera maggiore i valori bassi di feromone e in maniera minore i valori alti "
			  + "In questo modo ne si favorisce il concetramento intorno all'uscita diminuendo man mano che "
			  + "ci si allontana portando i microbot 'risalire' il feromone fino all'uscita.";
	}

	@Override
	public ColonyStrategy makeStrategy() { return new AtanAS(); }


}
