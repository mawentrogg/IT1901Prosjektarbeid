package gui;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class KnappKontroller {
	
	private Stage dialogStage;
	
	private MainApp MainApp;
	private TestApp TestApp;
	
	@FXML
	private void initialize() {
    }
	
	public KnappKontroller() {
    }
	
	public void setMainApp(MainApp mainApp) {
        this.MainApp = mainApp;
	}
	
	public void setTestApp(TestApp testApp) {
        this.TestApp = testApp;
	}
	
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
	}
	
	@FXML
	private void handleSkrivRapport(){
//		MainApp.showRapport();
		MainApp.showLogin();
		
	}

}
