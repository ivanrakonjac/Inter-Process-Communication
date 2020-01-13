package prijava_korisnika;

/*
Preuzima komunikaciju od serverSocketa i komunicira sa socketom klijenta
 */

import prijava_korisnika.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

public class RequestHandler extends Thread{
    protected Socket socket;
    protected Server server;
    protected PrintWriter out;
    protected BufferedReader in;

    public RequestHandler(Server server, Socket clientSocket){
        this.socket = clientSocket;
        this.server=server;

        try{
            out = new PrintWriter(clientSocket.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Socket getSocket() {
        return socket;
    }

    public void run(){
        System.out.println("Pokrenut requestHandler");

        try{
            while(!socket.isInputShutdown()){
                String request = receiveMessage();

                if(!request.equals("") && request!=null){
                    processRequest(request);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void processRequest(String request) throws InterruptedException {
        StringTokenizer st = new StringTokenizer(request, "#");
        String functionName = st.nextToken();

        if (functionName.equals("prijavi")) {
            server.prijavi();
        }else if (functionName.equals("zavrsi")) {
            server.zavrsi();
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
