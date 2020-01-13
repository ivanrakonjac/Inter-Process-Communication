package aukcija_vise_niti;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ChannelServer extends Thread{
    private ServerSocket mySocket;
    private int port;
    private PrintWriter out;
    private BufferedReader in;

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

            out = new PrintWriter(client.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            //neki posao
            while(!client.isClosed()){
                int ponuda = Integer.parseInt(in.readLine());
                if(ponuda<1000){
                    PrintWriter odgovor = new PrintWriter(client.getOutputStream(),true);
                    odgovor.println("Moramo odbiti Vasu ponudu");
                    System.out.println("Odbijeno!");
                }
                else{
                    out.println("Prihvatamo Vasu ponudu");
                }
            }

            System.out.println("Izasa sam iz whila");

            client.close();
            MainServer.realesePort(port);
            mySocket.close();
            System.out.println("Port oslobodjen!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
