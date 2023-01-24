package Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class mainController implements Initializable {
    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;
    @FXML
    private VBox vbox;
    private Parent fxml;
    String email="";
    String password="";
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        emailField.setStyle("-fx-text-fill: white;");

        emailField.setStyle("-fx-background-color: transparent;");

        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.5));
        transition.setNode(vbox);
        transition.setToX(vbox.getLayoutX() * 25);
        transition.play();
    }

    @FXML
    private void open_signIn(ActionEvent event) {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.5));
        transition.setNode(vbox);
        transition.setToX(0);
        transition.play();
        transition.setOnFinished((e) ->{
            try{
                fxml = FXMLLoader.load(getClass().getResource("/View/secWin.fxml"));
                vbox.getChildren().removeAll();
                vbox.getChildren().setAll(fxml);
            }catch(IOException ex){

            }
        });

    }
    @FXML
    public void back(MouseEvent mouseEvent) {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.5));
        transition.setNode(vbox);
        transition.setToX(vbox.getLayoutX() * 25);
        transition.play();
        transition.setOnFinished((e) ->{
            try{
                fxml = FXMLLoader.load(getClass().getResource("/View/defaultWin.fxml"));
                vbox.getChildren().removeAll();
                vbox.getChildren().setAll(fxml);
            }catch(IOException ex){

            }
        });
    }
    public void closeSys(MouseEvent mouseEvent) {
       System.exit(0);
    }
    @FXML

    public void getLogin (ActionEvent actionEvent) throws IOException {
        email=emailField.getText();
        System.out.println(email);
        password=passwordField.getText();

        System.out.println(password);
        if((email.equals("ADMIN"))&&(password.equals("ADMIN"))){
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(mainController.class.getResource("/View/DashboardPage.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
//            scene.setFill(Color.TRANSPARENT);
            Stage stage2 =new Stage();
            stage2.setScene(scene);
            stage2.setResizable(false);
//            stage2.initStyle(StageStyle.TRANSPARENT);
            stage2.show();

        }else {
            FXMLLoader fxmlLoader = new FXMLLoader(mainController.class.getResource("/View/err.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            scene.setFill(Color.TRANSPARENT);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
        }
//
//           Alert alert = new Alert(Alert.AlertType.NONE);
//           alert.setTitle("error password");
//           alert.setContentText("danna mukuth nathi nisa damme :D");
//           alert.setHeaderText("ellila kansa gahe..");
//           alert.setGraphic(new ImageView(this.getClass().getResource("/Assets/gf.gif").toString()));
//           alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
//           alert.showAndWait();

    }



    public void onActionGetEmail(ActionEvent actionEvent) {
        email=emailField.getText();
        System.out.println(email);

    }


    public void onActionGetPassword(ActionEvent actionEvent) {
        password=passwordField.getText();
        System.out.println(password);
    }
}