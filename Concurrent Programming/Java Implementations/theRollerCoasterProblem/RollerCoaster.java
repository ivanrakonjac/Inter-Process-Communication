package theRollerCoasterProblem;

import java.util.concurrent.Semaphore;

public class RollerCoaster extends Thread {
    public static final int C = 5; //kapacitet vozila

    private Semaphore start;
    private Semaphore allAboard;
    private Semaphore stop;
    private Semaphore allOut;

    private int passengers;

    public RollerCoaster(Semaphore start, Semaphore allAboard, Semaphore stop, Semaphore allOut, int passengers) {
        this.start = start;
        this.allAboard = allAboard;
        this.stop = stop;
        this.allOut = allOut;
        this.passengers = passengers;
    }

    @Override
    public void run() {
        super.run();
        while(true){
            boardCar();
            ride();
            leaveCar();
        }
    }

    public void boardCar(){
        for (int i = 0; i < C; i++) {
            start.release();
        }
        allAboard.acquireUninterruptibly();
    }

    public void leaveCar(){
        for (int i = 0; i < C; i++) {
            stop.release();
        }
        allOut.acquireUninterruptibly();
    }

    public void ride(){
        //Vozim se
    }

    public void incPassengers(){
        this.passengers++;
    }

    public void decPassengers(){
        this.passengers--;
    }

    public int getPassengers() {
        return passengers;
    }

    public static int getKapacitet() {
        return C;
    }
}
