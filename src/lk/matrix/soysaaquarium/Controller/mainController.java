package lk.matrix.soysaaquarium.Controller;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class mainController implements Initializable {
    public PasswordField passwordField;
    public Button loginBtn;
    @FXML
    private TextField userName;

    @FXML
    private VBox vbox;
    private Parent fxml;
    String username ="";
    String password="";
    static Stage stage;
    Stage stage2;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userName.setStyle("-fx-text-fill: white;");
        userName.setStyle("-fx-background-radius: 10px;");

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
                fxml = FXMLLoader.load(getClass().getResource("/lk/matrix/soysaaquarium/View/login_sec_win.fxml"));
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
                fxml = FXMLLoader.load(getClass().getResource("/lk/matrix/soysaaquarium/View/login_default_win.fxml"));
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
        username = userName.getText();
        password=passwordField.getText();
        int count = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/aquarium","root","1234");

            String query = "select count(name) from user where name = '" + username + "' and type = '" + password + "'";
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(query);

            while(rs.next()){
                count += rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("Error : "+e);
        }

        if(count == 1){
            if(stage2 == null) {
                stage = (Stage) userName.getScene().getWindow();
                stage.hide();
                FXMLLoader fxmlLoader = new FXMLLoader(DashboardPageController.class.getResource("/lk/matrix/soysaaquarium/View/dashboard_page.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage2 = new Stage();
                stage2.setScene(scene);
                //stage2.setResizable(false);
                stage2.show();
            }else{
                stage = (Stage) userName.getScene().getWindow();
                stage.hide();
                stage2.show();
            }

        }else {
            FXMLLoader fxmlLoader = new FXMLLoader(mainController.class.getResource("/lk/matrix/soysaaquarium/View/popup_login_err.fxml"));
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
        username = userName.getText();
    }


    public void onActionGetPassword(ActionEvent actionEvent) {
        password=passwordField.getText();
        loginBtn.fire();
    }
}