package theSleepingBarberProblem;

public class BarberShop {
    private int numChairs;
    private int numPresents;
    private int[] chairs;
    private int nextChair;
    private int nextToShave;
    private boolean done;

    public BarberShop(int numChairs) {
        this.chairs = new int[numChairs];
        this.numChairs=numChairs;
        this.numPresents = 0;
        this.nextChair = 0;
        this.nextToShave = 0;
        this.done = false;
    }

    public synchronized int getNextCustomer(){
        while(numPresents==0){ //ako nema nikog zabodi ga
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return chairs[nextToShave]; //vrati br stolice na kojoj sedi nova musterija
    }

    public synchronized boolean getHaircut(int id){
        if(numPresents == numChairs) return false; //ako nema mesta => false
        numPresents++;
        int myChair = nextChair;
        nextChair = (nextChair+1) % numChairs;
        while(myChair!=nextToShave){ //spavaj dok ne dodje red na tebe, budi ga brica
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        notifyAll(); //obavesti sve koji cekaju da si ti na redu
        while (!done){ //spavaj dok te brica ne zavrsi
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        done=false; //reci brici da si skontao da si gotov
        notifyAll(); //obavesti bricu
        return true;
    }

    public synchronized void finishedCut(){
        done=true; //gotov sam
        notifyAll(); //obavesti musteriju da je gotova
        while (done){//cekaj dok musterija ne shvati da je osisana
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        nextToShave= (nextToShave+1) % numChairs;
        numPresents--;
        notifyAll(); //probudi one koji cekaju na red
    }
}
