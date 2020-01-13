package multi_thread_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ChannelServer extends Thread{
    private ServerSocket mySocket;
    private int port;

    public ChannelServer(int port){
        try {
            mySocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.port = port;
    }

    public void run(){
        try{
            System.out.println("Novi serverski port " + port + " pokrenut...");
            Socket client = mySocket.accept();
            System.out.println("Client accepted...");

            //neki posao

            PrintWriter out = new PrintWriter(client.getOutputStream(),true);
            out.println("Channel server: uspeo sam da mu posaljem!");

            client.close();
            MainServer.realesePort(port);
            mySocket.close();
            System.out.println("Port oslobodjen!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
