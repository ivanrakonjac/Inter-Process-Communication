package atomicBroadcast;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AtomicBroadcastBuffer<T> extends Remote {

    public void put(T item) throws RemoteException;

    public T get(int id) throws RemoteException;

}
