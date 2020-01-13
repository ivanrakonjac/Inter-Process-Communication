package citanje_podataka;

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

    public String dohvatiIme(int redBr) throws IOException {
        String message = "#dohvatiIme#" + redBr + "#";
        sendMessage(message);
        return receiveMessage();
    }

    public int promeniIme(String ime, int redBr) throws IOException {
        String message = "#promeniIme#" + ime + "#" + redBr + "#";
        sendMessage(message);
        return receiveIntMessage();
    }
}
