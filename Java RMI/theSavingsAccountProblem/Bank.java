package theSavingsAccountProblem;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Bank extends Remote {
    public UserAccount getUserAccount(String name) throws RemoteException;
}

