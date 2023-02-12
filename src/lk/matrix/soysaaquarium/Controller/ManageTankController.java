package lk.matrix.soysaaquarium.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.matrix.soysaaquarium.Models.Tank;

import java.io.IOException;
import java.sql.*;
public class ManageTankController {


    public TableColumn colC1;
    public TableColumn colC2;
    public TableColumn colC3;
    @FXML
    private TableView<Tank> tankTable;

    @FXML
    private AnchorPane bgPane;

    @FXML
    public void AddNewPageBtnOnAction(ActionEvent event) throws IOException {

        Node node;
        node = (Node)FXMLLoader.load(DashboardPageController.class.getResource("/lk/matrix/soysaaquarium/View/add_new_tank_form.fxml"));
        DashboardPageController.dashBoardMainPane.getChildren().setAll(node);

    }

    @FXML
    public void initialize() {

        visualizeColumn();
        ObservableList<Tank> data = FXCollections.observableArrayList();


        try{
            Connection connection = DBConnection.connection;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT tankDetail.tankId , fish.name FROM tankDetail INNER JOIN fish ON tankDetail.fishId = fish.fishId;");
            while (resultSet.next()){
                System.out.println(resultSet.getString("tankId")+" - "+resultSet.getString("name"));
                data.add(new Tank(resultSet.getString("tankId"), resultSet.getString("name")));
            }

        }catch (Exception e){
            e.printStackTrace();
        }


        tankTable.setItems(data);
    }

    public void visualizeColumn(){
        colC1.setCellValueFactory(new PropertyValueFactory<Tank,String>("tankId"));
        colC2.setCellValueFactory(new PropertyValueFactory<Tank,String>("fishType"));
        colC3.setCellValueFactory(new PropertyValueFactory<Tank,JFXButton>("bin"));
    }
    @FXML
    void onBack(ActionEvent event) {
        try {
            Parent view = FXMLLoader.load(this.getClass().getResource("/lk/matrix/soysaaquarium/View/dashboard_page.fxml"));
            Stage primaryStage = (Stage) bgPane.getScene().getWindow();
            Scene scene = new Scene(view);
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
