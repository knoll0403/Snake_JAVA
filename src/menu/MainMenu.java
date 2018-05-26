package menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * <h1>MainMenu</h1>
 * A f�men� oszt�lya.
 * <p>
 * Az oszt�ly l�trehozza a f�men� 3 gombj�t, majd ezeket elhelyezi a k�perny� k�zep�re egym�s al�.
 * 
 * @author Knoll Zsolt
 */
public class MainMenu
{	
	private Stage primaryStage;
	private VBox buttons;
	private Scene scene, playscene, topscene;
	private Button play, toplist, options, quit;
	
	/**
	 * Az oszt�ly konstruktora.
	 * L�trehozza a gombokat �s a hely�kre rakja �ket.
	 * @param ps az ablak stage-e
	 * @param but a men� elemeit tartalmaz� VBox
	 * @param sc a f�men� scene-je
	 * @param psc a j�t�km�dv�laszt� scene-je
	 * @param tsc a toplista scene-je
	 */
	public MainMenu(Stage ps, VBox but, Scene sc, Scene psc, Scene tsc)
	{
		primaryStage = ps;
		buttons = but;
		scene = sc;
		playscene = psc;
		topscene = tsc;

		EventHandler ch = new ClickHandler();
		play = new Button();
		play.setText("Play");
		play.setMinSize(100, 30);
		play.setOnAction(ch);
		toplist = new Button();
		toplist.setText("Toplist");
		toplist.setMinSize(100, 30);
		toplist.setOnAction(ch);
		quit = new Button();
		quit.setText("Quit");
		quit.setMinSize(100, 30);
		quit.setOnAction(ch);
		
        Insets ins = new Insets(10, 10, 10, 10);
        buttons.getChildren().add(play);
        buttons.setMargin(play, ins);
        buttons.getChildren().add(toplist);
        buttons.setMargin(toplist, ins);
        buttons.getChildren().add(quit);
        buttons.setMargin(quit, ins);
	}

	/**
	 * <h2>ClickHandler</h2>
	 * A kattint�st kezel� bels� oszt�ly.
	 * 
	 * @author Knoll Zsolt
	 */
	class ClickHandler implements EventHandler<ActionEvent>
	{
		/**
		 * Att�l f�gg�en, hogy a felhaszn�l� melyik gombra kattint, azt az almen�t nyitja meg, vagy bez�rja a j�t�kot. 
		 */
		public void handle(ActionEvent event)
		{
			if (event.getSource().equals(play))
			{
				primaryStage.setScene(playscene);
			}
			if (event.getSource().equals(toplist))
			{
				ToplistMenu.table.refresh();
				ToplistMenu.table.sort();
				primaryStage.setScene(topscene);
			}
			if (event.getSource().equals(quit))
			{
				Stage s = (Stage) quit.getScene().getWindow();
				s.close();
			}
		}
	}
	
}
