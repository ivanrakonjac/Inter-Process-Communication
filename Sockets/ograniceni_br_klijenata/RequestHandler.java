package ograniceni_br_klijenata;

/*
Preuzima komunikaciju od serverSocketa i komunicira sa socketom klijenta
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

public class RequestHandler extends Thread{
    protected Socket socket;
    protected PrintWriter out;
    protected BufferedReader in;

    private int maxBrKlijenata;
    private int tempBrKlijenata;

    public RequestHandler(Socket clientSocket,int maxBrKlijenata,int tempBrKlijenata){
        this.socket = clientSocket;
        this.maxBrKlijenata=maxBrKlijenata;
        this.tempBrKlijenata=tempBrKlijenata;

        try{
            out = new PrintWriter(clientSocket.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void run(){
        System.out.println("Pokrenut requestHandler");

        try{
            while(!socket.isInputShutdown()){
                String request = receiveMessage();

                if(!request.equals("")){
                    processRequest(request);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void processRequest(String request) throws IOException {
        StringTokenizer st = new StringTokenizer(request, "#");
        String functionName = st.nextToken();

        if (functionName.equals("opsluziMe")) {
            if(tempBrKlijenata<maxBrKlijenata) {
                sendMessage("Moze naravno");
                Server.incTempBrKlijennata();
            }
            else{
                sendMessage("Zao nam je morate sacekati, opsluzujemo " + Server.getTempBrKlijenata() + " klijenata.");
            }
        }
        else if(functionName.equals("zavrsi")){
            sendMessage("Pozdrav");
            Server.decTempBrKlijennata();
        }
    }

    protected void sendMessage(String message){
        out.println(message);
    }

    protected String receiveMessage() {
        try{
            return in.readLine();
        } catch(IOException exc){ }
        return "";
    }
}
