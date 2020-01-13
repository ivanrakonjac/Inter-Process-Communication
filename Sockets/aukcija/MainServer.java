package aukcija;

import multi_thread_server.ChannelServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
    private final static int N=5; //max number of users simultaneously
    private static boolean[] allocatedPorts = new boolean[N];

    public MainServer(){
        for (int i=0;i<N;i++){
            allocatedPorts[i]=false;
        }
    }

    private static synchronized int getFreePort(){
        for(int i=0;i<N;i++){
            if(!allocatedPorts[i]) {
                allocatedPorts[i] = true;
                return 1201 + i;
            }
        }
        return -1;
    }

    private static int getIndexOfPort(int portNumber){
        if(portNumber <1201 || portNumber >1205) return -1;
        return portNumber-1201;
    }

    public static synchronized void realesePort(int portNumber){
        int index = getIndexOfPort(portNumber);
        if(index==-1) return;
        allocatedPorts[index]=false;
    }

    private static void printPorts(){
        for(int i=0;i<N;i++){
            System.out.println((1201+i) + " = "+allocatedPorts[i]);
        }
    }

    public static void main(String[] args) {
        try{
            printPorts();
            ServerSocket serverSocket = new ServerSocket(1600);
            System.out.println("Server pokrenut...");
            while(true){
                Socket client = serverSocket.accept();
                System.out.println("Client accepted...");

                int newPort = getFreePort();
                if(newPort==-1){
                    System.out.println("TRENUTNO SU SVI RESURSI ZAUZETI...");
                }
                System.out.println("Novi port " + newPort);

                new ChannelServer(newPort).start();

                PrintWriter out = new PrintWriter(client.getOutputStream(),true);
                out.println(""+newPort);

                client.close();
                printPorts();
                System.out.println("Klijent oslobodjen!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
