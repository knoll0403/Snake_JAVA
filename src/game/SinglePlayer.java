package game;

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import toplist.Result;
import toplist.Toplist;

/**
 * <h1>SinglePlayer</h1>
 * Az egyjátékos mód osztálya
 * <p>
 * Ez az osztály futtatja az egyjátékos módot. 
 * 
 * @author Knoll Zsolt
 */
public class SinglePlayer
{	
	public static final int APP_W = 640;
	public static final int APP_H = 480;
	
	private Stage primaryStage;
	private Scene mainmenu;
	private GraphicsContext gc;

	private boolean inIt = true;
	
	private Timeline timeline = new Timeline();
	private Toplist toplist;
	
	private Snake snake;
	private Food food;
	private Poison poison;
	
	Random rand = new Random();

	/**
	 * Az osztály konstruktora.
	 * Létrehozza a grafikus felületet, amelyet felkészít a játék futtatására. Itt található a játék irányításához szükséges metódus.
	 * Legvégül pedig elindítja a játékot.
	 * 
	 * @param pStage az ablak stage-e
	 * @param menu a fõmenü scene-je
	 * @param tl a játék toplistája
	 */
	public SinglePlayer(Stage pStage, Scene menu, Toplist tl)
	{
		primaryStage = pStage;
		mainmenu = menu;
		toplist = tl;

		Group group = new Group();
        Canvas canvas = new Canvas(640, 480);
        gc = canvas.getGraphicsContext2D();
        group.getChildren().add(canvas);
		Scene scene = new Scene(group);
		this.createContent();
		
		scene.setOnKeyPressed(event ->
		{			
			switch(event.getCode())
			{
			case W:
				if (snake.getDirection() != Snake.Direction.DOWN)
					snake.setNextDir(Snake.Direction.UP);
				break;
			case A:
				if (snake.getDirection() != Snake.Direction.RIGHT)
					snake.setNextDir(Snake.Direction.LEFT);
				break;
			case S:
				if (snake.getDirection() != Snake.Direction.UP)
					snake.setNextDir(Snake.Direction.DOWN);
				break;
			case D:
				if (snake.getDirection() != Snake.Direction.LEFT)
					snake.setNextDir(Snake.Direction.RIGHT);
				break;
			case UP:
				if (snake.getDirection() != Snake.Direction.DOWN)
					snake.setNextDir(Snake.Direction.UP);
				break;
			case LEFT:
				if (snake.getDirection() != Snake.Direction.RIGHT)
					snake.setNextDir(Snake.Direction.LEFT);
				break;
			case DOWN:
				if (snake.getDirection() != Snake.Direction.UP)
					snake.setNextDir(Snake.Direction.DOWN);
				break;
			case RIGHT:
				if (snake.getDirection() != Snake.Direction.LEFT)
					snake.setNextDir(Snake.Direction.RIGHT);
				break;
			case ESCAPE:
				stopGame();
				quitGame();
				break;
			case SPACE:
				stopGame();
				startGame();
				break;
			}
		});

		primaryStage.setScene(scene);
		primaryStage.show();
		startGame();
	}
	
	/**
	 * Ez a függvény futtatja a játék ciklusát.
	 * Az elején egy ételt helyez el a pályán, 0.2 sec-enként lépteti a kígyót, ellenõrizve, hogy még életben van-e,
	 * illetve szükség esetén új ételt helyez el a képernyõn. Ha a kígyó meghalt kilép a ciklusból.
	 */
	public void createContent()
	{
		Pane root = new Pane();
		root.setPrefSize(APP_W, APP_H);
		
		food = new Food(gc);
		
		KeyFrame frame = new KeyFrame(Duration.seconds(0.2), event-> {
			if (!snake.isAlive())
				return;

			snake.setDirection(snake.getNextDir());
			snake.move();
			if(!snake.isAlive())
				stopGame();

			generatePoison();

			if (eat(snake.getHead(), food.getUnit()))
			{
				snake.addScore();
				writeScore();
				generateFood();
				
				Unit un = new Unit(gc, snake.getHead().getX(), snake.getHead().getY(), snake.getColor());
				snake.add(un);
			}
			
			inIt = true;
			if (poison != null && eat(snake.getHead(), poison.getUnit()))
			{
				snake.decScore();
				writeScore();
				poison.delete();
				poison = null;
			}

			//Kirajzolunk mindent
			if(snake.isAlive())
			{
				snake.draw();
				food.draw();
				if (poison != null)
				{
					poison.decLife();
					if (poison.noLife())
					{
						poison.delete();
						poison = null;
					}
					else
					{
						poison.draw();
					}
				}
			}
		});
		
		timeline.getKeyFrames().add(frame);
		timeline.setCycleCount(Timeline.INDEFINITE);
	}
	
	/**
	 * Új ételt helyez el a pályán, vigyázva, hogy az étel ne kerüljön a kígyó testébe, és a méreggel se kerüljön egy mezõre.
	 */
	public void generateFood()
	{
		inIt = true;
		while (inIt)
		{
			food = new Food(gc);
			inIt = snake.inSnake(food.getUnit()) || 
					( poison != null && food.getUnit().getX() == poison.getUnit().getX() && food.getUnit().getY() == poison.getUnit().getY());
			if (inIt)
				food.delete();
		}
	}
	
	/**
	 * Véletlen idõközönként mérget helyez el a pályán, figyelve, hogy a méreg ne legyen benne a kígyóban és az étellel se kerüljön egy mezõre.
	 */
	public void generatePoison()
	{
		inIt = true;
		int x = rand.nextInt(75);
		if (x % 75 == 0 && poison == null)
		{
			while (inIt)
			{
				poison = new Poison(gc);
				inIt = snake.inSnake(poison.getUnit()) || 
						(food.getUnit().getX() == poison.getUnit().getX() && food.getUnit().getY() == poison.getUnit().getY());
				if (inIt)
					poison.delete();
			}
		}
	}
	
	/**
	 * Megnézi, hogy a kígyó feje megette-e a paraméterként kapott ételt vagy mérget.
	 * 
	 * @param head a kígyó feje
	 * @param thing az étel vagy méreg.
	 * @return true, ha a head és a thing is ugyanazon a mezõn szerepel.
	 */
	public boolean eat(Unit head, Unit thing)
	{
		return head.getX() == thing.getX() && head.getY() == thing.getY();
	}
	
	/**
	 * A játék végén a játékos pontszámát összehasonlítja a toplista legkisebb pontszámával.
	 * Ha a játékos felkerült a toplistára, bekéri a nevét, majd az eredményt a névvel együtt elmenti,
	 * különben pedig egy felirat jelenik meg a képernyõn, majd az ESC gomb megnyomásával visszakerülünk a fõmenübe.
	 */
	public void topScore()
	{
		if(snake.getScore() > toplist.lowestScore())
		{
			Scene scene = new Scene(new Group(), 640, 480);
			TextField tfName = new TextField();
		    Button btn = new Button();
		    btn.setText("Ok");
		    
		    btn.setOnAction(new EventHandler<ActionEvent>()
		    {
		        @Override
		        public void handle(ActionEvent event)
		        {
					String sName = tfName.getText();
					
					Result r = new Result(sName, snake.getScore());
					toplist.add(r);
					
					toplist.save();
					quitGame();
		        }
		    });
		    
		    VBox box = new VBox();
		    box.setPadding(new Insets(200, 200, 200, 200));
		    GridPane grid = new GridPane();
		    grid.setVgap(4);
		    grid.setHgap(10);
		    grid.setPadding(new Insets(5, 5, 5, 5));
		    grid.add(new Label("Name: "), 0, 1);
		    grid.add(tfName, 1, 1);
		    grid.add(btn, 2, 1);
		    box.getChildren().add(new Label("Felkerült a toplistára, kérjük írja be a nevét!"));
		    box.getChildren().add(grid);

		    Group root = (Group) scene.getRoot();
		    root.getChildren().add(box);
		    primaryStage.setScene(scene);
		}
		else {
			gc.setFill(Color.WHITE);
			gc.fillRect(220, 200, 200, 100);
			gc.setFill(Color.BLACK);
			gc.setTextAlign(TextAlignment.LEFT);
			gc.fillText("Sajnos nem került fel a toplistára.", 230, 235);
			gc.fillText("ESC - kilépés a fõmenübe", 230, 265);
			gc.fillText("Space - Új játék", 230, 250);
		}
	}

	/**
	 * A képernyõ bal felsõ sarkába kiírja a játékos pontszámát.
	 */
	public void writeScore()
	{
		gc.clearRect(570, 20, 100, 35);
		gc.setTextAlign(TextAlignment.RIGHT);
		gc.setFill(Color.BLACK);
		gc.fillText(snake.getScore().toString(), 615, 30);
	}
	
	/**
	 * A játék végén meghívja a topScore metódust, leállítja az idõzítõt.
	 */
	private void stopGame()
	{
		topScore();

		timeline.stop();
	}

	/**
	 * A képernyõt visszaállítja a fõmenüre
	 */
	private void quitGame()
	{
	    timeline.stop();
		primaryStage.setScene(mainmenu);
	}

	/**
	 * Kirajzolja a pályát, létrehozza a kígyót és kiírja a játékos pontszámát, valamint elindítja az idõzítõt.
	 */
	public void startGame()
	{
		snake = new Snake(gc, 5*Unit.BLOCK_SIZE, 5*Unit.BLOCK_SIZE, Color.BLUE);
		gc.clearRect(0, 0, APP_W, APP_H);
		gc.setStroke(Color.BLACK);
		gc.setFill(Color.BLACK);
		gc.setTextAlign(TextAlignment.LEFT);
		gc.strokeRect(Unit.BLOCK_SIZE - 1, 3 * Unit.BLOCK_SIZE - 1, 30 * Unit.BLOCK_SIZE + 2, 19 * Unit.BLOCK_SIZE + 2);
		gc.fillText("Single Player", 20, 30);
		writeScore();
		
		gc.setFill(Color.YELLOW);
		gc.fillRect(250, 450, Unit.BLOCK_SIZE, Unit.BLOCK_SIZE);
		gc.setFill(Color.BLACK);
		gc.fillText("- Food", 310, 465);
		
		gc.setFill(Color.GREEN);
		gc.fillRect(340, 450, Unit.BLOCK_SIZE, Unit.BLOCK_SIZE);
		gc.setFill(Color.BLACK);
		gc.fillText("- Poison", 410, 465);

		timeline.play();
	}
}