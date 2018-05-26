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
 * Az a men�, amelyben a j�t�km�dok k�z�l v�laszthatunk.
 * <p>
 * L�trehoz 3 gombot egym�s al�. Az els� gomb az egyj�t�kos m�dot ind�tja el, a m�sodik a k�tj�t�kos m�dot, m�g a harmadik gomb visszavisz a f�men�be.
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
	 * Az oszt�ly konstruktora.
	 * L�trehozza a gombokat �s elrendezi �ket a k�perny�n.
	 * @param ps az ablak stage-e
	 * @param but a gombokat tartalmaz� VBox
	 * @param sc az egyj�t�kos m�d scene-je
	 * @param msc a k�tj�t�kos m�d scene-je
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
	 * A kattint�st kezel� bels� oszt�ly.
	 * @author Knoll Zsolt
	 */
	class ClickHandler implements EventHandler<ActionEvent>
	{
		/**
		 * Att�l f�gg�en, hogy a felhaszn�l� melyik gombra kattint, elind�tja azt a j�t�kot, vagy visszaviszi a f�men�be.
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
