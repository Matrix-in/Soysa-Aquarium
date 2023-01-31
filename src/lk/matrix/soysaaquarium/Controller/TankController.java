package lk.matrix.soysaaquarium.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

import static lk.matrix.soysaaquarium.Controller.DashboardPageController.stage;
import static lk.matrix.soysaaquarium.Controller.DashboardPageController.stage2;

public class TankController {

  @FXML
  private JFXButton btn1;
Stage stage;
Stage stag2;
    @FXML
    public void SendB(ActionEvent event) throws IOException {

        Stage thisStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(DashboardPageController.class.getResource("/lk/matrix/soysaaquarium/View/TankDetailForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        thisStage.setScene(scene);

//            stage = (Stage) btn1.getScene().getWindow();
//            stage.hide();
//           FXMLLoader fxmlLoader = new FXMLLoader(DashboardPageController.class.getResource("/lk/matrix/soysaaquarium/View/TankDetailForm.fxml"));
//           Scene scene = new Scene(fxmlLoader.load());
//           stage2 = new Stage();
//           stage2.setScene(scene);
//           stage2.setResizable(false);
//           stage2.show();

    }
}
