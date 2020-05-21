package semaphoreFIFO;

public class WaitObject {
    static final int DONE = 0;
    static final int NOTDONE= 1;

    int state;

    public WaitObject(){
        this.state = NOTDONE;
    }

    public synchronized void await(){
        while(state != DONE){ //uslov jer Semafor::waitS nije synchronized
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void signal(){
        state=DONE;
        notify();
    }
}

/*
*ovde se sustina postize blokiranjem 1 niti na 1 objektu
*jer java garantuje koja ce nit biti probudjena, samo ako je ta nit jedina
**/
