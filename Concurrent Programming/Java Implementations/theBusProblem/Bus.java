package theBusProblem;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Bus extends Thread {
    private static final int MAX_PASSENGER_NUM=50;
    private static int bid;

    private int id;
    private int numFree;
    private List<Station> stationList;

    private Iterator<Station> iterator;
    private Station nextStation;
    private boolean toExit;

    public Bus() {
        this.id = Bus.nextID();
        this.numFree = MAX_PASSENGER_NUM;
        this.stationList = new LinkedList<Station>();
        this.iterator = stationList.iterator();
        this.toExit = false;
    }

    private static synchronized int nextID(){
        return bid++;
    }

    public synchronized void addStation(Station newStation){
        stationList.add(newStation);
    }

    public synchronized Station getLastStation(){
        return stationList.get(stationList.size()-1);
    }

    public synchronized Station getNextStation(){
        nextStation = iterator.next();
        notifyAll();
        return nextStation;
    }

    public synchronized void exitBus(Station exitStation){
        while(!toExit || !exitStation.equals(nextStation)){ //cekaj dok ne dobijes dozvolu i ne dodjemo na tvoju stanicu
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        numFree++;
    }

    public synchronized void permissionToExit(){
        toExit = true;
        notifyAll();
        try {
            wait(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        toExit=false;
    }

    public synchronized int getNumFree(){
        return numFree;
    }

    public synchronized void setNumFree(int numFree){
        this.numFree = numFree;
    }
}
