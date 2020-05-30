package logovanje;

import java.rmi.RemoteException;

public class UlogujSeImpl implements UlogujSe {
    @Override
    public boolean ulogujSe(String username, String password) throws RemoteException {
        if(username.equals("root") && password.equals("123")){
            return true;
        }else{
            return false;
        }
    }
}
