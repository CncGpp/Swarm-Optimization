package util;

import java.util.Arrays;
import java.util.List;

import controller.BottomViewController;
import controller.RootViewController;
import controller.gui.ChronometerController;
import controller.gui.InfoController;
import controller.gui.RankController;
import controller.gui.SettingController;
import javafx.scene.paint.Color;
import strategy.AtanAS;
import strategy.ColonyStrategy;

public abstract class Global {

	public abstract static class Settings{
		public static int UPDATE_DELAY = 20;
		public static int BOT_NUMBER = 50;
		public static ColonyStrategy COLONY_STRATEGY = new AtanAS();
	}

	public static class C{	//Colori
		public final static Color PHEROMONE_COLOR = Color.RED;
		public final static Color MICROBOT_COLOR = Color.MEDIUMPURPLE;
		public final static Color START_COLOR = Color.DARKRED;
		public final static Color END_COLOR = Color.LIMEGREEN;
		public final static Color MANHOLE_COLOR = Color.DODGERBLUE;

		public final static Color WALL_COLOR = Color.BLACK;
		public final static Color FREE_COLOR = Color.WHITE;
		public final static Color RAISED_COLOR = Color.LIGHTGRAY;
	}

	public abstract static class R{
		public final static ClassLoader CLASSLOADER = Global.class.getClassLoader();

		/* RISORSE - FONT */
		public final static String BOLD_FONT_URI = CLASSLOADER.getResource("font/Quicksand-Bold.ttf").toExternalForm();
		public final static String MEDIUM_FONT_URI = CLASSLOADER.getResource("font/Quicksand-Medium.ttf").toExternalForm();
		public final static String REGULAR_FONT_URI = CLASSLOADER.getResource("font/Quicksand-Regular.ttf").toExternalForm();
		public final static String LIGHT_FONT_URI = CLASSLOADER.getResource("font/Quicksand-Light.ttf").toExternalForm();
		public final static String MONOSPACE_FONT_URI = CLASSLOADER.getResource("font/Quicksand-Bold.ttf").toExternalForm();

		/* RISORSE - ICONE */
		public final static String APP_ICON_URI = CLASSLOADER.getResource("icon/app.png").toExternalForm();
		public final static String START_ICON_URI = CLASSLOADER.getResource("icon/start.png").toExternalForm();
		public final static String PAUSE_ICON_URI = CLASSLOADER.getResource("icon/pause.png").toExternalForm();
		public final static String RESTART_ICON_URI = CLASSLOADER.getResource("icon/restart.png").toExternalForm();
		public final static String RANK_ICON_URI = CLASSLOADER.getResource("icon/rank.png").toExternalForm();
		public final static String INFO_ICON_URI = CLASSLOADER.getResource("icon/info.png").toExternalForm();

		public final static String USER_ICON_URI = CLASSLOADER.getResource("icon/user.png").toExternalForm();
		public final static String CHANGE_ICON_URI = CLASSLOADER.getResource("icon/change.png").toExternalForm();

		public final static String FIRST_ICON_URI =  CLASSLOADER.getResource("icon/1st.png").toExternalForm();
		public final static String SECOND_ICON_URI = CLASSLOADER.getResource("icon/2nd.png").toExternalForm();
		public final static String THIRD_ICON_URI = CLASSLOADER.getResource("icon/3rd.png").toExternalForm();

		/* RISORSE - FILE*/
		public final static String RANK_URI = "rank.txt";
		public final static List<String> STAGE_LIST = Arrays.asList( "file/0a.txt",
																	 "file/1a.txt",
																	 "file/2a.txt",
																	 "file/3.txt",
																	 "file/4a.txt"
																	);
	}

	public abstract static class Controllers{
		public static RootViewController rootView;
		public static BottomViewController bottomViewController;

		public static ChronometerController chronometerController;
		public static RankController rankController;
		public static InfoController infoController;
		public static SettingController settingController;
	}

}
