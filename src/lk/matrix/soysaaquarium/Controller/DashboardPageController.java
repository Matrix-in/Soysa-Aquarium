package lk.matrix.soysaaquarium.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.skins.BarSkin;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.util.Duration;

public class DashboardPageController {



    public VBox vBox2;
    public VBox vBox1;
    public VBox vBox;
    @FXML
    private JFXButton addFirstTankBtn;
    @FXML
    private Pane notificationPane;
    private static ObservableList<String> notifications = FXCollections.observableArrayList();
    @FXML
    private ComboBox tankComboBox;

    @FXML
    private Pane sidePaneDashBoard;
    @FXML
    private Label timeLabel;
    @FXML
    private Label dateLabel;

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
    @FXML
    private Pane adminPane;
    @FXML
    private Label adminLabel;
    @FXML
    private Label adminLabel2;
    @FXML
    private Pane profilePic;
    @FXML
    private Pane piePane1;
    @FXML
    private Pane piePane2;
    @FXML
    private Pane piePane3;
    @FXML
    private Label pl1;
    @FXML
    private Label pl2;
    @FXML
    private Label pl3;

    @FXML
    private Label pieLabel1;
    @FXML
    private Label pieLabel2;
    @FXML
    private Label pieLabel3;
    @FXML
    private Pane fullPane;
    @FXML
    private Pane buttonPane2;
    @FXML
    private TextField searchText;
    @FXML
    private Button notBtn;
    @FXML
    private Button setBtn;

    @FXML
    private Circle pieCircle2;

    @FXML
    private Circle pieCircle3;

    @FXML
    private Circle pieCircle1;

    static Stage dashBoardStage;
    public static boolean isSelected;

    private String[] tankIdArr = new String[0];
    private double[] temperatureData = new double[0];
    private String[] timeStampData = new String[0];

    private double latestTemp= 0.0;
    private double latestpH = 0.0;
    private double latestAmmo = 0.0;
    private String[] tipsArray=new String[10];
    @FXML
    private Label tipsLabel;

    Gauge tempMeter = new Gauge();
    Gauge pHMeter = new Gauge();
    Gauge ammoMeter = new Gauge();
    @FXML
    void tankChanged(ActionEvent event) {
        System.out.println(tankComboBox.getSelectionModel().getSelectedIndex());

        try{
            temperatureData = new double[0];
            timeStampData = new String[0];
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/aquarium","root","1234");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from tempRecords WHERE tankId = '"+tankIdArr[tankComboBox.getSelectionModel().getSelectedIndex()]+"' ORDER BY id DESC LIMIT 5");
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

        if(temperatureData.length>0){
            latestTemp = temperatureData[temperatureData.length-1];
        }

        XYChart.Series series = new XYChart.Series();
        series.setName("Temperature");
        //populating the series with data
        for(int i = timeStampData.length; i > 0; i--){

            series.getData().add(new XYChart.Data(timeStampData[i-1], temperatureData[i-1]));
        }

//        series.getData().add(new XYChart.Data("1", 23));
//        series.getData().add(new XYChart.Data("2", 14));


        fxLinechart.getData().clear();
        fxLinechart.getData().add(series);

        ObservableList<PieChart.Data> tempPieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Filled", latestTemp),
                new PieChart.Data("free", 100-latestTemp));

        tempMeter.setValue(latestTemp);
        pHMeter.setValue(latestpH);
        ammoMeter.setValue(latestAmmo);

        tempPieChart.getData().clear();
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

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> {
            double tempLast = 0;
            String timeStampLast = "";
            try{
                temperatureData = new double[0];
                timeStampData = new String[0];
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/aquarium","root","1234");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from tempRecords WHERE tankId = '"+tankIdArr[tankComboBox.getSelectionModel().getSelectedIndex()]+"' ORDER BY id DESC LIMIT 1");
                while (resultSet.next()){
                    tempLast= Double.parseDouble(resultSet.getString("temperature"));
                    timeStampLast = resultSet.getString("timeStampTemp");
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
            series.getData().add(new XYChart.Data<>(timeStampLast, tempLast));
            if(series.getData().size() > 5){
                series.getData().remove(0);
            }
            ObservableList<PieChart.Data> tempPieChartDataInTimeLine = FXCollections.observableArrayList(
                    new PieChart.Data("Filled", tempLast),
                    new PieChart.Data("free", 100-tempLast));

            tempPieChart.getData().clear();
            tempPieChart.getData().addAll(tempPieChartDataInTimeLine);
            pieLabel1.setText(Double.toString(tempLast));
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    Timeline timeline;
    LocalTime Time = LocalTime.parse("00:00:00");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
    @FXML
    public void tips(){
        tipsArray[0]="Maintain a consistent temperature:\n\nKeep the aquarium at a consistent temperature to ensure the well-being of your aquatic animals.";
        tipsArray[1]="Regular water changes:\n\nRegular water changes will help to remove waste and keep the water healthy for your fish.";
        tipsArray[2]="Proper filtration:\n\nEnsure that the aquarium has proper filtration to remove waste and maintain a healthy environment.";
        tipsArray[3]="Adequate lighting:\n\nProvide adequate lighting to promote healthy plant growth and improve the overall aesthetic of your aquarium.";
        tipsArray[4]="Feed fish regularly:\n\nFeed your fish regularly with a balanced diet to promote healthy growth and well-being.";
        tipsArray[5]="Monitor water quality:\n\nRegularly monitor the water quality to ensure it is safe for your fish and plants.";
        tipsArray[6]="Quarantine new fish:\n\nQuarantine new fish before adding them to your aquarium to prevent the spread of diseases.";
        tipsArray[7]="Research before buying:\n\nDo research before buying new fish or plants to ensure that they are compatible with your existing aquarium setup.";
        Random tipsRandom = new Random();
        int num= tipsRandom.nextInt(8);
        tipsLabel.setText(tipsArray[num]);
    }
    public void initialize() {
        tips();
        try{

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/aquarium","root","1234");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from tankDetail");
            while (resultSet.next()){
                String[] tempArray = new String[tankIdArr.length+1];
                for (int i = 0; i < tankIdArr.length; i++){
                    tempArray[i] = tankIdArr[i];
                }
                tempArray[tempArray.length-1] = resultSet.getString("tankId");
                tankIdArr = tempArray;
                System.out.println(resultSet.getString("tankId"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try{

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/aquarium","root","1234");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from tempRecords WHERE tankId = '"+tankIdArr[0]+"' ORDER BY id DESC LIMIT 5");
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

        if(temperatureData.length>0){
            latestTemp = temperatureData[temperatureData.length-1];
        }

//        tankIdArr = new int[0];
        if(tankIdArr.length > 0){
            addFirstTankBtn.setVisible(false);
            tankComboBox.setPromptText("Tank 001");
//        tankComboBox.setStyle("-fx-prompt-text-fill : #000000;");


            ObservableList<String> tanksCB =
                    FXCollections.observableArrayList();

            System.out.println(Arrays.toString(tankIdArr));
            for (String id: tankIdArr){
                System.out.println(id);
                tanksCB.add("Tank "+id);
            }


            tankComboBox.setItems(tanksCB);

            ObservableList<PieChart.Data> tempPieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Filled", latestTemp),
                    new PieChart.Data("free", 100-latestTemp));

            Random r = new Random();
            new Thread(){
                public void run(){
                    while(true){
                        latestTemp = r.nextInt(101);
                        latestpH = r.nextInt(101);
                        latestAmmo = r.nextInt(101);
                    }
                }
            }.start();

            tempMeter.setSkin(new BarSkin(tempMeter));
            tempMeter.setTitle("Temp");
            tempMeter.setValue(10);
            tempMeter.setAnimated(true);
            tempMeter.setValueColor(Color.RED);
            tempMeter.setTitleColor(Color.BLACK);
            tempMeter.setBarColor(Color.rgb(255,165,0));
            tempMeter.setValue(latestTemp);
            tempMeter.setMaxSize(150.0,150.0);

            pHMeter.setSkin(new BarSkin(pHMeter));
            pHMeter.setTitle("pH");
            pHMeter.setValue(10);
            pHMeter.setAnimated(true);
            pHMeter.setValueColor(Color.RED);
            pHMeter.setTitleColor(Color.BLACK);
            pHMeter.setBarColor(Color.rgb(255,165,0));
            pHMeter.setValue(latestpH);
            pHMeter.setMaxSize(150.0,150.0);

            ammoMeter.setSkin(new BarSkin(ammoMeter));
            ammoMeter.setTitle("Ammonia");
            ammoMeter.setValue(10);
            ammoMeter.setAnimated(true);
            ammoMeter.setValueColor(Color.RED);
            ammoMeter.setTitleColor(Color.BLACK);
            ammoMeter.setBarColor(Color.rgb(255,165,0));
            ammoMeter.setValue(latestAmmo);
            ammoMeter.setMaxSize(150.0,150.0);

            vBox.setPadding(new Insets(40,20,20,20));
            vBox.getChildren().add(tempMeter);
            vBox1.setPadding(new Insets(40,20,20,20));
            vBox1.getChildren().add(pHMeter);
            vBox2.setPadding(new Insets(40,20,20,20));
            vBox2.getChildren().add(ammoMeter);

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

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> {
                double tempLast = 0;
                String timeStampLast = "";
                try{
                    temperatureData = new double[0];
                    timeStampData = new String[0];
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/aquarium","root","1234");
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("select * from tempRecords WHERE tankId = '"+tankIdArr[0]+"' ORDER BY id DESC LIMIT 1");
                    while (resultSet.next()){
                        tempLast= Double.parseDouble(resultSet.getString("temperature"));
                        timeStampLast = resultSet.getString("timeStampTemp");
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }

                if(timeStampData.length > 0){
                    if(! timeStampLast.equals(timeStampData[timeStampData.length-1])){
                        series.getData().add(new XYChart.Data<>(timeStampLast, tempLast));
                        if(series.getData().size() > 5){
                            series.getData().remove(0);
                        }
                        ObservableList<PieChart.Data> tempPieChartDataInTimeLine = FXCollections.observableArrayList(
                                new PieChart.Data("Filled", tempLast),
                                new PieChart.Data("free", 100-tempLast));

                        tempMeter.setValue(latestTemp);
                        pHMeter.setValue(latestpH);
                        ammoMeter.setValue(latestAmmo);

                        tempPieChart.getData().clear();
                        tempPieChart.getData().addAll(tempPieChartDataInTimeLine);
                        pieLabel1.setText(Double.toString(tempLast));
                    }
                }



            }));

            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();


        }else {
            tankComboBox.setVisible(false);
        }

        timeLabel.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("     hh:mm:ss a")));

        dateLabel.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("     yyyy-MM-dd")));

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> timeLabel.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("     hh:mm:ss a")))));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(1), event -> dateLabel.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("     yyyy-MM-dd")))));
        timeline2.setCycleCount(Animation.INDEFINITE);
        timeline2.play();
        //--notification
        ListView<String> notificationListView = new ListView<>(notifications);
        StackPane root = new StackPane();
        root.getChildren().add(notificationListView);
        root.setPrefWidth(350);
        root.setPrefHeight(450);
        notificationPane.getChildren().add(root);
        addNotification("hello Java"+"\n"+(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a"))));

    }
    public static void addNotification( String message) {
        notifications.add(message);
    }

    @FXML
    void logoutClick(MouseEvent event) throws IOException {
        System.out.println("logout");
        Stage thiStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        thiStage.hide();

        FXMLLoader fxmlLoader = new FXMLLoader(loginController.class.getResource("/lk/matrix/soysaaquarium/View/login_form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.setFill(Color.TRANSPARENT);
        Stage outStage =new Stage();
        outStage.setScene(scene);
        outStage.setResizable(false);
        outStage.initStyle(StageStyle.TRANSPARENT);
        outStage.show();
    }


    public void onActionToggleBtn(ActionEvent actionEvent) {}

    public void onActiontankDetailForm(ActionEvent actionEvent) throws IOException {
        dashBoardStage = (Stage) infoPane.getScene().getWindow();
        dashBoardStage.hide();
        FXMLLoader fxmlLoader = new FXMLLoader(DashboardPageController.class.getResource("/lk/matrix/soysaaquarium/View/manage_tank_form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage outStage =new Stage();
        outStage.setScene(scene);
        outStage.show();
//        thisStage.setFullScreen(true);

//        int width = (int) Screen.getPrimary().getBounds().getWidth();
//        int height = (int) Screen.getPrimary().getBounds().getHeight();
//
//        thisStage.setMaximized(true);
//        thisStage.setWidth(width);
//        thisStage.setHeight(height);
//        if(stage2 == null) {
//            stage = (Stage) fullPane.getScene().getWindow();
//            stage.hide();
//            FXMLLoader fxmlLoader = new FXMLLoader(DashboardPageController.class.getResource("/lk/matrix/soysaaquarium/View/manage_tank_form.fxml"));
//            Scene scene = new Scene(fxmlLoader.load());
//            stage2 = new Stage();
//            stage2.setScene(scene);
//            stage2.setResizable(false);
//            stage2.show();
//        }else{
//            stage = (Stage) fullPane.getScene().getWindow();
//            stage.hide();
//            stage2.show();
//        }
    }
    public void onActioninfo(ActionEvent actionEvent) throws IOException {
        dashBoardStage = (Stage) infoPane.getScene().getWindow();
        dashBoardStage.hide();
        FXMLLoader fxmlLoader1 = new FXMLLoader(loginController.class.getResource("/lk/matrix/soysaaquarium/View/info_window_form.fxml"));
        Scene scene = new Scene(fxmlLoader1.load());
        Stage outStage =new Stage();
        outStage.setScene(scene);
        outStage.show();

    }

    @FXML
    void fishesBtnOnAction(ActionEvent event) throws IOException {
        dashBoardStage = (Stage) infoPane.getScene().getWindow();
        dashBoardStage.hide();
        FXMLLoader fxmlLoader1 = new FXMLLoader(FishTypeWindowFormController.class.getResource("/lk/matrix/soysaaquarium/View/fish_type_window_form.fxml"));
        Scene scene = new Scene(fxmlLoader1.load());
        Stage outStage =new Stage();
        outStage.setScene(scene);
        outStage.show();

    }

    public void onActionGetReport(ActionEvent actionEvent) throws IOException {
        dashBoardStage = (Stage) infoPane.getScene().getWindow();
        dashBoardStage.hide();
        FXMLLoader fxmlLoader= new FXMLLoader(GetReportFormController.class.getResource("/lk/matrix/soysaaquarium/View/get_report_form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage outStage =new Stage();
        outStage.setScene(scene);
        outStage.show();


    }

    public void onMouseClickedNotificstionIcon(MouseEvent mouseEvent) {
        if(notificationPane.isVisible()){
            notificationPane.setVisible(false);
        }else{
            notificationPane.setVisible(true);
        }
    }

    public void onMouseClickedFacebook(MouseEvent mouseEvent) throws URISyntaxException,IOException {
        Desktop.getDesktop().browse(new URI("https://www.facebook.com/profile.php?id=100039631345638"));
    }
    public void onMouseClickedgMail(MouseEvent mouseEvent) throws URISyntaxException,IOException {
        Desktop.getDesktop().browse(new URI("https://mail.google.com/mail/u/2/#inbox"));
    }
    public void onMouseClickedGoogle(MouseEvent mouseEvent) throws URISyntaxException,IOException {
        Desktop.getDesktop().browse(new URI("https://www.google.com/"));
    }

    public void viewCameraBtnOnAction(ActionEvent actionEvent) throws IOException {
        dashBoardStage = (Stage) infoPane.getScene().getWindow();
        dashBoardStage.hide();

        FXMLLoader fxmlLoader1 = new FXMLLoader(ViewCameraWindowFormController.class.getResource("/lk/matrix/soysaaquarium/View/view_camera_window_form.fxml"));

        Scene scene = new Scene(fxmlLoader1.load());
        Stage outStage =new Stage();
        outStage.setScene(scene);
        outStage.show();
    }
}
