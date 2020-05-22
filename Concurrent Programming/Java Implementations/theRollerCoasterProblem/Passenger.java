package theRollerCoasterProblem;

import java.util.concurrent.Semaphore;

public class Passenger extends Thread {
    private int id;
    private RollerCoaster rc;

    private Semaphore start;
    private Semaphore allAboard;
    private Semaphore stop;
    private Semaphore allOut;
    private Semaphore mutex;

    public Passenger(int id, RollerCoaster rc, Semaphore start, Semaphore allAboard, Semaphore stop, Semaphore allOut, Semaphore mutex) {
        this.id = id;
        this.rc = rc;
        this.start = start;
        this.allAboard = allAboard;
        this.stop = stop;
        this.allOut = allOut;
        this.mutex = mutex;
    }

    @Override
    public void run() {
        super.run();
        while (true){
            boardCar();
            ride();
            leaveCar();
        }
    }

    public void boardCar(){
        start.acquireUninterruptibly();
        mutex.acquireUninterruptibly();
        rc.incPassengers();
        System.out.println("Usao putnik: " + this.id);
        if(rc.getPassengers()==rc.getKapacitet()){
            allAboard.release();
        }
        mutex.release();
    }

    public void leaveCar(){
        stop.acquireUninterruptibly();
        mutex.acquireUninterruptibly();
        rc.decPassengers();
        System.out.println("Izasao putnik: " + this.id);
        if(rc.getPassengers()==0){
            allOut.release();
        }
        mutex.release();
    }

    public void ride(){
        //Vozim se
    }
}
