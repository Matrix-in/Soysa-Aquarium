package lk.matrix.soysaaquarium.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import javafx.scene.control.Label;

public class DashboardPageController  {

    @FXML
    private ComboBox tankComboBox;

    @FXML
    private Label timeLabel;
    @FXML
    private LineChart<?, ?> fxLinechart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    private int timer = 0;

    @FXML
    private PieChart tempPieChart;
    @FXML
    private PieChart phPieChart;

    @FXML
    private PieChart amoPieChart;

    @FXML
    private JFXButton logoutPane;

    @FXML
    private JFXButton vcPane;

    @FXML
    private JFXButton wchPane;

    @FXML
    private JFXButton getReportPane;

    @FXML
    private JFXButton infoPane;

    @FXML
    private JFXButton fishPane;

    @FXML
    private JFXButton manageTanksPane;

    @FXML
    private JFXToggleButton modeToggleBtn;
    @FXML
    private Pane buttonPane;
    @FXML
    private Pane bgPane;

    private int[] tankIdArr = new int[0];
    private double[] temperatureData = new double[0];
    private String[] timeStampData = new String[0];
    @FXML
    public void initialize() {

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

        tankComboBox.setPromptText("Tank 001");
//        tankComboBox.setStyle("-fx-prompt-text-fill : #000000;");


        ObservableList<String> tanksCB =
                FXCollections.observableArrayList();

        System.out.println(Arrays.toString(tankIdArr));
        for (int id: tankIdArr){
            System.out.println(id);
            tanksCB.add("Tank 00"+id);
        }


        tankComboBox.setItems(tanksCB);

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        timeLabel.setText(timeStamp);


        ObservableList<PieChart.Data> tempPieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Filled", 40),
                new PieChart.Data("free", 60));

        tempPieChart.getData().addAll(tempPieChartData);
        // Iterate through the chart's data and set the color for each segment
        for (PieChart.Data data : tempPieChart.getData()) {
            if(data.getName().equals("Filled")){
                data.getNode().setStyle("-fx-pie-color: #78E08F;");
            }
            if(data.getName().equals("free")){
                data.getNode().setStyle("-fx-pie-color: #4c4c4c;");
            }
        }

        ObservableList<PieChart.Data> phPieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Filled", 70),
                new PieChart.Data("free", 30));

        phPieChart.getData().addAll(phPieChartData);
        // Iterate through the chart's data and set the color for each segment
        for (PieChart.Data data : phPieChart.getData()) {
            if(data.getName().equals("Filled")){
                data.getNode().setStyle("-fx-pie-color: #F14647;");
            }
            if(data.getName().equals("free")){
                data.getNode().setStyle("-fx-pie-color: #4c4c4c;");
            }
        }

        ObservableList<PieChart.Data> amoPieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Filled", 30),
                new PieChart.Data("free", 70));

        amoPieChart.getData().addAll(amoPieChartData);
        // Iterate through the chart's data and set the color for each segment
        for (PieChart.Data data : amoPieChart.getData()) {
            if(data.getName().equals("Filled")){
                data.getNode().setStyle("-fx-pie-color: #78E08F;");
            }
            if(data.getName().equals("free")){
                data.getNode().setStyle("-fx-pie-color: #4c4c4c;");
            }
        }

        //line chart codes starts

        fxLinechart.setCreateSymbols(false);
        fxLinechart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
        x.setTickLabelGap(4);
        x.setTickMarkVisible(false);

        try{

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/aquarium","root","1234");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from tempRecords ORDER BY id DESC LIMIT 4");
            while (resultSet.next()){
                double[] tempArray = new double[temperatureData.length+1];
                for (int i = 0; i < temperatureData.length; i++){
                    tempArray[i] = temperatureData[i];
                 }
                tempArray[tempArray.length-1] = Double.parseDouble(resultSet.getString("temperature"));
                temperatureData = tempArray;
                System.out.println(Double.parseDouble(resultSet.getString("temperature")));

                String[] tempArray2 = new String[timeStampData.length+1];
                for (int i = 0; i < timeStampData.length; i++){
                    tempArray2[i] = timeStampData[i];
                }
                tempArray2[tempArray2.length-1] = resultSet.getString("timeStampTemp");
                timeStampData = tempArray2;
                System.out.println(resultSet.getString("timeStampTemp"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        XYChart.Series series = new XYChart.Series();
        series.setName("Temperature");
        //populating the series with data
        for(int i = timeStampData.length; i > 0; i--){

            series.getData().add(new XYChart.Data(timeStampData[i-1], temperatureData[i-1]));
        }

//        series.getData().add(new XYChart.Data("1", 23));
//        series.getData().add(new XYChart.Data("2", 14));


        fxLinechart.getData().add(series);

//        //tl1
//        XYChart.Series seriesTemp = new XYChart.Series<>();
//        seriesTemp.setName("Temparature");
//        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2.5), e -> {
//            Random r = new Random();
//            String nextMinute = "17:" + ( timer);
//            seriesTemp.getData().add(new XYChart.Data<>(nextMinute, r.nextDouble() * 100));
//            if(seriesTemp.getData().size() >= 7){
//                seriesTemp.getData().remove(0);
//            }
//        }));
//        fxLinechart.getData().add(seriesTemp);
//        timeline.setCycleCount(Animation.INDEFINITE);
//        timeline.play();

//        //tl2
//        XYChart.Series seriesPH = new XYChart.Series<>();
//        seriesPH.setName("pH");
//        Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(2.5), e -> {
//            Random r = new Random();
//            String nextMinute = "17:" + ( timer);
//            timer = timer+10;
//            seriesPH.getData().add(new XYChart.Data<>(nextMinute, r.nextDouble() * 100));
//            if(seriesPH.getData().size() >= 7){
//                seriesPH.getData().remove(0);
//            }
//        }));
//        fxLinechart.getData().add(seriesPH);
//        timeline2.setCycleCount(Animation.INDEFINITE);
//        timeline2.play();


    }
    @FXML
    void logoutClick(MouseEvent event) throws IOException {
        System.out.println("logout");
        Stage thiStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        thiStage.hide();

        FXMLLoader fxmlLoader = new FXMLLoader(mainController.class.getResource("/lk/matrix/soysaaquarium/View/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.setFill(Color.TRANSPARENT);
        Stage outStage =new Stage();
        outStage.setScene(scene);
        outStage.setResizable(false);
        outStage.initStyle(StageStyle.TRANSPARENT);
        outStage.show();
    }


    public void onActionToggleBtn(ActionEvent actionEvent) {
        System.out.println(modeToggleBtn.isSelected());
        if (modeToggleBtn.isSelected()){

            manageTanksPane.setStyle("-fx-background-color: #4c4c4c;-fx-background-radius:5px;-fx-text-fill:white");
            getReportPane.setStyle("-fx-background-color: #4c4c4c;-fx-background-radius:5px;-fx-text-fill:white");
            wchPane.setStyle("-fx-background-color: #4c4c4c;-fx-background-radius:5px;-fx-text-fill:white");
            fishPane.setStyle("-fx-background-color: #4c4c4c;-fx-background-radius:5px;-fx-text-fill:white");
            vcPane.setStyle("-fx-background-color: #4c4c4c;-fx-background-radius:5px;-fx-text-fill:white");
            infoPane.setStyle("-fx-background-color: #4c4c4c;-fx-background-radius:5px;-fx-text-fill:white");
            logoutPane.setStyle("-fx-background-color: #4c4c4c;-fx-background-radius:5px;-fx-text-fill:white");
            buttonPane.setStyle("-fx-background-color:white;-fx-background-radius:10px");
            bgPane.setStyle("-fx-background-color:white");
            x.setStyle("-fx-tick-label-fill:black");
            y.setStyle("-fx-tick-label-fill:black");
            modeToggleBtn.setText("Ligt Mode");

        }else{
            manageTanksPane.setStyle("-fx-background-color:#323232;-fx-background-radius:5px;-fx-text-fill:white");
            getReportPane.setStyle("-fx-background-color:#323232;-fx-background-radius:5px;-fx-text-fill:white");
            wchPane.setStyle("-fx-background-color:#323232;-fx-background-radius:5px;-fx-text-fill:white");
            fishPane.setStyle("-fx-background-color:#323232;-fx-background-radius:5px;-fx-text-fill:white");
            vcPane.setStyle("-fx-background-color:#323232;-fx-background-radius:5px;-fx-text-fill:white");
            infoPane.setStyle("-fx-background-color:#323232;-fx-background-radius:5px;-fx-text-fill:white");
            logoutPane.setStyle("-fx-background-color:#323232;-fx-background-radius:5px;-fx-text-fill:white");
            buttonPane.setStyle("-fx-background-color:#4c4c4c;-fx-background-radius:10px");
           bgPane.setStyle("-fx-background-color:#1f1f1f");
           x.setStyle("-fx-tick-label-fill:white");
           y.setStyle("-fx-tick-label-fill:white");
            modeToggleBtn.setText("Dark Mode");
        }
    }
}
