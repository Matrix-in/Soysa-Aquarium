package lk.matrix.soysaaquarium.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class AddNewTankMassageForm {

    @FXML
    private JFXButton okButton;

    @FXML
    void okButtonOnAction(ActionEvent event) {
        Stage thiStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        thiStage.hide();
    }

}
