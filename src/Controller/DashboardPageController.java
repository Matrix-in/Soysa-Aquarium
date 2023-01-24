package Controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import javafx.scene.control.Label;

public class DashboardPageController  {
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
    private Pane logoutPane;

    @FXML
    private Pane vcPane;

    @FXML
    private Pane wchPane;

    @FXML
    private Pane getReportPane;

    @FXML
    private Pane infoPane;

    @FXML
    private Pane fishPane;

    @FXML
    private Pane manageTanksPane;


    @FXML
    public void initialize() {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        timeLabel.setText(timeStamp);



//        x.setAutoRanging(false);
//        x.setLowerBound(0);
//        x.setUpperBound(24);
//        x.setTickUnit(3);

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

        fxLinechart.setCreateSymbols(false);

        fxLinechart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
//
//        XYChart.Series series = new XYChart.Series();
//        series.setName("Temparature");
//        series.getData().add(new XYChart.Data("17:00", 15));
//        series.getData().add(new XYChart.Data("17:05", 20));
//        series.getData().add(new XYChart.Data("17:10", 15));
//        series.getData().add(new XYChart.Data("17:15", 60));
//
//
//
//        XYChart.Series series2 = new XYChart.Series();
//        series2.setName("Water Level");
//        series2.getData().add(new XYChart.Data("17:00", 1));
//        series2.getData().add(new XYChart.Data("17:05", 3));
//        series2.getData().add(new XYChart.Data("17:10", 5));
//        series2.getData().add(new XYChart.Data("17:15", 8));
//        fxLinechart.getData().addAll(series, series2);


        x.setTickLabelGap(4);
        x.setTickMarkVisible(false);



        XYChart.Series seriesTemp = new XYChart.Series<>();
        seriesTemp.setName("Temparature");



        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2.5), e -> {


            Random r = new Random();


            String nextMinute = "17:" + ( timer);


            seriesTemp.getData().add(new XYChart.Data<>(nextMinute, r.nextDouble() * 100));

            if(seriesTemp.getData().size() >= 7){
                seriesTemp.getData().remove(0);
            }



        }));
        fxLinechart.getData().add(seriesTemp);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        XYChart.Series seriesPH = new XYChart.Series<>();
        seriesPH.setName("pH");

        Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(2.5), e -> {

            Random r = new Random();
            String nextMinute = "17:" + ( timer);
            timer = timer+10;
            seriesPH.getData().add(new XYChart.Data<>(nextMinute, r.nextDouble() * 100));

            if(seriesPH.getData().size() >= 7){
                seriesPH.getData().remove(0);
            }

        }));
        fxLinechart.getData().add(seriesPH);
        timeline2.setCycleCount(Animation.INDEFINITE);
        timeline2.play();

//        int numPoints = seriesTemp.getData().size();
//        if (numPoints > 4) {
//            x.setLowerBound(numPoints - 4);
//            x.setUpperBound(numPoints - 1);
//        }

    }
    @FXML
    void logoutClick(MouseEvent event) throws IOException {
        System.out.println("logout");
        Stage thiStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        thiStage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(mainController.class.getResource("/View/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.setFill(Color.TRANSPARENT);
        Stage outStage =new Stage();
        outStage.setScene(scene);
        outStage.setResizable(false);
        outStage.initStyle(StageStyle.TRANSPARENT);
        outStage.show();
    }

    @FXML
    void logOutMouseEnter(MouseEvent event) {
        //logoutPane.setStyle("-fx-background-color:#424242;");
    }

    @FXML
    void logOutMouseExit(MouseEvent event) {
        //logoutPane.setStyle("-fx-background-color:#323232;");
    }
    @FXML
    void sidepane1MouseEnter(MouseEvent event) {
        manageTanksPane.setStyle("-fx-background-color:#424242;");
    }

    @FXML
    void sidepane1MouseExit(MouseEvent event) {
        manageTanksPane.setStyle("-fx-background-color:#323232;");
    }

    @FXML
    void sidepane2MouseEnter(MouseEvent event) {
        getReportPane.setStyle("-fx-background-color:#424242;");
    }

    @FXML
    void sidepane2MouseExit(MouseEvent event) {
        getReportPane.setStyle("-fx-background-color:#323232;");
    }

    @FXML
    void sidepane3MouseEnter(MouseEvent event) {
        wchPane.setStyle("-fx-background-color:#424242;");
    }

    @FXML
    void sidepane3MouseExit(MouseEvent event) {
        wchPane.setStyle("-fx-background-color:#323232;");
    }

    @FXML
    void sidepane4MouseEnter(MouseEvent event) {
        fishPane.setStyle("-fx-background-color:#424242;");
    }

    @FXML
    void sidepane4MouseExit(MouseEvent event) {
        fishPane.setStyle("-fx-background-color:#323232;");
    }

    @FXML
    void sidepane5MouseEnter(MouseEvent event) {
        vcPane.setStyle("-fx-background-color:#424242;");
    }

    @FXML
    void sidepane5MouseExit(MouseEvent event) {
        vcPane.setStyle("-fx-background-color:#323232;");
    }

    @FXML
    void sidepane6MouseEnter(MouseEvent event) {
        infoPane.setStyle("-fx-background-color:#424242;");
    }

    @FXML
    void sidepane6MouseExit(MouseEvent event) {
        infoPane.setStyle("-fx-background-color:#323232;");
    }
}
