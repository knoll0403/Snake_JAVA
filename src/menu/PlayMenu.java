package menu;

import game.MultiPlayer;
import game.SinglePlayer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import toplist.Toplist;

/**
 * <h1>PlayMenu</h1>
 * Az a menü, amelyben a játékmódok közül választhatunk.
 * <p>
 * Létrehoz 3 gombot egymás alá. Az elsõ gomb az egyjátékos módot indítja el, a második a kétjátékos módot, míg a harmadik gomb visszavisz a fõmenübe.
 * 
 * @author Knoll Zsolt
 */
public class PlayMenu
{
	private Stage primaryStage;
	private VBox buttons;
	private Scene scene, mainscene;
	private Button onePlayer, twoPlayer, back;
	private Toplist toplist;

	/**
	 * Az osztály konstruktora.
	 * Létrehozza a gombokat és elrendezi õket a képernyõn.
	 * @param ps az ablak stage-e
	 * @param but a gombokat tartalmazó VBox
	 * @param sc az egyjátékos mód scene-je
	 * @param msc a kétjátékos mód scene-je
	 * @param tl a toplista
	 */
	public PlayMenu(Stage ps, VBox but, Scene sc, Scene msc, Toplist tl)
	{
		primaryStage = ps;
		buttons = but;
		scene = sc;
		mainscene = msc;
		toplist = tl;

		EventHandler ch = new ClickHandler();
		onePlayer = new Button();
		onePlayer.setText("Singleplayer");
		onePlayer.setMinSize(100, 30);
		onePlayer.setOnAction(ch);
		twoPlayer = new Button();
		twoPlayer.setText("Multiplayer");
		twoPlayer.setMinSize(100, 30);
		twoPlayer.setOnAction(ch);
		back = new Button();
		back.setText("Back");
		back.setMinSize(100, 30);
		back.setOnAction(ch);
		
        Insets ins = new Insets(10, 10, 10, 10);
        buttons.getChildren().add(onePlayer);
        buttons.setMargin(onePlayer, ins);
        buttons.getChildren().add(twoPlayer);
        buttons.setMargin(twoPlayer, ins);
        buttons.getChildren().add(back);
        buttons.setMargin(back, ins);
	}
	
	/**
	 * <h2>ClickHandler</h2>
	 * A kattintást kezelõ belsõ osztály.
	 * @author Knoll Zsolt
	 */
	class ClickHandler implements EventHandler<ActionEvent>
	{
		/**
		 * Attól függõen, hogy a felhasználó melyik gombra kattint, elindítja azt a játékot, vagy visszaviszi a fõmenübe.
		 */
		public void handle(ActionEvent event)
		{
			if (event.getSource().equals(onePlayer))
			{
				SinglePlayer splay = new SinglePlayer(primaryStage, mainscene, toplist);
			}
			if (event.getSource().equals(twoPlayer))
			{
				MultiPlayer mplay = new MultiPlayer(primaryStage, mainscene);
			}
			if (event.getSource().equals(back))
			{
		        primaryStage.setScene(mainscene);
			}
		}
	}
	
}
