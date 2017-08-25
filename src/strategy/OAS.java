package strategy;

import java.util.ArrayList;

import model.map.AMap;
import util.Path;
import util.Vertex;

public class OAS extends AS{

	private ArrayList<Path> paths = new ArrayList<>();
	private double Q;

	@Override
	public void offlineUpdate(AMap map, Path path) {
		paths.add(path);
		map.evaporatePheromone(1.0d - rho);

		Q = Math.log1p(paths.size());

		for(Path _path : paths)
			for(Vertex n : _path.getPath()){
				map.dropPheromoneAt(n.getRow(), n.getCol(), Q/_path.getLenght());
			}
	}

	public void initialize(AMap map) {
		super.initialize(map);
		rho = 0.3;
	}

	@Override
	public String getStrategyName() {
		return "Original Ant-System";
	}

	@Override
	public String getStrategyDescriprion() {
		return "Si tratta dell'algoritmo originalmente proposto da Marco Dorigo. "
		  + "Quando una formica l'uscita lascia lungo il suo percorso una traccia di ferormone proporzionale "
		  + "alla bont� della soluzione trovata. Ogni formica che raggiunge l'uscita aggiorna TUTTE le tracce di feromone"
		  + "lasciate dalle formiche che hanno gi� trovato una soluzione."
		  + "Il movimento delle formiche � di tipo stocatsico e la probabilit� di "
		  + "di spostarsi in una determinata direzione dipende dalla quantit� di feromone che attualmente vi � depositata. "
		  + "Il feromone depositato tuttavia evapora con il passare del tempo, dunque i percorsi migliori e pi� visitati "
		  + "tenderanno a rafforzarsi depositando sempre pi� ferormomne, mentre quelli peggiori tenderanno a sparire.";
	}

	@Override
	public ColonyStrategy makeStrategy() { return new OAS(); }

}
