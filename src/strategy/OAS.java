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
		  + "alla bontà della soluzione trovata. Ogni formica che raggiunge l'uscita aggiorna TUTTE le tracce di feromone"
		  + "lasciate dalle formiche che hanno già trovato una soluzione."
		  + "Il movimento delle formiche è di tipo stocatsico e la probabilità di "
		  + "di spostarsi in una determinata direzione dipende dalla quantità di feromone che attualmente vi è depositata. "
		  + "Il feromone depositato tuttavia evapora con il passare del tempo, dunque i percorsi migliori e più visitati "
		  + "tenderanno a rafforzarsi depositando sempre più ferormomne, mentre quelli peggiori tenderanno a sparire.";
	}

	@Override
	public ColonyStrategy makeStrategy() { return new OAS(); }

}
