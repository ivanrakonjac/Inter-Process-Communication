package theSavingsAccountProblem;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserAccount extends Remote {
    public float getStatus() throws RemoteException;
    public void transaction(float value) throws RemoteException;
}
