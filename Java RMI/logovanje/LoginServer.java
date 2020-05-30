package logovanje;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class LoginServer {

    public static void main(String[] args) {
        if(System.getSecurityManager()==null){
            System.setSecurityManager(new SecurityManager());
        }

        UlogujSe logovanje = new UlogujSeImpl();

        try {
            UlogujSe stub = (UlogujSe) UnicastRemoteObject.exportObject(logovanje,0);
            Registry registry = LocateRegistry.createRegistry(8080);
            registry.rebind("/logovanje",stub);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println("Server startovao!");


    }

}
