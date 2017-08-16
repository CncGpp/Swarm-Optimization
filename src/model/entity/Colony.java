package model.entity;


import model.map.Map;
import strategy.*;
import util.Coord;

public class Colony extends AColony{

	public Colony(final Map map, final int botCount, final AStart start) {
		super(map, botCount, start);
		this.setStrategy(new AtanAS());
	}

	//Factory method
	@Override
	protected ABot makeBot(final Coord startPosition){ return new Bot(this, startPosition); }

}
