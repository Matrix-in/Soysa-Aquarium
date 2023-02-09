package lk.matrix.soysaaquarium.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.matrix.soysaaquarium.Controller.*;
import lk.matrix.soysaaquarium.Services.ArduinoToSql;

import java.io.IOException;
// gahuva yako commit 100yak(Gammak tamai)........................

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException{

      FXMLLoader fxmlLoader= new FXMLLoader(loginController.class.getResource("/lk/matrix/soysaaquarium/View/login_form.fxml"));
      Scene scene = new Scene(fxmlLoader.load());
      scene.setFill(Color.TRANSPARENT);
      stage.setScene(scene);
      stage.initStyle(StageStyle.TRANSPARENT);
      stage.initStyle(StageStyle.TRANSPARENT);

      stage.show();
//        FXMLLoader fxmlLoader= new FXMLLoader(ViewCameraWindowFormController.class.getResource("/lk/matrix/soysaaquarium/View/view_camera_window_form.fxml"));
//        Scene scene = new Scene(fxmlLoader.load());
//        stage.setScene(scene);
//
//        stage.show();
    }

    public static void main(String[] args) throws Exception {
//        try{
//
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/aquarium","root","1234");
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("select * from tankDetail");
//            while (resultSet.next()){
//                System.out.println(resultSet.getString("tankId"));
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        new ArduinoToSql();
        launch();


    }


}
