package menu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import toplist.Result;
import toplist.Toplist;

/**
 * <h1>ToplistMenu</h1>
 * A toplist�t tartalmaz� men�.
 * <p>
 * Az oszt�ly l�trehoz egy stage-t amely egy TalbeView-ban megmutatja a toplist�ban tal�lhat� eredm�nyeket.
 * @author Knoll Zsolt
 */
public class ToplistMenu
{
	private Stage primaryStage;
	private VBox toplistbox;
	private Scene menuscene;
	private Button back;
	
	static TableView<Result> table;
	
	public static Toplist toplist = new Toplist();
	
    private final ObservableList<Result> data = FXCollections.observableArrayList(toplist.get(0), toplist.get(1), toplist.get(2), toplist.get(3), toplist.get(4));
	
    /**
     * Az oszt�ly konstruktora.
     * L�trehozza a t�bl�zatot, felt�lti a toplista adataival, �s al� besz�r egy gombot amely visszavisz a f�men�be.
     * @param ps az ablak stage-e
     * @param tlbox a men� elemeit tartalmaz� VBox
     * @param msc a f�men� scene-e
     */
	public ToplistMenu(Stage ps, VBox tlbox, Scene msc)
	{
		primaryStage = ps;
		toplistbox = tlbox;
		menuscene = msc;
		
		table = new TableView<Result>();
        TableColumn<Result, String> nameCol = new TableColumn<Result, String>("Name");
        nameCol.setMinWidth(100);
        nameCol.setCellValueFactory( new PropertyValueFactory<Result, String>("Name") );
        TableColumn<Result, Integer> scoreCol = new TableColumn<Result, Integer>("Score");
        scoreCol.setMinWidth(100);
        scoreCol.setCellValueFactory( new PropertyValueFactory<Result, Integer>("Score") );
        table.getColumns().addAll(nameCol, scoreCol);
        table.setItems(data);
        nameCol.setSortable(false);
        scoreCol.setSortType(TableColumn.SortType.DESCENDING);
		
		EventHandler<ActionEvent> ch = new ClickHandler();
		back = new Button();
		back.setText("Back");
		back.setMinSize(100, 30);
		back.setOnAction(ch);
		
        Insets ins = new Insets(10, 0, 0, 0);
		toplistbox.getChildren().add(table);
		toplistbox.getChildren().add(back);
        VBox.setMargin(back, ins);
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
		 * Ha a felhaszn�l� a vissza gombra kattint, akkor a stage scene-j�t vissza�ll�tja a f�men�re.
		 */
		public void handle(ActionEvent event)
		{
			if (event.getSource().equals(back))
			{
				primaryStage.setScene(menuscene);
			}
		}
	}
}
