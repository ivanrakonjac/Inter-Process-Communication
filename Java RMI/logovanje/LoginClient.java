package logovanje;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LoginClient {
    public static void main(String[] args) {
        if(System.getSecurityManager()==null){
            System.setSecurityManager(new SecurityManager());
        }
        try {
            Registry registry = LocateRegistry.getRegistry("localhost",8080);
            UlogujSe logovanje = (UlogujSe) registry.lookup("/logovanje");
            boolean uspesno = logovanje.ulogujSe("root","bugarin");
            System.out.println("Logovanje uspesno: " + uspesno);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}
