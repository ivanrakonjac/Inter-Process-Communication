package theSavingsAccountProblem;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserAccountImpl extends UnicastRemoteObject implements UserAccount, Serializable {

    private static final long serialVersionUID=1L;
    private float status=0;
    private String name;


    protected UserAccountImpl(String name) throws RemoteException {
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void work(){
        //Radi
    }

    @Override
    public float getStatus() throws RemoteException {
        synchronized (this){
            work();
            return status;
        }
    }

    @Override
    public void transaction(float value) throws RemoteException {
        synchronized (this){
            work();
            while (status+value<0){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            status+=value;
            notifyAll();
        }
    }
}
