import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import menu.MainMenu;
import menu.PlayMenu;
import menu.ToplistMenu;

/**
 * <h1>Main</h1>
 * A program Main met�dusa.
 * <p>
 * Az Application oszt�lyb�l �r�kl�dik. L�trehozza a program f� men�jeit �s a k�perny�re helyezi a f�men�t.
 * 
 * @author Knoll Zsolt
 */
public class Main extends Application
{
	/**
	 * Az oszt�ly main met�dusa. Elind�tja az alkalmaz�st.
	 */
	public static void main()
	{
		launch();
	}

	/**
	 * Az Application start met�dus�t �rja fel�l.
	 * L�trehozza a f�men�t, a toplista men�j�t, �s a j�t�km�dv�laszt� men�j�t.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		primaryStage.setTitle("Snake");
		primaryStage.setResizable(false);
		Insets menuins = new Insets(220, 0, 0, 270);
		
		BorderPane mainlayout = new BorderPane();
		VBox mainbuttons = new VBox();
		mainbuttons.setPadding(menuins);
		mainlayout.setCenter(mainbuttons);
        Scene mainmenu = new Scene(mainlayout, 640, 480);
        
		BorderPane playlayout = new BorderPane();
		VBox playbuttons = new VBox();
		playbuttons.setPadding(menuins);
		playlayout.setCenter(playbuttons);
        Scene playmenu = new Scene(playlayout, 640, 480);

        Insets toplistins = new Insets(20, 370, 20, 68);
		BorderPane toplayout = new BorderPane();
		VBox toplistbox = new VBox();
		toplistbox.setPadding(toplistins);
		toplayout.setCenter(toplistbox);
        Scene topmenu = new Scene(toplayout, 640, 480);
        
		MainMenu mm = new MainMenu(primaryStage, mainbuttons, mainmenu, playmenu, topmenu);
		PlayMenu pm = new PlayMenu(primaryStage, playbuttons, playmenu, mainmenu, ToplistMenu.toplist);
		ToplistMenu tlm = new ToplistMenu(primaryStage, toplistbox, mainmenu);
		
		primaryStage.setScene(mainmenu);
        primaryStage.show();
	}

}
