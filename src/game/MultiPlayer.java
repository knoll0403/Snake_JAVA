package game;

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * <h1>MultiPlayer</h1>
 * Az t�bbj�t�kos m�d oszt�lya
 * <p>
 * Ez az oszt�ly futtatja az t�bbj�t�kos m�dot. 
 * 
 * @author Knoll Zsolt
 */
public class MultiPlayer
{	
	public static final int APP_W = 640;
	public static final int APP_H = 480;
	
	private Stage primaryStage;
	private Scene mainmenu;
	private GraphicsContext gc;

	private boolean live1 = true, live2 = true, inIt;
	
	private Timeline timeline = new Timeline();
	
	private Snake snake1, snake2;
	private Food food;
	private Poison poison;

	/**
	 * Az oszt�ly konstruktora.
	 * L�trehozza a grafikus fel�letet, amelyet felk�sz�t a j�t�k futtat�s�ra. Itt tal�lhat� a j�t�k ir�ny�t�s�hoz sz�ks�ges met�dus.
	 * Legv�g�l pedig elind�tja a j�t�kot.
	 * 
	 * @param pStage az ablak stage-e
	 * @param menu a f�men� scene-je
	 */
	public MultiPlayer(Stage pStage, Scene menu)
	{
		primaryStage = pStage;
		mainmenu = menu;
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
					if (snake1.getDirection() != Snake.Direction.DOWN) 
						snake1.setNextDir(Snake.Direction.UP);
					break;
				case A:
					if (snake1.getDirection() != Snake.Direction.RIGHT)
						snake1.setNextDir(Snake.Direction.LEFT);
					break;
				case S:
					if (snake1.getDirection() != Snake.Direction.UP)
						snake1.setNextDir(Snake.Direction.DOWN);
					break;
				case D:
					if (snake1.getDirection() != Snake.Direction.LEFT)
						snake1.setNextDir(Snake.Direction.RIGHT);
					break;
				case UP:
					if (snake2.getDirection() != Snake.Direction.DOWN)
						snake2.setNextDir(Snake.Direction.UP);
					break;
				case LEFT:
					if (snake2.getDirection() != Snake.Direction.RIGHT)
						snake2.setNextDir(Snake.Direction.LEFT);
					break;
				case DOWN:
					if (snake2.getDirection() != Snake.Direction.UP)
						snake2.setNextDir(Snake.Direction.DOWN);
					break;
				case RIGHT:
					if (snake2.getDirection() != Snake.Direction.LEFT)
						snake2.setNextDir(Snake.Direction.RIGHT);
					break;
				case SPACE:
					stopGame();
					startGame();
					break;
				case ESCAPE:
					stopGame();
					quitGame();
					break;
			}
		});

		primaryStage.setScene(scene);
		primaryStage.show();
		startGame();
	}
	
	/**
	 * Ez a f�ggv�ny futtatja a j�t�k ciklus�t.
	 * Az elej�n egy �telt helyez el a p�ly�n, 0.2 sec-enk�nt l�pteti a k�gy�kat, ellen�rizve, hogy m�g �letben vannak-e,
	 * illetve sz�ks�g eset�n �j �telt helyez el a k�perny�n. Ha az egyik k�gy� meghalt kil�p a ciklusb�l.
	 */
	public void createContent()
	{
		Pane root = new Pane();
		root.setPrefSize(APP_W, APP_H);
		
		food = new Food(gc);

		Random rand = new Random();
		
		KeyFrame frame = new KeyFrame(Duration.seconds(0.2), event-> {
			if (!live1 || !live2)
				return;
			
			snake1.setDirection(snake1.getNextDir());
			snake2.setDirection(snake2.getNextDir());
			snake1.move();
			snake2.move();
			
			if(!snake1.isAlive())
				live1 = false;
			if(!snake2.isAlive())
				live2 = false;
			if(snake1.inSnake(snake2.getHead()))
				live2 = false;
			if(snake2.inSnake(snake1.getHead()))
				live1 = false;
			if(snake1.getHead().getX() == snake2.getHead().getX() && snake1.getHead().getY() == snake2.getHead().getY())
			{
				live1 = false;
				live2 = false;
			}
			
			if(!live1)
			{
				for (int i = 0; i < 5; i++)
					snake2.addScore();
				gc.clearRect(570, 20, 100, 35);
				gc.setTextAlign(TextAlignment.RIGHT);
				gc.setFill(snake2.getColor());
				gc.fillText(snake2.getScore().toString(), 615, 30);
			}
			if(!live2)
			{
				for (int i = 0; i < 5; i++)
					snake1.addScore();
				gc.clearRect(50, 20, 100, 35);
				gc.setTextAlign(TextAlignment.RIGHT);
				gc.setFill(snake1.getColor());
				gc.fillText(snake1.getScore().toString(), 95, 30);
			}
			
			if(!live1 || !live2)
			{
				stopGame();
				drawResult();
				return;
			}
			
			inIt = true;
			int x = rand.nextInt(75);
			if (x % 75 == 0 && poison == null)
			{
				while (inIt)
				{
					poison = new Poison(gc);
					inIt = snake1.inSnake(poison.getUnit()) || snake2.inSnake(poison.getUnit()) ||
							(food.getUnit().getX() == poison.getUnit().getX() && food.getUnit().getY() == poison.getUnit().getY());
					if (inIt)
						poison.delete();
				}

			}

			inIt = true;
			if (eat(snake1.getHead(), food.getUnit()))
			{
				snake1.addScore();
				gc.clearRect(50, 20, 100, 35);
				gc.setTextAlign(TextAlignment.RIGHT);
				gc.setFill(snake1.getColor());
				gc.fillText(snake1.getScore().toString(), 95, 30);
				while (inIt)
				{
					food = new Food(gc);
					inIt = snake1.inSnake(food.getUnit()) || snake2.inSnake(food.getUnit()) ||
							( poison != null && food.getUnit().getX() == poison.getUnit().getX() && food.getUnit().getY() == poison.getUnit().getY());
					if (inIt)
						food.delete();
				}
				
				Unit un = new Unit(gc, snake1.getHead().getX(), snake1.getHead().getY(), snake1.getColor());
				snake1.add(un);
			}
			
			inIt = true;
			if (eat(snake2.getHead(), food.getUnit()))
			{
				snake2.addScore();
				gc.clearRect(570, 20, 100, 35);
				gc.setTextAlign(TextAlignment.RIGHT);
				gc.setFill(snake2.getColor());
				gc.fillText(snake2.getScore().toString(), 615, 30);
				while (inIt)
				{
					food = new Food(gc);
					inIt = snake1.inSnake(food.getUnit()) || snake2.inSnake(food.getUnit()) ||
							( poison != null && food.getUnit().getX() == poison.getUnit().getX() && food.getUnit().getY() == poison.getUnit().getY());
					if (inIt)
						food.delete();
				}
				
				Unit un = new Unit(gc, snake2.getHead().getX(), snake2.getHead().getY(), snake2.getColor());
				snake2.add(un);
			}
			
			inIt = true;
			if (poison != null && eat(snake1.getHead(), poison.getUnit()))
			{
				snake1.decScore();
				gc.clearRect(50, 20, 100, 35);
				gc.setTextAlign(TextAlignment.RIGHT);
				gc.setFill(snake1.getColor());
				gc.fillText(snake1.getScore().toString(), 95, 30);
				poison.delete();
				poison = null;
			}
			
			inIt = true;
			if (poison != null && eat(snake2.getHead(), poison.getUnit()))
			{
				snake2.decScore();
				gc.clearRect(570, 20, 100, 35);
				gc.setTextAlign(TextAlignment.RIGHT);
				gc.setFill(snake2.getColor());
				gc.fillText(snake2.getScore().toString(), 615, 30);
				poison.delete();
				poison = null;
			}

			//Kirajzolunk mindent
			snake1.draw();
			snake2.draw();
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
		});
		
		timeline.getKeyFrames().add(frame);
		timeline.setCycleCount(Timeline.INDEFINITE);
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
	 * A j�t�k v�g�n ki�rja a k�perny� k�zep�re, hogy melyik j�t�kos nyert.
	 */
	private void drawResult()
	{
		if(snake1.getScore() > snake2.getScore())
		{
			gc.setFill(Color.WHITE);
			gc.fillRect(220, 200, 200, 100);
			gc.setFill(snake1.getColor());
			gc.setTextAlign(TextAlignment.LEFT);
			gc.fillText("Gratul�lunk az 1. j�t�kos nyert", 230, 235);
		}
		else if(snake2.getScore() > snake1.getScore())
		{
			gc.setFill(Color.WHITE);
			gc.fillRect(220, 200, 200, 100);
			gc.setFill(snake2.getColor());
			gc.setTextAlign(TextAlignment.LEFT);
			gc.fillText("Gratul�lunk a 2. j�t�kos nyert", 230, 235);
		}
		else
		{
			gc.setFill(Color.WHITE);
			gc.fillRect(220, 200, 200, 100);
			gc.setFill(Color.BLACK);
			gc.setTextAlign(TextAlignment.LEFT);
			gc.fillText("A j�t�k d�ntetlennel v�gz�d�tt", 230, 235);
		}
		gc.fillText("ESC - kil�p�s a f�men�be", 230, 250);
	}
	
	/**
	 * A j�t�k v�g�n le�ll�tja az id�z�t�t, valamint a k�perny�t vissza�ll�tja a f�men�re.
	 */
	private void stopGame()
	{
		timeline.stop();
	}
	
	private void quitGame()
	{
		timeline.stop();
		primaryStage.setScene(mainmenu);
	}
	
	/**
	 * Kirajzolja a p�ly�t, l�trehozza a k�gy�kat �s ki�rja a j�t�kosok pontsz�m�t, valamint elind�tja az id�z�t�t.
	 */
	public void startGame()
	{
		snake1 = new Snake(gc, 5*Unit.BLOCK_SIZE, 5*Unit.BLOCK_SIZE, Color.BLUE);
		snake2 = new Snake(gc, 5*Unit.BLOCK_SIZE, 15*Unit.BLOCK_SIZE, Color.RED);
		live1 = true;
		live2 = true;
		
		gc.clearRect(0, 0, APP_W, APP_H);
		gc.setStroke(Color.BLACK);
		gc.setTextAlign(TextAlignment.LEFT);
		gc.strokeRect(Unit.BLOCK_SIZE - 1, 3 * Unit.BLOCK_SIZE - 1, 30 * Unit.BLOCK_SIZE + 2, 19 * Unit.BLOCK_SIZE + 2);
		gc.setFill(snake1.getColor());
		gc.fillText("Score", 20, 30);
		gc.setFill(snake2.getColor());
		gc.fillText("Score", 540, 30);
		gc.setTextAlign(TextAlignment.RIGHT);
		gc.setFill(snake1.getColor());
		gc.fillText(snake1.getScore().toString(), 95, 30);
		gc.setFill(snake2.getColor());
		gc.fillText(snake2.getScore().toString(), 615, 30);
		
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