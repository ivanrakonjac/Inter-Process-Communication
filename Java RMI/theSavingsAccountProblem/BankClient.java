package theSavingsAccountProblem;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class BankClient {
    public static String host ="localhost";
    public static int port=8080;

    public static void main(String[] args) {
        Bank bank=null;
        UserAccount userAccount=null;
        String name="/Bank";

        if(System.getSecurityManager()==null){
            System.setSecurityManager(new SecurityManager());
        }

        try {
            Registry registry = LocateRegistry.getRegistry(host,port);
            Object remote = registry.lookup(name);
            bank = (Bank) remote;
            System.out.println("Nadjena banka na serveru:" + host + ":" + port);

            userAccount = bank.getUserAccount(name);
            System.out.println(userAccount.toString());

            System.out.println(userAccount.getStatus());
            userAccount.transaction(500);
            System.out.println(userAccount.getStatus());

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }
}
