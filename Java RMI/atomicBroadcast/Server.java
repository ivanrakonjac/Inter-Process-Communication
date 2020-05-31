package atomicBroadcast;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args) {
        if(System.getSecurityManager()==null){
            System.setSecurityManager(new SecurityManager());
        }

        AtomicBroadcastBuffer<Integer> buffer = new AtomicBroadcastBufferImpl<Integer>();
        try {
            AtomicBroadcastBuffer<Integer> stub = (AtomicBroadcastBuffer<Integer>) UnicastRemoteObject.exportObject(buffer,0);
            Registry registry = LocateRegistry.createRegistry(8080);
            registry.rebind("/buffer",stub);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        System.out.println("Server started!");

    }
}
