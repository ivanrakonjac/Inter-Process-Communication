package rezervacija_karata;

/*
Preuzima komunikaciju od serverSocketa i komunicira sa socketom klijenta
 */

import klijent_server.ServerUsluga;

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

    ServerUsluga su = new ServerUsluga();

    public RequestHandler(Server server,Socket clientSocket){
        this.socket = clientSocket;
        this.server=server;

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

    protected void processRequest(String request) throws InterruptedException {
        StringTokenizer st = new StringTokenizer(request, "#");
        String functionName = st.nextToken();
        String arg1 = st.nextToken();

        if (functionName.equals("rezervisi")) {
            int brKarata = Integer.parseInt(arg1);
            server.rezervisi(this,brKarata);
        }/* else if (functionName.equals("zbir")) {
            String arg2 =  st.nextToken();
            int a = Integer.parseInt(arg1);
            int b = Integer.parseInt(arg2);
            int res = su.zbir(a,b);
            sendMessage("" + res);
        }*/
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
