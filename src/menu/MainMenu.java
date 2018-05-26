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
 * A fõmenü osztálya.
 * <p>
 * Az osztály létrehozza a fõmenü 3 gombját, majd ezeket elhelyezi a képernyõ közepére egymás alá.
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
	 * Az osztály konstruktora.
	 * Létrehozza a gombokat és a helyükre rakja õket.
	 * @param ps az ablak stage-e
	 * @param but a menü elemeit tartalmazó VBox
	 * @param sc a fõmenü scene-je
	 * @param psc a játékmódválasztó scene-je
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
	 * A kattintást kezelõ belsõ osztály.
	 * 
	 * @author Knoll Zsolt
	 */
	class ClickHandler implements EventHandler<ActionEvent>
	{
		/**
		 * Attól függõen, hogy a felhasználó melyik gombra kattint, azt az almenüt nyitja meg, vagy bezárja a játékot. 
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
