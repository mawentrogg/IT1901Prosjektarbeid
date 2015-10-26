package guiFixed;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class ReportMenuController {
	
	//TODO Update showReportMenu
	
	private String Username;
	private MainApp MainApp;
	
	

	@FXML
	private TableView<?> table;
	
    @FXML
    private TableColumn<?, ?> tableDate;

    @FXML
    private TableColumn<?, ?> tableKoie;

    @FXML
    private TextArea textAreaComment;

    @FXML
    private CheckBox axe;

    @FXML
    private CheckBox saw;

    @FXML
    private CheckBox bench;

    @FXML
    private CheckBox spade;

    @FXML
    private CheckBox lamp;

    @FXML
    private CheckBox lampGlass;

    @FXML
    private CheckBox detergent;

    @FXML
    private CheckBox brush;

    @FXML
    private CheckBox dishRag;

    @FXML
    private CheckBox kitchenRag;

    @FXML
    private CheckBox cutleries;

    @FXML
    private CheckBox cupsAndPlates;

    @FXML
    private CheckBox primus;

    @FXML
    private CheckBox needles;

    @FXML
    private CheckBox wick;

    @FXML
    private CheckBox pan;

    @FXML
    private CheckBox pot;

    @FXML
    private CheckBox mixer;

    @FXML
    private CheckBox book;

    @FXML
    private CheckBox hammer;

    @FXML
    private CheckBox rule;

    @FXML
    private CheckBox blanket;

    @FXML
    private CheckBox fireExtinguisher;

    @FXML
    private Label datoLabel;

    @FXML
    private Label koieLabel;

    @FXML
    private Button showPrognosis;
    
    

    @FXML
    void showPrognosis(ActionEvent event) {
    	MainApp.showPrognosis();

    }

	public void setMainApp(MainApp mainApp) {
		this.MainApp = mainApp;
		
	}
	
	public void setUsername(String username){
		this.Username = username;
	}
	public void closeWindow(ActionEvent event){
		MainApp.closePopup2();
	}

}
