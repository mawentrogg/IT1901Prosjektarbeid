package gui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class NewUserController {

    @FXML
    private TextField newName;

    @FXML
    private TextField newPhone;

    @FXML
    private TextField newEpost;

    @FXML
    private TextField newUsername;

    @FXML
    private PasswordField newPass1;

    @FXML
    private PasswordField newPass2;

    @FXML
    void registerNewUser(ActionEvent event) {
    	System.out.println(newName.getText()+newPhone.getText());

    }
}