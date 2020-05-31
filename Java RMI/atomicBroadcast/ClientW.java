package atomicBroadcast;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientW extends Thread implements AtomicBroadcastBuffer<Integer>{
    private AtomicBroadcastBuffer<Integer> stub;
    private int vrednost=0;
    private int id=0;

    public ClientW(int id, String host, int port) {
        this.id = id;
        if(System.getSecurityManager()==null){
            System.setSecurityManager(new SecurityManager());
        }

        try {
            Registry registry = LocateRegistry.getRegistry(host,port);
            stub = (AtomicBroadcastBuffer<Integer>) registry.lookup("/buffer");


        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();
        while (vrednost<100){
            try {
                stub.put(vrednost);
                System.out.println(id + ": Stavio sam " + vrednost);
                vrednost++;
                sleep(500);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void put(Integer item) {
        try {
            stub.put(item);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer get(int id){
        try {
            return stub.get(id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        ClientW clientW1 = new ClientW(1,"localhost",8080);
        clientW1.start();
    }


}
