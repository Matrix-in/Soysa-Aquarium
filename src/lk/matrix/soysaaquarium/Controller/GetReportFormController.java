package lk.matrix.soysaaquarium.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfWriter;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.skins.BarSkin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import com.lowagie.text.Rectangle;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;


public class GetReportFormController {
    public JFXButton backBtn;
    private Gauge allFishGauge = new Gauge();
    private Gauge allSameFishGauge = new Gauge();
    @FXML
    private Pane report;

    @FXML
    private JFXComboBox<?> tankComboBox;
    @FXML
    private JFXComboBox<?> fishIdOrTypeComboBox;

    @FXML
    private Label tankId1;

    @FXML
    private JFXRadioButton radioBtnFishType;

    @FXML
    private JFXRadioButton radioBtnFishId;

    @FXML
    private Label tankId;

    @FXML
    private Label tankId2;

    @FXML
    private Label tankId22;

    @FXML
    private Label tankId21;

    @FXML
    private Label tankId211;

    @FXML
    private Label tankId2111;
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
    private TextField textFishType;

    @FXML
    private TextField textFishId;

    @FXML
    private TextField textTankId;

    @FXML
    private TextField textFishQty;
    @FXML
    private Label fishTypeAndQtyLabel1;
    @FXML
    private TextField textAddedDate;

    @FXML
    private TextField textAllFishC;

    @FXML
    private TextField textAlllSameFishC;

    @FXML
    private VBox allFCountvBox;

    @FXML
    private VBox allSameFCountvBox;

    @FXML
    private LineChart<?, ?> lineChartTemp;
    @FXML
    private Label fishTypeAndQtyLabel;
    @FXML
    private LineChart<?, ?> lineChartpH;

    @FXML
    private LineChart<?, ?> lineChartAmmo;

    @FXML
    private JFXButton generateReportBtn;
    private String selectedIdOrType;
    private Connection con;
    ObservableList dataTankId = FXCollections.observableArrayList();
    ObservableList dataFishId = FXCollections.observableArrayList();
    ObservableList dataFishType = FXCollections.observableArrayList();

    public void initialize() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/aquarium","root","1234");
        }catch (ClassNotFoundException e) {
            System.out.println("Class not found!");
        }

        ResultSet rs1 = con.createStatement().executeQuery("SELECT * FROM fish");
        while(rs1.next()){
            dataFishType.add(new String(rs1.getString("name")));
            dataFishId.add(new String(rs1.getString("fishId")));
        }

        ResultSet rs2 = con.createStatement().executeQuery("SELECT tank.tankId\n" +
                "FROM tank\n" +
                "INNER JOIN tankdetail\n" +
                "ON tank.tankId = tankdetail.tankId;");
        while(rs2.next()){
            dataTankId.add(new String(rs2.getString("tankId")));
        }
        dataTankId = FXCollections.observableArrayList();
        ResultSet rs = con.createStatement().executeQuery("SELECT (tankId) FROM tankdetail;");
        while(rs.next()) dataTankId.add(rs.getString("tankId"));
        tankComboBox.setItems(dataTankId);

        allFishGauge.setSkin(new BarSkin(allFishGauge));
        allFishGauge.setTitle("%");
        allFishGauge.setAnimated(true);
        allFishGauge.setValueColor(Color.valueOf("#0084FF"));
        allFishGauge.setTitleColor(Color.BLACK);
        allFishGauge.setBarColor(Color.valueOf("#0084FF"));
        allFishGauge.setValue(0);

        allFCountvBox.getChildren().add(allFishGauge);


        allSameFishGauge.setSkin(new BarSkin(allSameFishGauge));
        allSameFishGauge.setTitle("%");
        allSameFishGauge.setAnimated(true);
        allSameFishGauge.setValueColor(Color.valueOf("#0084FF"));
        allSameFishGauge.setTitleColor(Color.BLACK);
        allSameFishGauge.setBarColor(Color.valueOf("#0084FF"));
        allFishGauge.setValue(0);

        allSameFCountvBox.getChildren().add(allSameFishGauge);
    }
    @FXML
    void fishIdOrTypeComboBoxOnAction(ActionEvent event) throws SQLException {
        String selected = fishIdOrTypeComboBox.getSelectionModel().getSelectedItem().toString();
        System.out.println(selected);

        ObservableList data = FXCollections.observableArrayList();
        if(radioBtnFishType.isSelected()){
            ResultSet rs = con.createStatement().executeQuery("SELECT tankdetail.tankId\n" +
                    "FROM fish\n" +
                    "LEFT JOIN tankdetail\n" +
                    "ON fish.fishId = tankdetail.fishId\n" +
                    "WHERE name = '"+ selected +"';");
            while(rs.next()) data.add(rs.getString("tankId"));
            tankComboBox.setItems(data);
        }else if(radioBtnFishId.isSelected()){
            ResultSet rs = con.createStatement().executeQuery("SELECT (tankId) FROM tankdetail WHERE fishId = '"+ selected +"'");
            while(rs.next()) data.add(rs.getString("tankId"));
            tankComboBox.setItems(data);
        }else{
            tankComboBox.setItems(this.dataTankId);
        }
    }
    @FXML
    void tankComboBoxOnMouseClicked(MouseEvent event) {
    }
    @FXML
    void radioBtnFishIdOnAction(ActionEvent event) {
        radioBtnFishType.setSelected(false);
        selectedIdOrType = "fishId";
        fishIdOrTypeComboBox.setItems(dataFishId);
    }
    @FXML
    void radioBtnFishTypeOnAction(ActionEvent event) {
        radioBtnFishId.setSelected(false);
        selectedIdOrType = "name";
        fishIdOrTypeComboBox.setItems(dataFishType);
    }

    public void generateReportBtnOnAction(ActionEvent actionEvent) {
        exportToPdf(this.report);

//        try {
//            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/aquarium","root","1234");
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM tankDetail");
//            Document document = new Document();
//            PdfWriter.getInstance(document, new FileOutputStream("report.pdf"));
//            document.open();
//            while (resultSet.next()) {
//                String tankName = resultSet.getString("tankId");
//                String fishQty = resultSet.getString("fishQty");
//                document.add(new Paragraph("Tank Name: " + tankName));
//                document.add(new Paragraph("fish Qty: " + fishQty));
//
//            }
//            document.close();
//            resultSet.close();
//            statement.close();
//            connection.close();
//        } catch (Exception e) {
//            System.out.println("Error: " + e);
//        }

    }

    public void comboBoxOnAction(ActionEvent actionEvent) throws SQLException {
        String tankId = tankComboBox.getSelectionModel().getSelectedItem().toString();
        String fishId = "";
        int fishQty = 0;
        String fishType = "";
        ResultSet rs = con.createStatement().executeQuery("SELECT * FROM tankdetail WHERE tankId = '" + tankId + "';");
        while(rs.next()) {
            fishId = rs.getString("fishId");
            textFishId.setText(fishId);
            textTankId.setText(tankId);
            fishQty = rs.getInt("fishQty");
            textFishQty.setText("" + fishQty);
            textAddedDate.setText("" + rs.getTimestamp("addedDate"));
        }

        ResultSet rs1 = con.createStatement().executeQuery("SELECT * FROM fish WHERE fishId = '"+fishId+"';");
        while(rs1.next()) fishType = rs1.getString("name");
        textFishType.setText(fishType);

        int allFishCount = 0;
        ResultSet rs2 = con.createStatement().executeQuery("SELECT SUM(fishQty) FROM tankdetail;");
        while(rs2.next()) allFishCount = rs2.getInt(1);
        fishTypeAndQtyLabel1.setText("" + allFishCount);

        allFishGauge.setVisible(true);
        double percentage1 = (fishQty *  100d) / allFishCount;
        allFishGauge.setValue(percentage1);


        int allSameFishC = 0;
        ResultSet rs3 = con.createStatement().executeQuery("SELECT SUM(fishQty) FROM tankdetail WHERE fishId = '"+fishId+"';");
        while(rs3.next()) allSameFishC = rs3.getInt(1);
        fishTypeAndQtyLabel.setText("" + allSameFishC);

        allSameFishGauge.setVisible(true);
        double percentage2 = (fishQty *  100d) / allSameFishC;
        allSameFishGauge.setValue(percentage2);


        XYChart.Series series1 = new XYChart.Series();
        ResultSet tempRs = con.createStatement().executeQuery("SELECT *\n" +
                "FROM tempRecords\n" +
                "WHERE (tankId = '" + tankId + "') &&\n" +
                "(month(timeStampTemp) = 1);");
        int i = 1;
        while(tempRs.next()){
            System.out.println(i);
            System.out.println(tempRs.getDouble("temperature"));
            series1.getData().add(new XYChart.Data("" + i,tempRs.getDouble("temperature")));
            i++;
        }
        series1.setName("Tempature");
        lineChartTemp.getData().clear();
        lineChartTemp.getData().add(series1);


        XYChart.Series series2 = new XYChart.Series();
        ResultSet pHRs = con.createStatement().executeQuery("SELECT *\n" +
                "FROM pHRecords\n" +
                "WHERE (tankId = '" + tankId + "') &&\n" +
                "(month(timeStampPH) = 1);");
        int j = 1;
        while(pHRs.next()){
            System.out.println(j);
            System.out.println(pHRs.getDouble("pH"));
            series2.getData().add(new XYChart.Data("" + j,pHRs.getDouble("pH")));
            j++;
        }
        series2.setName("pH");
        lineChartpH.getData().clear();
        lineChartpH.getData().add(series2);


        XYChart.Series series3 = new XYChart.Series();
        ResultSet ammoRs = con.createStatement().executeQuery("SELECT *\n" +
                "FROM ammoniarecords\n" +
                "WHERE (tankId = '" + tankId + "') &&\n" +
                "(month(timeStampAmmo) = 1);");
        int k = 1;
        while(ammoRs.next()){
            System.out.println(k);
            System.out.println(ammoRs.getDouble("ammonia"));
            series3.getData().add(new XYChart.Data("" + k,ammoRs.getDouble("ammonia")));
            k++;
        }
        series3.setName("Ammonia");
        lineChartAmmo.getData().clear();
        lineChartAmmo.getData().add(series3);


        ResultSet maxTempResult = con.createStatement().executeQuery("SELECT MAX(temprecords.temperature)\n" +
                "FROM tank\n" +
                "INNER JOIN temprecords\n" +
                "ON tank.tankId = temprecords.tankId\n" +
                "WHERE temprecords.tankId = '" + tankId + "'");

        while(maxTempResult.next()) {
            maxTempLabel.setText(maxTempResult.getDouble(1) != 0 ? maxTempResult.getString(1) : "N/A");
        }

        ResultSet minTempResult = con.createStatement().executeQuery("SELECT MIN(temprecords.temperature)\n" +
                "FROM tank\n" +
                "INNER JOIN temprecords\n" +
                "ON tank.tankId = temprecords.tankId\n" +
                "WHERE temprecords.tankId = '" + tankId + "'");

        while(minTempResult.next()) {
            minTempLabel.setText(minTempResult.getDouble(1) != 0 ? minTempResult.getString(1) : "N/A");
        }
        //pH
        ResultSet maxpHResult = con.createStatement().executeQuery("SELECT MAX(pHRecords.pH)\n" +
                "FROM tank\n" +
                "INNER JOIN pHRecords\n" +
                "ON tank.tankId = pHRecords.tankId\n" +
                "WHERE pHRecords.tankId = '" + tankId + "'");

        while(maxpHResult.next()) {
            maxPHLabel.setText(maxpHResult.getDouble(1) != 0 ? maxpHResult.getString(1) : "N/A");
        }

        ResultSet minpHResult = con.createStatement().executeQuery("SELECT MIN(pHRecords.pH)\n" +
                "FROM tank\n" +
                "INNER JOIN pHRecords\n" +
                "ON tank.tankId = pHRecords.tankId\n" +
                "WHERE pHRecords.tankId = '" + tankId + "'");

        while(minpHResult.next()) {
            minPHLabel.setText(minpHResult.getDouble(1) != 0 ? minpHResult.getString(1) : "N/A");
        }
        //ammonia
        ResultSet maxAmmoResult = con.createStatement().executeQuery("SELECT MAX(ammoniaRecords.ammonia)\n" +
                "FROM tank\n" +
                "INNER JOIN ammoniaRecords\n" +
                "ON tank.tankId = ammoniaRecords.tankId\n" +
                "WHERE ammoniaRecords.tankId = '" + tankId + "'");

        while(maxAmmoResult.next()) {
            maxAmmoLabel.setText(maxAmmoResult.getDouble(1) != 0 ? maxAmmoResult.getString(1) : "N/A");
        }

        ResultSet minAmmoResult = con.createStatement().executeQuery("SELECT MIN(ammoniaRecords.ammonia)\n" +
                "FROM tank\n" +
                "INNER JOIN ammoniaRecords\n" +
                "ON tank.tankId = ammoniaRecords.tankId\n" +
                "WHERE ammoniaRecords.tankId = '" + tankId + "'");

        while(minAmmoResult.next()) {
            minAmmoLabel.setText("" + minAmmoResult.getDouble(1));
            System.out.println("sout");
        }
    }

    private void exportToPdf(Pane report) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File file = fileChooser.showSaveDialog(new Stage());

            if (file != null) {
                WritableImage image = report.snapshot(null, null);
                BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bImage, "png", bos);
                Image imageFromStream = Image.getInstance(bos.toByteArray());

                float width = imageFromStream.getWidth();
                float height = imageFromStream.getHeight();

                Document document = new Document(new Rectangle(width, height), 5, 5, 5, 5);
                PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();
                imageFromStream.scaleAbsolute(imageFromStream.getWidth(), imageFromStream.getHeight());
                document.add(imageFromStream);
                document.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void backBtnOnAction(ActionEvent actionEvent) {
        //to do
    }
}

