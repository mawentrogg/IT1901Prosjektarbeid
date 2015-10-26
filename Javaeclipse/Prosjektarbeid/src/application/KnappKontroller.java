package application;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class KnappKontroller {
	
	private Stage dialogStage;
	
	private MainApp MainApp;
	
	@FXML
	private void initialize() {
    }
	
	public KnappKontroller() {
    }
	
	public void setMainApp(MainApp mainApp) {
        this.MainApp = mainApp;
	
	}
	
	
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
	}
	
	@FXML
	private void handleSkrivRapport(){
		MainApp.showRapport();
		
	}

}
