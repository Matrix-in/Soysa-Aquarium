package lk.matrix.soysaaquarium.Models;


import com.jfoenix.controls.JFXButton;
import javafx.scene.image.ImageView;

public class Tank {


    private String tankId;

    public String getTankId() {
        return tankId;
    }

    public void setTankId(String tankId) {
        this.tankId = tankId;
    }

    public String getFishType() {
        return fishType;
    }

    public void setFishType(String fishType) {
        this.fishType = fishType;
    }

    public JFXButton getBin() {
        return bin;
    }

    public void setBin(JFXButton bin) {
        this.bin = bin;
    }

    private String fishType;
    private JFXButton bin;


    public Tank(String tankId, String fishType) {
        this.tankId = tankId;
        this.fishType = fishType;
        this.bin = new JFXButton("Delete");
        this.bin.setStyle("-fx-background-color: #ff7738; -fx-text-fill: #ffffff");
    }


}


