package firstRMI_add;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) throws RemoteException, MalformedURLException {

        if(System.getSecurityManager()==null){
            System.setSecurityManager(new SecurityManager());
        }


        AddI obj = new AddImpl();
        //firstRMI_add.AddI stub = (firstRMI_add.AddI) UnicastRemoteObject.exportObject(obj,0);
        Registry registry = LocateRegistry.createRegistry(4001);
        Naming.rebind("rmi://localhost:4001/obj",obj);

        System.out.println("Server started!");

    }
}
