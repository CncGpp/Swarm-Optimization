package strategy;

import model.entity.ABot;
import model.map.AMap;
import util.Global.Settings;
import util.Path;
import util.Vertex;

public class RAS extends AS {
	private static final long serialVersionUID = -8945756587781283964L;

	private int count = 0;
	private int remaning = Settings.BOT_NUMBER;

	public RAS() {
		alpha = 10;
		beta = 3;
		rho = 0.92;	//Evaporazione online
		tau = 1.05; //Incremento online solo alla prima visita
	}

	@Override
	public void onlineUpdate(AMap map, ABot bot) {
		if(!bot.isVisited(bot.getCoordinate())){
			map.dropPheromoneAt(bot.getRow(), bot.getCol(), 1.0d-rho);
			map.evaporatePheromoneAt(bot.getRow(), bot.getCol(), tau);
		}
		map.evaporatePheromoneAt(bot.getRow(), bot.getCol(), rho);
		if(count++ > remaning){ count = 0; map.evaporatePheromone(phi);}
	}

	@Override
	public void offlineUpdate(AMap map, Path path) {
		remaning--;
		double sum = 0;
		for(Vertex n : path.getPath()){
			sum+=n.getWeight();
			map.dropPheromoneAt(n.getRow(), n.getCol(), sum/(path.getLenght()));
		}
	}

	@Override
	public void initialize(AMap map) {
		phi = 1.0d-0.20d/mapDiameter(map);
		map.setPheromone(0);
	}
	private double mapDiameter(final AMap map){ return Math.sqrt(map.getRows()*map.getRows() + map.getCols()*map.getCols());}

	@Override
	public String getStrategyName() { return "Real Ant-System"; }

	@Override
	public String getStrategyDescriprion() {
		return "Si tratta di un tentativo di applicare il comportamento effettivo delle formiche al problema. "
				+ "Ogni formica, quando si muovo, rilascia un quantitativo di feromone sulle celle che non ha già visitato "
				+ "proporzionale alla quantità dello stesso già presente e una volta arrivata al cibo torna indietro fino "
				+ "all'entrata della sua tana effettuando lo stesso processo. "
				+ "(Questa fase è simulata in quanto nel nostro problema i microbot non devono tornare indietro). "
				+ "Il feromone evapora lentamente a ritmo costante indipendentemente dalle formiche.";
	}
	@Override
	public ColonyStrategy makeStrategy() { return new RAS(); }

}
