package semaphoreFIFO;

import java.util.LinkedList;

public class Semafor {
    private int s;
    private LinkedList<WaitObject> queue;

    public Semafor(int val){
        s=val;
        queue = new LinkedList<WaitObject>();
    }

    public void waitS(){
        WaitObject lock = lock();
        if(lock==null){ //ako niko ne ceka
            return;
        }
        lock.await();
    }

    public synchronized void signalS(){
        if(queue.size()>0){ //ako ima nekog u redu
            WaitObject waitObject = queue.remove();
            waitObject.signal();
        }
        else{
            s++;
        }
    }

    private synchronized WaitObject lock(){
        WaitObject result = null;
        if(queue.size()>0 || s<= 0){ //ako neko ceka dodaj i novu niz u red
            result = new WaitObject();
            queue.add(result);
        }else{
            s--;
        }
        return result;
    }
}
