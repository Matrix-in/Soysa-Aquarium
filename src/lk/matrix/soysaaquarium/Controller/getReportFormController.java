package lk.matrix.soysaaquarium.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfWriter;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import com.lowagie.text.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;



public class getReportFormController {
    @FXML
    private JFXButton generateReportBtn;
    @FXML
    private JFXComboBox<String> tankComboBox;
    private int[] tankIdArr = new int[0];
    private double[] temperatureData = new double[0];
    private String[] timeStampData = new String[0];
    private double latestTemp=0.0;
    @FXML
    private Label tankId;
    @FXML
    private Pane report;

    public void initialize(){
        try{

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/aquarium","root","1234");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from tankDetail");
            while (resultSet.next()){
                int[] tempArray = new int[tankIdArr.length+1];
                for (int i = 0; i < tankIdArr.length; i++){
                    tempArray[i] = tankIdArr[i];
                }
                tempArray[tempArray.length-1] = Integer.parseInt(resultSet.getString("tankId"));
                tankIdArr = tempArray;
                System.out.println(Integer.parseInt(resultSet.getString("tankId")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }



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

    public void comboBoxOnAction(ActionEvent actionEvent) {
        tankId.setText("Tank id : "+(tankComboBox.getValue()));
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




}

