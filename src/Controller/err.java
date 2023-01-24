package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class err {

@FXML
private Button okBtn;
    public void closeWin(ActionEvent actionEvent) {
        Stage stage = (Stage) okBtn.getScene().getWindow();
        stage.close();
    }
}
