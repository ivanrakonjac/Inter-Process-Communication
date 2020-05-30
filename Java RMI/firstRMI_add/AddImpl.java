package firstRMI_add;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AddImpl extends UnicastRemoteObject implements AddI {

    protected AddImpl() throws RemoteException {
        super();
    }


    @Override
    public int add(int x, int y) throws RemoteException {
        return x+y;
    }


}
