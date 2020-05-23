package theChildCareProblem;

import java.util.concurrent.Semaphore;

public class ChildCare {
    public static final int C=3; //na 3 deteta 1 vaspitacica

    private int numChild;
    private int numNanny;
    private int numWaiting;

    Semaphore mutex;
    Semaphore confirm;
    Semaphore toLeave;

    public ChildCare() {
        this.numChild = 0;
        this.numNanny = 0;
        this.numWaiting = 0;
        this.mutex = new Semaphore(1);
        this.confirm = new Semaphore(0);
        this.toLeave = new Semaphore(0);
    }

    public boolean bringUpChildren(int newChildNum){
        boolean result = false;
        mutex.acquireUninterruptibly();

        if((numChild+newChildNum)<= C*numNanny){
            numChild+=newChildNum;
            result = true;
        }else {
            result = false;
        }
        mutex.release();
        return result;
    }

    public void bringBackChilder(int num){
        int out;
        mutex.acquireUninterruptibly();
        numChild = numChild - num;
        out = numNanny - (numChild + C - 1) / C; //br vaspitacica koji zeli da ode
        if(out>numWaiting){ //ako je veci br koji moze da ode od koji zeli da ode => pusti sve koje cekaju
            out = numWaiting;
        }

        for (int i = 0; i < out; i++) {
            toLeave.release(); //vaspitacica moze da ide
            confirm.acquireUninterruptibly(); //cekaj da javi da je otisla
        }
        mutex.release();
    }

    public void nannyEnter(){
        mutex.acquireUninterruptibly();
        numNanny++;
        if(numWaiting>0){
            toLeave.release(); //1 dosla => 1 moze da bezi
            confirm.acquireUninterruptibly(); //cekaj da javi da je otisla
        }
        mutex.release();
    }

    public void nannyExit() {
        mutex.acquireUninterruptibly();
        if (numChild <= C * (numNanny - 1)) {
            numNanny--;
            mutex.release();
        } else {
            numWaiting++;
            mutex.release();
            toLeave.acquireUninterruptibly();
            numNanny--;
            numWaiting--;
            confirm.release();
        }
    }
}
