package Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.Scanner;

public class tankViewController {

    @FXML
    private  JFXButton btn1;

   @FXML
   public void SendB(ActionEvent event) {

      Scanner input  =  new Scanner(System.in);

      int TemLevel = 40;
      double phLevel = 0.9;
      int amoniaLevel = 25;

      int TempLevel = 50;
      double phpLevel = 1.0;
      int amoniapLevel = 30;


      System.out.print("Input temp : ");
      int temp = input.nextInt();
      System.out.print("Input ph : ");
      double ph = input.nextDouble();
      System.out.print("Input amoina : ");
      int am = input.nextInt();

      if (((temp <=TemLevel) && (ph <= phLevel) && (am <= amoniaLevel))) {
          btn1.setStyle("-fx-background-color:green;-fx-text-fill:white");
          btn1.setText("Good");
      }else if(((temp <= TempLevel) && (ph <= phpLevel) && (am <= amoniapLevel))){
          btn1.setStyle("-fx-background-color:yellow;-fx-text-fill:black");
          btn1.setText("Midrange");
      }else{
          btn1.setStyle("-fx-background-color:red;-fx-text-fill:white");
          btn1.setText("Bad");
      }
    }
}
