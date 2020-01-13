package klijent_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

    protected ServerSocket serverSocket = null;
    protected boolean kraj = false;

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
                Thread t = new RequestHandler(clientSocket);
                t.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        closeSocket();
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
