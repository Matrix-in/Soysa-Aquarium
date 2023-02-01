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
        Stage thisStage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();

        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.hide();

        FXMLLoader fxmlLoader1 = new FXMLLoader(loginController.class.getResource("/lk/matrix/soysaaquarium/View/dashboard_page.fxml"));

        Scene scene = new Scene(fxmlLoader1.load());
        Stage outStage =new Stage();
        outStage.setScene(scene);
        outStage.show();
    }
}
