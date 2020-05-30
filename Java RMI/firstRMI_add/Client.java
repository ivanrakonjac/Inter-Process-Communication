package firstRMI_add;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        AddI obj = (AddI)Naming.lookup("rmi://localhost:4001/obj");
        int n = obj.add(3,9);
        System.out.println("Rezultat je: " + n);
    }

}
