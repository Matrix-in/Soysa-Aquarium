package lk.matrix.soysaaquarium.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class InfoWindowFormController {
    @FXML
    private Button backBtn;


    public void backBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.hide();

        DashboardPageController.dashBoardStage.show();
    }
}
