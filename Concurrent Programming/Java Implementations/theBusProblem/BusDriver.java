package theBusProblem;

public class BusDriver extends Thread {
    public static int bdid=0;
    private int id;
    private  Bus bus;

    public BusDriver(int id, Bus bus) {
        this.id = bdid++;
        this.bus = bus;
    }

    @Override
    public void run() {
        super.run();
        while (true){
            Station nextStation = bus.getNextStation();
            ride(nextStation);
            bus.permissionToExit(); //daj ljudima dozvolu da izadju
            nextStation.busEnter(bus); //hocu da udjem u stanicu
        }
    }

    private void ride(Station nextStation){
        //Vozim bus
    }
}
