package theBusProblem;

public class Passenger extends Thread {

    public static int pid;
    int id;
    Station start, end;

    public Passenger(int id, Station start, Station end) {
        this.id = Passenger.nextId();
        this.start = start;
        this.end = end;
    }

    public static int nextId(){
        return pid++;
    }

    @Override
    public void run() {
        super.run();
        while (true){
            Bus bus = start.waitBus(); //dodjem na stanicu i cekam bus
            travel();
            bus.exitBus(end); //hocu napolje iz busa
        }
    }

    private void travel(){
        //Putujem
    }
}
