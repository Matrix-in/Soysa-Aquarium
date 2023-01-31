package lk.matrix.soysaaquarium.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.*;

public class TankDetailFormController {
    public JFXComboBox tankComboBox;
    public JFXButton addTankButton;
    public ImageView imageId;
    public Label fishCountLabel;
    public JFXButton backButton;
    @FXML
    private TextField fishCountTextField;
    @FXML
    private TextField maxAmoTextField;

    @FXML
    private TextField maxTempTextField;

    @FXML
    private TextField maxpHTextField;

    @FXML
    private TextField minAmoTextField;

    @FXML
    private TextField minTempTextField;

    @FXML
    private TextField minpHTextField;
    @FXML
    private JFXComboBox<?> fishTypeComboBox;
    private int fishId;
    @FXML
    private AnchorPane bgPaneTdf;
    private Connection con;
    static int count;
    public void initialize() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/aquarium","root","1234");
        }catch (ClassNotFoundException e) {
            System.out.println("Class not found!");
        }
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM fish");
        ObservableList data = FXCollections.observableArrayList();
        while(rs.next()){
            data.add(new String(rs.getString("name")));
            fishTypeComboBox.setItems(data);
        }
//        if(!(DashboardPageController.isSelected)){
//            bgPaneTdf.setStyle("-fx-background-color: black");
//        } else if(DashboardPageController.isSelected){
//            bgPaneTdf.setStyle("-fx-background-color: white");
//        }
    }  //connect database

    public void fishTypeComboBoxOnAction(ActionEvent actionEvent) throws SQLException{
        String selected = fishTypeComboBox.getSelectionModel().getSelectedItem().toString();
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM fish WHERE name = '" + selected + "'");
        while (rs.next()){
            try{
                fishId = Integer.parseInt(rs.getString("fishId"));
                minTempTextField.setText(String.valueOf(rs.getDouble("minTemp")));
                maxTempTextField.setText(String.valueOf(rs.getDouble("maxTemp")));
                minpHTextField.setText(String.valueOf(rs.getDouble("minPH")));
                maxpHTextField.setText(String.valueOf(rs.getDouble("maxPH")));
                minAmoTextField.setText(String.valueOf(rs.getDouble("minAmo")));
                maxAmoTextField.setText(String.valueOf(rs.getDouble("maxAmo")));


                Image image = new Image("lk/matrix/soysaaquarium/Assets/" + rs.getString("img"));
                imageId.setImage(image);
                imageId.setVisible(true);
            }catch (Exception ex){
                System.out.println("Error");
            }
        }
    }   //autofill text-fields

    public void tankComboBoxOnMouseClicked(MouseEvent mouseEvent) throws SQLException {
        count = 0;
        ResultSet rs1 = con.createStatement().executeQuery("SELECT COUNT(tank.tankId)\n" +
                "FROM tank \n" +
                "LEFT JOIN tankDetail ON tank.tankId = tankDetail.tankId\n" +
                "WHERE tankDetail.tankId is NULL;");
        while(rs1.next()) count += rs1.getInt(1);

        if(count != 0) {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT *\n" +
                    "FROM tank\n" +
                    "LEFT JOIN tankDetail\n" +
                    "ON tank.tankId = tankDetail.tankId\n" +
                    "WHERE tankDetail.tankId is NULL");
            ObservableList data = FXCollections.observableArrayList();
            while (rs.next()) {
                data.add(rs.getString("tankId"));
                tankComboBox.setItems(data);
            }
        }else{
            ObservableList data = FXCollections.observableArrayList();
            data.add("No tanks to add!");
            tankComboBox.setItems(data);
        }
    }   //set-up tankId combo box

    public void addTankButtonOnAction(ActionEvent actionEvent) throws SQLException {
        if(fishCountTextField.getLength() == 0 || minpHTextField.getLength() == 0 || tankComboBox.getValue() == null || count == 0){
            fishCountLabel.setText("*fields are empty!");
        }else{
            try{
                Integer.parseInt(fishCountTextField.getText());
                String query = "INSERT INTO tankDetail (tankId,fishId,fishQty,minTemp,maxTemp,minpH,maxpH,minAmo,maxAmo)" + "VALUES(?,?,?,?,?,?,?,?,?)";
                try {
                    PreparedStatement statement = con.prepareStatement(query);
                    statement.setInt(1, Integer.parseInt(tankComboBox.getSelectionModel().getSelectedItem().toString()));
                    statement.setInt(2, fishId);
                    statement.setInt(3, Integer.parseInt(fishCountTextField.getText()));
                    statement.setDouble(4, Double.parseDouble(minTempTextField.getText()));
                    statement.setDouble(5, Double.parseDouble(maxTempTextField.getText()));
                    statement.setDouble(6, Double.parseDouble(minpHTextField.getText()));
                    statement.setDouble(7, Double.parseDouble(maxpHTextField.getText()));
                    statement.setDouble(8, Double.parseDouble(minAmoTextField.getText()));
                    statement.setDouble(9, Double.parseDouble(maxAmoTextField.getText()));
                    statement.execute();
                    imageId.setVisible(false);
                    fishCountLabel.setText("");

                    showMassage();
                    clearFields();
                } catch (Exception ex) {
                    System.out.println("add new tankdetails failed");
                }
            }catch (NumberFormatException e){
                fishCountLabel.setText("Wrong input in fish count");
            }

        }
    }   //new tank-detail adding
    public void showMassage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AddNewTankMassageForm.class.getResource("/lk/matrix/soysaaquarium/View/addNewTankMassageForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.setFill(Color.TRANSPARENT);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.show();
    }
    public void clearFields(){
        tankComboBox.setValue(null);
        fishTypeComboBox.setValue(null);
        minTempTextField.setText(null);
        maxTempTextField.setText(null);
        minpHTextField.setText(null);
        maxpHTextField.setText(null);
        minAmoTextField.setText(null);
        maxAmoTextField.setText(null);
        fishCountTextField.setText(null);
    }

    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {

        Stage thisStage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(DashboardPageController.class.getResource("/lk/matrix/soysaaquarium/View/DashboardPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        thisStage.setScene(scene);

//        Stage stage = (Stage) backButton.getScene().getWindow();
//        stage.hide();
//
//        FXMLLoader fxmlLoader = new FXMLLoader(DashboardPageController.class.getResource("/lk/matrix/soysaaquarium/View/TankView.fxml"));
//
//        FXMLLoader fxmlLoader1 = new FXMLLoader(mainController.class.getResource("/lk/matrix/soysaaquarium/View/DashboardPage.fxml"));
//
//        Scene scene = new Scene(fxmlLoader1.load());
//        Stage outStage =new Stage();
//        outStage.setScene(scene);
//        outStage.show();
    }
}
