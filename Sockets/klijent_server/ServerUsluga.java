package klijent_server;

public class ServerUsluga {
    protected int i =0;

    public int setI(int ni){
        int tmp = i;
        i = ni;
        return tmp;
    }

    public int zbir(int a,int b){
        return a+b+i;
    }
}
