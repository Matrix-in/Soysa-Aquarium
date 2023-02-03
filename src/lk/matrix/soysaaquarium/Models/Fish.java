package lk.matrix.soysaaquarium.Models;

public class Fish {
    private int tankId;
    private int fishQty;

    public Fish(int tankId, int fishQty){
        this.setTankId(tankId);
        this.setFishQty(fishQty);
    }

    public int getTankId() {
        return tankId;
    }

    public void setTankId(int tankId) {
        this.tankId = tankId;
    }

    public int getFishQty() {
        return fishQty;
    }

    public void setFishQty(int fishQty) {
        this.fishQty = fishQty;
    }
}
