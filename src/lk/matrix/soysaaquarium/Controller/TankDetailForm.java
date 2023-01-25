package lk.matrix.soysaaquarium.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.sql.*;

public class TankDetailForm {
    public JFXComboBox tankComboBox;
    public JFXButton addTankButton;
    public ImageView imageId;
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

    private Connection con;
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/aquarium","root","1234");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }   //connect database
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

    public void fishTypeComboBoxOnMouseClicked(MouseEvent mouseEvent) throws SQLException {
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM fish");
        ObservableList data = FXCollections.observableArrayList();
        while(rs.next()){
            data.add(new String(rs.getString("name")));
            fishTypeComboBox.setItems(data);
        }
    }   //set-up fishType combo box


    public void tankComboBoxOnMouseClicked(MouseEvent mouseEvent) throws SQLException {
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("SELECT *\n" +
                "FROM tank\n" +
                "LEFT JOIN tankDetail\n" +
                "ON tank.tankId = tankDetail.tankId\n" +
                "WHERE tankDetail.tankId is NULL");
        ObservableList data = FXCollections.observableArrayList();
        while(rs.next()){
            data.add(rs.getString("tankId"));
            tankComboBox.setItems(data);
        }
    }   //set-up tankId combo box

    public void addTankButtonOnAction(ActionEvent actionEvent) throws SQLException {
        String query = "INSERT INTO tankDetail (tankId,fishId,fishQty,minTemp,maxTemp,minpH,maxpH,minAmo,maxAmo)" + "VALUES(?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, Integer.parseInt(tankComboBox.getSelectionModel().getSelectedItem().toString()));
            statement.setInt(2,fishId);
            statement.setInt(3,Integer.parseInt(fishCountTextField.getText()));
            statement.setDouble(4,Double.parseDouble(minTempTextField.getText()));
            statement.setDouble(5,Double.parseDouble(maxTempTextField.getText()));
            statement.setDouble(6,Double.parseDouble(minpHTextField.getText()));
            statement.setDouble(7,Double.parseDouble(maxpHTextField.getText()));
            statement.setDouble(8,Double.parseDouble(minAmoTextField.getText()));
            statement.setDouble(9,Double.parseDouble(maxAmoTextField.getText()));
            statement.execute();
            System.out.println("New Tank Added!");
            imageId.setVisible(false);
        }catch (Exception ex){
            System.out.println("add new tankdetails failed");
        }
    }   //new tank-detail adding
}