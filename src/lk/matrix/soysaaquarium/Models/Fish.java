package lk.matrix.soysaaquarium.Models;

import java.sql.Timestamp;

public class Fish {
    private String tankId;
    private int fishQty;
    private String timeStamp;

    public Fish(String tankId, int fishQty,String timeStamp){
        this.setTankId(tankId);
        this.setFishQty(fishQty);
        this.setTimeStamp(timeStamp);
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTankId() {
        return tankId;
    }

    public void setTankId(String tankId) {
        this.tankId = tankId;
    }

    public int getFishQty() {
        return fishQty;
    }

    public void setFishQty(int fishQty) {
        this.fishQty = fishQty;
    }
}
