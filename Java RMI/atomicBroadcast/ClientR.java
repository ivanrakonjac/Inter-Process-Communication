package atomicBroadcast;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientR extends Thread implements AtomicBroadcastBuffer<Integer>{
    private AtomicBroadcastBuffer<Integer> stub;
    private int vrednost=0;
    private int id;

    public ClientR(int id,String host, int port) {
        this.id=id;
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
        while (true){
            try {
                int x = stub.get(0);
                System.out.println(id + ": Dohvatio sam " + x);
                sleep(1000);
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
        ClientR clientR1 = new ClientR(1,"localhost",8080);
        clientR1.start();
        ClientR clientR2 = new ClientR(2,"localhost",8080);
        clientR2.start();
        ClientR clientR3 = new ClientR(3,"localhost",8080);
        clientR3.start();
    }


}
