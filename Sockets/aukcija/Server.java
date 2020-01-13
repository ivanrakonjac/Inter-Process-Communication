package aukcija;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class Server {

    private static BufferedReader in;
    private static PrintWriter out;

    private static int bestOffer=0;
    private static String bestClientHost=null;
    private static String getBestClientPort;

    private static boolean auctionOver=false;
    private static int badOfferCounter=0;


    public static boolean isAuctionOver() {
        return auctionOver;
    }

    private static void sendMsgToBestClient(String poruka){
        try {
            Socket clientSocket = new Socket(bestClientHost,Integer.parseInt(getBestClientPort));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
            out.println(poruka);
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void main(String[] args) throws IOException {

        try{
            System.out.println("Waiting for clients...");
            ServerSocket serverSocket = new ServerSocket(1025); //portNumber naseg servera
            Socket clientSocket;
            StringTokenizer st;

            while (!auctionOver){
                clientSocket = serverSocket.accept();
                System.out.println("Connection established");

                //Pristize ponuda u formi host->port->novaPonuda
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String message = in.readLine();
                System.out.println(message);

                st = new StringTokenizer(message,"#");

                String host = st.nextToken();
                String port = st.nextToken();
                int novaPonuda = Integer.parseInt(st.nextToken());
                System.out.println(""+host+port+novaPonuda);

                out=new PrintWriter(clientSocket.getOutputStream(),true);

                if(novaPonuda>bestOffer){
                    if(bestClientHost!=null){
                        sendMsgToBestClient("Vasa ponuda nazalost vise nije najbolja...");
                    }
                    bestOffer=novaPonuda;
                    bestClientHost=host;
                    getBestClientPort=port;
                    out.println("Vasa ponuda je prihvacena kao najbolja!");
                    badOfferCounter=0;
                }
                else{
                    out.println("Morate ponuditi vise...");
                }

                clientSocket.close();

                if(badOfferCounter==5){
                    if(bestClientHost!=null){
                        sendMsgToBestClient("Cestitamo POBEDILI STE na aukciji");
                        auctionOver=true;
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
