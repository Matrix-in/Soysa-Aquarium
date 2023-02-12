package lk.matrix.soysaaquarium.Controller;

import com.jfoenix.controls.JFXButton;
import eu.hansolo.medusa.Clock;
import eu.hansolo.medusa.ClockBuilder;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.skins.BarSkin;
import eu.hansolo.medusa.skins.ClockSkin;
import eu.hansolo.medusa.skins.DBClockSkin;
import eu.hansolo.medusa.skins.DigitalClockSkin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Arrays;

public class ViewCameraWindowFormController {

    @FXML
    private JFXButton view1Btn;

    @FXML
    private JFXButton view3Btn;

    @FXML
    private JFXButton view4Btn;

    @FXML
    private JFXButton view6Btn;

    @FXML
    private JFXButton view5Btn;

    @FXML
    private JFXButton view2Btn;

    @FXML
    private JFXButton view8Btn;

    @FXML
    private JFXButton view7Btn;

    @FXML
    private Label view1;
    @FXML
    private Label view2;

    @FXML
    private Label view3;

    @FXML
    private Label view4;

    @FXML
    private Label view5;

    @FXML
    private Label view6;

    @FXML
    private Label view7;

    @FXML
    private Label view8;

    @FXML
    private Label mainCamera;

    @FXML
    private HBox hBox;

    @FXML
    private JFXButton backBtn;
    Label[] labelList = {};

    public void initialize(){
        view1.setText("Camera 1");
        labelList = new Label[]{view2,view3,view4,view5,view6,view7,view8};
    }

    @FXML
    void backBtnOnAction(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.hide();

        DashboardPageController.dashBoardStage.show();
    }

    @FXML
    void view1BtnOnAction(ActionEvent event) {
        for (int i = 0; i < labelList.length; i++){
            if(labelList[i].getText().equals("Camera 1")){
                String v1 = mainCamera.getText();
                String v2 = labelList[i].getText();
                mainCamera.setText(v2);
                labelList[i].setText(v1);
            }
        }
    }

    @FXML
    void view2BtnOnAction(ActionEvent event) {
        for (int i = 0; i < labelList.length; i++){
            if(labelList[i].getText().equals("Camera 2")){
                String v1 = mainCamera.getText();
                String v2 = labelList[i].getText();
                mainCamera.setText(v2);
                labelList[i].setText(v1);
            }
        }
    }

    @FXML
    void view3BtnOnAction(ActionEvent event) {
        for (int i = 0; i < labelList.length; i++){
            if(labelList[i].getText().equals("Camera 3")){
                String v1 = mainCamera.getText();
                String v2 = labelList[i].getText();
                mainCamera.setText(v2);
                labelList[i].setText(v1);
            }
        }
    }

    @FXML
    void view4BtnOnAction(ActionEvent event) {
        for (int i = 0; i < labelList.length; i++){
            if(labelList[i].getText().equals("Camera 4")){
                String v1 = mainCamera.getText();
                String v2 = labelList[i].getText();
                mainCamera.setText(v2);
                labelList[i].setText(v1);
            }
        }
    }

    @FXML
    void view5BtnOnAction(ActionEvent event) {
        for (int i = 0; i < labelList.length; i++){
            if(labelList[i].getText().equals("Camera 5")){
                String v1 = mainCamera.getText();
                String v2 = labelList[i].getText();
                mainCamera.setText(v2);
                labelList[i].setText(v1);
            }
        }
    }

    @FXML
    void view6BtnOnAction(ActionEvent event) {
        for (int i = 0; i < labelList.length; i++){
            if(labelList[i].getText().equals("Camera 6")){
                String v1 = mainCamera.getText();
                String v2 = labelList[i].getText();
                mainCamera.setText(v2);
                labelList[i].setText(v1);
            }
        }
    }

    @FXML
    void view7BtnOnAction(ActionEvent event) {
        for (int i = 0; i < labelList.length; i++){
            if(labelList[i].getText().equals("Camera 7")){
                String v1 = mainCamera.getText();
                String v2 = labelList[i].getText();
                mainCamera.setText(v2);
                labelList[i].setText(v1);
            }
        }
    }

    @FXML
    void view8BtnOnAction(ActionEvent event) {
        for (int i = 0; i < labelList.length; i++){
            if(labelList[i].getText().equals("Camera 8")){
                String v1 = mainCamera.getText();
                String v2 = labelList[i].getText();
                mainCamera.setText(v2);
                labelList[i].setText(v1);
            }
        }
    }

}
