package lk.matrix.soysaaquarium.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import lk.matrix.soysaaquarium.Models.Fish;

import java.sql.*;

public class FishTypeWindowFormController {
    @FXML
    private TableView<Fish> fishTable;
    @FXML
    private TableColumn<Fish, Integer> tankIdCol;
    @FXML
    private TableColumn<Fish, Integer> fishQtyCol;
    @FXML
    private Label availableTanksLabel;

    @FXML
    private JFXTextArea fishDescriptionText;

    @FXML
    private ImageView fishImage;

    @FXML
    private JFXComboBox<?> fishSelectComboBox;

    @FXML
    private Label fishTypeLabel;

    @FXML
    private JFXButton goBackBtn;

    private Connection con;

    public void initialize() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/aquarium","root","1234");
        Statement statement = con.createStatement();

        ResultSet rs = statement.executeQuery("SELECT fish.name\n" +
                "FROM fish\n" +
                "LEFT JOIN tankDetail\n" +
                "ON fish.fishId = tankDetail.fishId\n" +
                "GROUP BY fish.name;");
        ObservableList data = FXCollections.observableArrayList();
        while(rs.next()){
            data.add(new String(rs.getString("name")));
            fishSelectComboBox.setItems(data);
        }

        tankIdCol.setCellValueFactory(new PropertyValueFactory<Fish, Integer>("TankId"));
        fishQtyCol.setCellValueFactory(new PropertyValueFactory<Fish, Integer>("FishQty"));
        fishTable.setVisible(false);
    }
    @FXML
    void fishSelectComboBoxOnAction(ActionEvent event) throws SQLException {
        String selected = fishSelectComboBox.getSelectionModel().getSelectedItem().toString();
        ResultSet rs = con.createStatement().executeQuery("SELECT * FROM fish WHERE name = '" + selected + "'");
        while(rs.next()) {
            fishTypeLabel.setText(rs.getString("name"));

            fishDescriptionText.setText(rs.getString("description"));

            ResultSet rs1 = con.createStatement().executeQuery("SELECT COUNT(fish.name)\n" +
                    "FROM fish\n" +
                    "LEFT JOIN tankDetail\n" +
                    "ON fish.fishId = tankDetail.fishId\n" +
                    "WHERE fish.name = '" + selected + "'");
            while(rs1.next()) availableTanksLabel.setText("Available in " + rs1.getInt(1) + " fish tanks.");

            ResultSet rs2 = con.createStatement().executeQuery("SELECT tankDetail.tankId, tankDetail.fishQty\n" +
                    "FROM fish\n" +
                    "LEFT JOIN tankDetail\n" +
                    "ON fish.fishId = tankDetail.fishId\n" +
                    "WHERE fish.name = '" + selected + "'");

            ObservableList<Fish> data = FXCollections.observableArrayList();
            while(rs2.next()){
                data.add(new Fish(rs2.getInt(1),rs2.getInt(2)));
            }
            fishTable.setItems(data);
            fishTable.setVisible(true);


            Image image = new Image("lk/matrix/soysaaquarium/Assets/" + rs.getString("img"));
            fishImage.setImage(image);
            fishImage.setVisible(true);
        }
    }

    @FXML
    void goBackBtnOnAction(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.hide();

        DashboardPageController.dashBoardStage.show();
    }

}
