package rezervacija_karata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*
Usluge koje user moze da trazi od servera
*/

public class Usluga {
    protected Socket sock=null;
    protected PrintWriter out=null;
    protected BufferedReader in =null;

    private int brRezKarata=0;

    public Usluga(String host, int port) throws IOException {
        sock = new Socket(host,port);
        out = new PrintWriter(sock.getOutputStream(),true);
        in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

        System.out.println("Socket spreman...");
    }

    protected void sendMessage(String message){
        System.out.println("Poruka: " + message);
        out.println(message);
    }

    protected String receiveMessage() throws IOException {
        return in.readLine();
    }

    protected int receiveIntMessage() throws IOException {
        String answer = receiveMessage();
        return Integer.parseInt(answer);
    }

    protected void finalize() throws Throwable {
        super.finalize();
        closeSocket();
    }

    public void closeSocket() throws IOException {
        out.close();
        in.close();
        sock.close();
    }

    public String rezervisi(int brKarata) throws IOException {
        String message = "#rezervisi#" + brKarata + "#";
        sendMessage(message);
        brKarata=Integer.parseInt(receiveMessage());
        return "Rezervisano je " + brKarata + " karata.";
    }

}
