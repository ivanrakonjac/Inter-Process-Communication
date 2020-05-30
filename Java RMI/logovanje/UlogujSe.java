package logovanje;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UlogujSe extends Remote {
    public boolean ulogujSe(String username,String password) throws RemoteException;
}
