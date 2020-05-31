package atomicBroadcast;

import java.rmi.RemoteException;

public class AtomicBroadcastBufferImpl<T> implements AtomicBroadcastBuffer<T> {

    private T vrednost;
    private boolean procitana=false;
    private boolean upisana=false;
    private int brojCitaca=3;
    private int brojac=0;

    @Override
    public synchronized void put(T item) throws RemoteException {
        if(upisana==true){ //ako je neko vec upisao, ali niko nije procitao
            while(procitana==false){ //cekaj dok ne procita
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        vrednost=item;
        procitana=false;
        upisana=true;
        notifyAll();
        System.out.println("Upisao sam:" + vrednost);

        System.out.println("Buffer: " + vrednost);
    }

    @Override
    public synchronized T get(int id) throws RemoteException {
        while (upisana==false){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        T val=vrednost;
        brojac++;
        if(brojac==3){
            procitana=true;
            brojac=0;
        }
        notifyAll();
        System.out.println("Procitao sam:" + val);
        return val;
    }
}
