package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Controller.tankViewController;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(tankViewController.class.getResource("/View/tankViewForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage2 =new Stage();
        stage2.setScene(scene);
        stage2.show();

    }
    public static void main(String[] args) {

        launch();

    }
}
