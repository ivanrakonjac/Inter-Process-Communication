package theSantaClausProblem;

public class SantaClausHouse {
    public static final int NUM_REINDEER=9;
    public static final int NUM_ELVES=10;
    public static final int MIN_ELVES=3;
    public static final int MIN_REINDEER=9;

    public static final int REINDEERS=1;
    public static final int ELVES=0;

    private int elvesAtDoor;
    private int reindeerAtDoor;
    private int elvesInTheRoom;
    private int reindeerInTheSleigh;

    private boolean wakeupE;
    private boolean wakeupR;
    private boolean enterElves;
    private boolean exitElves;
    private boolean enterReindeers;
    private boolean exitReindeers;
    private boolean isSleeping;

    public SantaClausHouse() {
        this.elvesAtDoor = 0;
        this.reindeerAtDoor = 0;
        this.elvesInTheRoom = 0;
        this.reindeerInTheSleigh = 0;
        this.wakeupE = false;
        this.wakeupR = false;
        this.enterElves = false;
        this.exitElves = false;
        this.enterReindeers = false;
        this.exitReindeers = false;
        this.isSleeping = true;
    }

    public synchronized int sleeping(){
        isSleeping=true;
        notifyAll();
        while (!(wakeupE || wakeupR)){ //spavaj dok ga neko ne probudi
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        isSleeping=false;
        if(wakeupR){
            wakeupR=false;
            enterReindeers=true;
            notifyAll(); //obavesti irvase koji cekaju
            while(reindeerAtDoor!=0){ //cekaj dok svi ne udju
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            enterReindeers=false;
            return REINDEERS;
        }else{
            wakeupE=false;
            enterElves=true; //pozovi elfove da udju
            notifyAll();
            while (elvesAtDoor!=0){ //cekaj dok ne udju svi
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            enterElves=false;
            return ELVES;
        }
    }

    public synchronized void wakupSantaR(){
        while (!isSleeping){ //dok ne spava nego radi nesto drugo => cekaj
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        reindeerAtDoor++;
        if(reindeerAtDoor==MIN_REINDEER){ //kad se skupi 9
            wakeupR=true;
            notifyAll(); //obavesti deda mraza koji ceka da ga neko probudi
        }
        while (!enterReindeers){ //cekaj dok nas deda mraz ne pozove
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        reindeerAtDoor--;
        reindeerInTheSleigh++;
        if(reindeerAtDoor==0) notifyAll(); //ako smo svi usli obavesti deda mraza
    }

    public synchronized void wakupSantaE(){
        while (!isSleeping){ //dok ne spava nego radi nesto drugo => cekaj
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        elvesAtDoor++;
        if(reindeerAtDoor==MIN_ELVES){ //kad se skupi 3
            wakeupE=true;
            notifyAll(); //obavesti deda mraza koji ceka da ga neko probudi
        }
        while (!enterElves){ //cekaj dok nas deda mraz ne pozove
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        elvesAtDoor--;
        elvesInTheRoom++;
        if(elvesAtDoor==0) notifyAll(); //ako su svi usli obavesti deda mraza
    }

    public synchronized void exitTheSleigh(){
        while(!exitReindeers){//cekaj dozvolu deda mraza da odem
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        reindeerInTheSleigh--;
        if(reindeerInTheSleigh==0) notifyAll(); //ako je posl obavesti deda mraza
    }

    public synchronized void exitRoom(){
        while (!exitElves){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        elvesInTheRoom--;
        if(elvesInTheRoom==0) notifyAll();//obavesti deda mraza da su svi izasli
    }

    public synchronized void unharnessReindeers(){
        exitReindeers=true;
        notifyAll(); //obavesti sve irvase koji cekaju dozvolu da odu
        while (reindeerInTheSleigh!=0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        exitReindeers=false;
    }

    public synchronized void showoutElves(){
        exitElves=true; //daj im dozvolu da idu
        notifyAll();
        while (elvesInTheRoom!=0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        exitElves=false;
    }
}
