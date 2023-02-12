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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.matrix.soysaaquarium.Models.Fish;

import java.sql.*;

public class FishTypeWindowFormController {
    public Label fishTypeL;
    public Label fishIdLabel;
    public Label tankIdLabel;
    public Label fishAddedDateLabel;
    public Label fishQtyLabel;
    @FXML
    private TableView<Fish> fishTable;
    @FXML
    private TableColumn<Fish, String> tankIdCol;
    @FXML
    private TableColumn<Fish, Integer> fishQtyCol;
    @FXML
    private TableColumn<Fish, String> addedDateCol;
    @FXML
    private Label availableTanksLabel;

    @FXML
    private Label maxTempLabel;

    @FXML
    private Label maxPHLabel;

    @FXML
    private Label maxAmmoLabel;

    @FXML
    private Label minTempLabel;

    @FXML
    private Label minPHLabel;

    @FXML
    private Label minAmmoLabel;
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

    private String fishId;
    private String selected;

    public void initialize() throws SQLException, ClassNotFoundException {
        con = DBConnection.connection;
        Statement statement = con.createStatement();

        ResultSet rs = statement.executeQuery("SELECT fish.name,tankdetail.fishId\n" +
                "FROM fish\n" +
                "LEFT JOIN tankDetail\n" +
                "ON fish.fishId = tankDetail.fishId\n" +
                "GROUP BY fish.name;");
        ObservableList data = FXCollections.observableArrayList();
        while(rs.next()){
            data.add(new String(rs.getString("name")));
            fishSelectComboBox.setItems(data);
        }

        tankIdCol.setCellValueFactory(new PropertyValueFactory<Fish, String>("TankId"));
        fishQtyCol.setCellValueFactory(new PropertyValueFactory<Fish, Integer>("FishQty"));
        addedDateCol.setCellValueFactory(new PropertyValueFactory<Fish, String>("timeStamp"));
//        fishTable.setVisible(false);
    }
    @FXML
    void fishSelectComboBoxOnAction(ActionEvent event) throws SQLException {
        selected = fishSelectComboBox.getSelectionModel().getSelectedItem().toString();
        ResultSet rs = con.createStatement().executeQuery("SELECT * FROM fish WHERE name = '" + selected + "'");
        while(rs.next()) {
            fishId = rs.getString("fishId");

            fishTypeLabel.setText(rs.getString("name"));

            fishDescriptionText.setText(rs.getString("description"));

            ResultSet rs1 = con.createStatement().executeQuery("SELECT COUNT(fish.name)\n" +
                    "FROM fish\n" +
                    "LEFT JOIN tankDetail\n" +
                    "ON fish.fishId = tankDetail.fishId\n" +
                    "WHERE fish.name = '" + selected + "'");
            while(rs1.next()) availableTanksLabel.setText("Available in " + rs1.getInt(1) + " fish tanks.");

            ResultSet rs2 = con.createStatement().executeQuery("SELECT tankDetail.tankId, tankDetail.fishQty, tankDetail.addedDate\n" +
                    "FROM fish\n" +
                    "LEFT JOIN tankDetail\n" +
                    "ON fish.fishId = tankDetail.fishId\n" +
                    "WHERE fish.name = '" + selected + "'");

            ObservableList<Fish> data = FXCollections.observableArrayList();
            while(rs2.next()){
                data.add(new Fish(rs2.getString(1),rs2.getInt(2),"" + rs2.getTimestamp(3)));
            }
            fishTable.setItems(data);
            fishTable.setVisible(true);



            Image image = new Image("lk/matrix/soysaaquarium/Assets/" + rs.getString("img"));
            fishImage.setImage(image);
            fishImage.setVisible(true);
        }
    }
    @FXML
    void fishTableOnSort(ActionEvent event) {

    }
    @FXML
    void fishTableOnMouseClicked(MouseEvent event) throws SQLException {
        Fish selectedFish = fishTable.getSelectionModel().getSelectedItem();
//        double maxTemp = 0;     double minTemp = 0;
//        double maxPH = 0;     double minPH = 0;
//        double maxAmmo = 0;     double minAmmo = 0;

        new Timestamp(System.currentTimeMillis());

        fishTypeL.setText(selected);
        fishIdLabel.setText(fishId);
        tankIdLabel.setText(selectedFish.getTankId());
        fishAddedDateLabel.setText(selectedFish.getTimeStamp());
        fishQtyLabel.setText("" + selectedFish.getFishQty());
        //temperature
        ResultSet maxTempResult = con.createStatement().executeQuery("SELECT MAX(temprecords.temperature)\n" +
                "FROM tank\n" +
                "INNER JOIN temprecords\n" +
                "ON tank.tankId = temprecords.tankId\n" +
                "WHERE temprecords.tankId = '" + selectedFish.getTankId() + "'");

        while(maxTempResult.next()) {
            maxTempLabel.setText(maxTempResult.getDouble(1) != 0 ? maxTempResult.getString(1) : "N/A");
        }

        ResultSet minTempResult = con.createStatement().executeQuery("SELECT MIN(temprecords.temperature)\n" +
                "FROM tank\n" +
                "INNER JOIN temprecords\n" +
                "ON tank.tankId = temprecords.tankId\n" +
                "WHERE temprecords.tankId = '" + selectedFish.getTankId() + "'");

        while(minTempResult.next()) {
            minTempLabel.setText(minTempResult.getDouble(1) != 0 ? minTempResult.getString(1) : "N/A");
        }
        //pH
        ResultSet maxpHResult = con.createStatement().executeQuery("SELECT MAX(pHRecords.pH)\n" +
                "FROM tank\n" +
                "INNER JOIN pHRecords\n" +
                "ON tank.tankId = pHRecords.tankId\n" +
                "WHERE pHRecords.tankId = '" + selectedFish.getTankId() + "'");

        while(maxpHResult.next()) {
            maxPHLabel.setText(maxpHResult.getDouble(1) != 0 ? maxpHResult.getString(1) : "N/A");
        }

        ResultSet minpHResult = con.createStatement().executeQuery("SELECT MIN(pHRecords.pH)\n" +
                "FROM tank\n" +
                "INNER JOIN pHRecords\n" +
                "ON tank.tankId = pHRecords.tankId\n" +
                "WHERE pHRecords.tankId = '" + selectedFish.getTankId() + "'");

        while(minpHResult.next()) {
            minPHLabel.setText(minpHResult.getDouble(1) != 0 ? minpHResult.getString(1) : "N/A");
        }
        //ammonia
        ResultSet maxAmmoResult = con.createStatement().executeQuery("SELECT MAX(ammoniaRecords.ammonia)\n" +
                "FROM tank\n" +
                "INNER JOIN ammoniaRecords\n" +
                "ON tank.tankId = ammoniaRecords.tankId\n" +
                "WHERE ammoniaRecords.tankId = '" + selectedFish.getTankId() + "'");

        while(maxAmmoResult.next()) {
            maxAmmoLabel.setText(maxAmmoResult.getDouble(1) != 0 ? maxAmmoResult.getString(1) : "N/A");
        }

        ResultSet minAmmoResult = con.createStatement().executeQuery("SELECT MIN(ammoniaRecords.ammonia)\n" +
                "FROM tank\n" +
                "INNER JOIN ammoniaRecords\n" +
                "ON tank.tankId = ammoniaRecords.tankId\n" +
                "WHERE ammoniaRecords.tankId = '" + selectedFish.getTankId() + "'");

        while(minAmmoResult.next()) {
            minAmmoLabel.setText(minAmmoResult.getDouble(1) != 0 ? minAmmoResult.getString(1) : "N/A");
        }
    }

    @FXML
    void goBackBtnOnAction(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.hide();

        DashboardPageController.dashBoardStage.show();
    }

}
