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
 * Az egyj�t�kos m�d oszt�lya
 * <p>
 * Ez az oszt�ly futtatja az egyj�t�kos m�dot. 
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
	 * Az oszt�ly konstruktora.
	 * L�trehozza a grafikus fel�letet, amelyet felk�sz�t a j�t�k futtat�s�ra. Itt tal�lhat� a j�t�k ir�ny�t�s�hoz sz�ks�ges met�dus.
	 * Legv�g�l pedig elind�tja a j�t�kot.
	 * 
	 * @param pStage az ablak stage-e
	 * @param menu a f�men� scene-je
	 * @param tl a j�t�k toplist�ja
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
	 * Ez a f�ggv�ny futtatja a j�t�k ciklus�t.
	 * Az elej�n egy �telt helyez el a p�ly�n, 0.2 sec-enk�nt l�pteti a k�gy�t, ellen�rizve, hogy m�g �letben van-e,
	 * illetve sz�ks�g eset�n �j �telt helyez el a k�perny�n. Ha a k�gy� meghalt kil�p a ciklusb�l.
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
	 * �j �telt helyez el a p�ly�n, vigy�zva, hogy az �tel ne ker�lj�n a k�gy� test�be, �s a m�reggel se ker�lj�n egy mez�re.
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
	 * V�letlen id�k�z�nk�nt m�rget helyez el a p�ly�n, figyelve, hogy a m�reg ne legyen benne a k�gy�ban �s az �tellel se ker�lj�n egy mez�re.
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
	 * Megn�zi, hogy a k�gy� feje megette-e a param�terk�nt kapott �telt vagy m�rget.
	 * 
	 * @param head a k�gy� feje
	 * @param thing az �tel vagy m�reg.
	 * @return true, ha a head �s a thing is ugyanazon a mez�n szerepel.
	 */
	public boolean eat(Unit head, Unit thing)
	{
		return head.getX() == thing.getX() && head.getY() == thing.getY();
	}
	
	/**
	 * A j�t�k v�g�n a j�t�kos pontsz�m�t �sszehasonl�tja a toplista legkisebb pontsz�m�val.
	 * Ha a j�t�kos felker�lt a toplist�ra, bek�ri a nev�t, majd az eredm�nyt a n�vvel egy�tt elmenti,
	 * k�l�nben pedig egy felirat jelenik meg a k�perny�n, majd az ESC gomb megnyom�s�val visszaker�l�nk a f�men�be.
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
		    box.getChildren().add(new Label("Felker�lt a toplist�ra, k�rj�k �rja be a nev�t!"));
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
			gc.fillText("Sajnos nem ker�lt fel a toplist�ra.", 230, 235);
			gc.fillText("ESC - kil�p�s a f�men�be", 230, 265);
			gc.fillText("Space - �j j�t�k", 230, 250);
		}
	}

	/**
	 * A k�perny� bal fels� sark�ba ki�rja a j�t�kos pontsz�m�t.
	 */
	public void writeScore()
	{
		gc.clearRect(570, 20, 100, 35);
		gc.setTextAlign(TextAlignment.RIGHT);
		gc.setFill(Color.BLACK);
		gc.fillText(snake.getScore().toString(), 615, 30);
	}
	
	/**
	 * A j�t�k v�g�n megh�vja a topScore met�dust, le�ll�tja az id�z�t�t.
	 */
	private void stopGame()
	{
		topScore();

		timeline.stop();
	}

	/**
	 * A k�perny�t vissza�ll�tja a f�men�re
	 */
	private void quitGame()
	{
	    timeline.stop();
		primaryStage.setScene(mainmenu);
	}

	/**
	 * Kirajzolja a p�ly�t, l�trehozza a k�gy�t �s ki�rja a j�t�kos pontsz�m�t, valamint elind�tja az id�z�t�t.
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