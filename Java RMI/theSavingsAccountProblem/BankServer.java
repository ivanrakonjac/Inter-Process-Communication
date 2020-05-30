package theSavingsAccountProblem;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class BankServer {

    public static String host ="localhost";
    public static int port=8080;

    public static void main(String[] args) {
        if(System.getSecurityManager()==null){
            System.setSecurityManager(new SecurityManager());
        }

        Bank bank = new BankImpl();
        System.out.println("Banka je kreirana!");

        try {
            Bank stub = (Bank) UnicastRemoteObject.exportObject(bank,0);
            System.out.println("Banka je exportovana!");

            Registry registry = LocateRegistry.createRegistry(port);
            registry.rebind("/Bank",stub);
            System.out.println("Banka je bindovana!");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
