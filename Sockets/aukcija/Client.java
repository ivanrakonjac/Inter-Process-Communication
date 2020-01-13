package aukcija;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Thread{
    private static int gen_id=0;

    private String host;
    private int port;
    private String ponuda;
    private int id=++gen_id;

    public Client(String host,int port,String ponuda){
        this.host=host;
        this.port=port;
        this.ponuda=ponuda;
    }

    public void run(){
        try {
            Socket socket = new Socket("localhost",1025); //localHost=IP adr computera
            System.out.println("Client started");

            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            out.println("#"+host+"#"+port+"#"+ponuda);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(in.readLine());

            while (!socket.isClosed()){
                BufferedReader inAnswer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message = inAnswer.readLine();
                if(message!=null) System.out.println(message);
            }

            socket.close();
            System.out.println("Socket " + id + " je zatvoren.");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
        Client c1 = new Client("localhost",1025,"500");
        c1.start();
        Client c2 = new Client("localhost",1025,"1500");
        c2.start();
        Client c3 = new Client("localhost",1025,"100");
        c3.start();/*
        Client c4 = new Client("localhost",1025,"200");
        c4.start();
        Client c5 = new Client("localhost",1025,"200");
        c5.start();
        Client c6 = new Client("localhost",1025,"200");
        c6.start();
        Client c7 = new Client("localhost",1025,"200");
        c7.start();*/
    }
}
