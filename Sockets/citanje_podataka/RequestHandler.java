package citanje_podataka;

/*
Preuzima komunikaciju od serverSocketa i komunicira sa socketom klijenta
*/

import citanje_podataka.ServerUsluga;

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

    ServerUsluga su = new ServerUsluga();

    public RequestHandler(Socket clientSocket){
        this.socket = clientSocket;

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

    protected void processRequest(String request) {
        StringTokenizer st = new StringTokenizer(request, "#");
        String functionName = st.nextToken();
        String arg1 = st.nextToken();

        if (functionName.equals("dohvatiIme")) {
            int redniBroj = Integer.parseInt(arg1);
            String ime = su.getIme(redniBroj);
            sendMessage("" + ime);
        } else if (functionName.equals("promeniIme")) {
            String ime = arg1;
            String arg2 =  st.nextToken();
            int redniBr = Integer.parseInt(arg2);
            int odg = su.setIme(ime,redniBr);
            sendMessage("" + odg);
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
