package util;

import java.util.Arrays;
import java.util.List;

import view.BottomViewController;
import view.gui.ChronometerController;
import view.gui.RankController;
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

		public final static String APP_ICON_URI = CLASSLOADER.getResource("icon/icon.png").toExternalForm();
		public final static String START_ICON_URI = CLASSLOADER.getResource("icon/start.png").toExternalForm();
		public final static String PAUSE_ICON_URI = CLASSLOADER.getResource("icon/pause.png").toExternalForm();
		public final static String RESTART_ICON_URI = CLASSLOADER.getResource("icon/restart.png").toExternalForm();
		public final static String RANK_ICON_URI = CLASSLOADER.getResource("icon/rank.png").toExternalForm();
		public final static String INFO_ICON_URI = CLASSLOADER.getResource("icon/info.png").toExternalForm();

		public final static String USER_ICON_URI = CLASSLOADER.getResource("icon/user.png").toExternalForm();
		public final static String NOLOGIN_ICON_URI = CLASSLOADER.getResource("icon/noLogin.png").toExternalForm();
		public final static String CHANGE_ICON_URI = CLASSLOADER.getResource("icon/change.png").toExternalForm();

		public final static String FIRST_ICON_URI =  CLASSLOADER.getResource("icon/1st.png").toExternalForm();
		public final static String SECOND_ICON_URI = CLASSLOADER.getResource("icon/2nd.png").toExternalForm();
		public final static String THIRD_ICON_URI = CLASSLOADER.getResource("icon/3rd.png").toExternalForm();

		public final static String RANK_URI = "rank.txt";
		public final static List<String> STAGE_LIST = Arrays.asList( "file/1.txt",
																	 "file/2.txt"
																	);
	}

	public abstract static class Controllers{
		public static TileMapController tileMap;
		public static PheromoneMapController pheromoneMap;
		public static EntityMapController entityMap;
		public static BottomViewController bottomViewController;

		public static ChronometerController chronometerController;
		public static RankController rankController;
	}

}
