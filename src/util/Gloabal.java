package util;

import java.util.Arrays;
import java.util.List;

import view.BottomViewController;
import view.map.EntityMapController;
import view.map.PheromoneMapController;
import view.map.TileMapController;

public abstract class Gloabal {

	public abstract static class Settings{
		public final static int UPDATE_DELAY = 25;
		public final static int BOT_NUMBER = 50;
	}

	public abstract static class R{
		public final static ClassLoader CLASSLOADER = Gloabal.class.getClassLoader();
		public final static List<String> STAGE_LIST = Arrays.asList( "file/1.txt",
																	 "file/2.txt",
																	 "file/3.txt"
																	);
	}

	public abstract static class Controllers{
		public static TileMapController tileMap;
		public static PheromoneMapController pheromoneMap;
		public static EntityMapController entityMap;
		public static BottomViewController bottomViewController;
	}

}
