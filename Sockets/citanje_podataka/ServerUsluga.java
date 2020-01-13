package citanje_podataka;

public class ServerUsluga {
    protected int i =0;
    protected String[] nizImena={"Ivan","Nikola","Marko","Janko","Stevan"};

    public String getIme(int redBr){
        return nizImena[redBr];
    }

    public int setIme(String ime,int redBr){
        nizImena[redBr]=ime;
        return 1;
    }
}
