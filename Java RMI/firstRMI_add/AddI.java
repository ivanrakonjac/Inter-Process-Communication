package firstRMI_add;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AddI extends Remote {
    public int add (int x, int y) throws RemoteException;
}
