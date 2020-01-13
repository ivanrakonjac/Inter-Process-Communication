package prijava_korisnika;

import prijava_korisnika.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

    protected ServerSocket serverSocket = null;
    protected boolean kraj = false;

    private int brKorisnika=0;
    private boolean prviGotov=false;
    private boolean dozvoljenoPrijavljivanje=true;

    public Server(int port){
        try{
            serverSocket = new ServerSocket(port);
            System.out.println("Server pokrenut na portu: " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void closeSocket(){
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopServer(){
        kraj=true;
    }

    public void run(){
        System.out.println("Server pokrenut i ceka zahtev");
        while(!kraj){
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
                System.out.println("Primljen zahtev...");
                RequestHandler rh = new RequestHandler(this, clientSocket);
                rh.start();
                if(dozvoljenoPrijavljivanje) {
                    rh.sendMessage("Prihvaceni ste");
                }
                else{
                    rh.sendMessage("Odbijeni ste");
                    //rh.getSocket().close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        closeSocket();
    }

    public synchronized void prijavi(){
        if(dozvoljenoPrijavljivanje) brKorisnika++;
        System.out.println("Usao je u prijavi");
    }

    public synchronized void zavrsi(){
        if(prviGotov==false){
            prviGotov=true;
            dozvoljenoPrijavljivanje=false;
            System.out.println("Promenjeno");
        }
        System.out.println("Ovo se radi");
        --brKorisnika;
        if(brKorisnika==0){
            prviGotov=false;
            dozvoljenoPrijavljivanje=true;
        }
    }

    public static void main(String[] args) throws IOException {
        Server s = new Server(6001);
        s.start();

        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        s.stopServer();
    }
}
