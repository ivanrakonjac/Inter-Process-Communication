package theBusProblem;

public class Station {
    private String name;
    private int numPassenger, numFreePlaces;
    private boolean busIN;
    private Bus bus;

    public Station(String name) {
        this.name = name;
        this.numPassenger = 0;
        this.numFreePlaces = 0;
        this.busIN = false;
        this.bus = null;
    }

    public synchronized Bus waitBus(){
        while (true){
            while (busIN){ //ako je bus u stanici, cekaj da ti kazu da mozes da udjes
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            numPassenger++; //br ljudi koji ceka na stanici
            while (!busIN){ //cekaj ako bus nije u stanici => dok ne dodje
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            numPassenger--;
            if(numPassenger == 0){ //ako nema ljudi na stanici obavesti vozaca
                notifyAll();
            }
            if(numFreePlaces>0){
                numFreePlaces--;
                return bus;
            }
        }
    }

    public synchronized void busEnter(Bus b){ //bus ulazi u stanicu
        while (busIN){ //ako vec ima bus u stanici
           try {
               wait();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
        }
        busIN=true;
        bus = b;
        numFreePlaces = b.getNumFree();
        notifyAll(); //obavesti ljude koji cekaju
        while(numPassenger!=0){ //cekaj dok ima ljudi na stanici
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        busIN=false; //ode bus
        bus=null;
        b.setNumFree(numFreePlaces);
        notifyAll();
    }
}
