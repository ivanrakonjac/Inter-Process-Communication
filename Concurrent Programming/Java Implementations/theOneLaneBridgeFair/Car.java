package theOneLaneBridge;

public class Car extends Thread{
    protected int id;
    protected Bridge bridge;

    public Car(int id, Bridge bridge) {
        this.id = id;
        this.bridge = bridge;
    }

    public void crossing(){};
    public void starting(){};
}
