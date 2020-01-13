import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class FactorialServer {
    public static void main(String[] args) throws IOException {
        try{
            System.out.println("Waiting for clients...");
            ServerSocket ss = new ServerSocket(9806); //portNumber naseg servera
            Socket soc = ss.accept();
            System.out.println("Connection establieshed");

            BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream())); //prihvatam broj
            int number = Integer.parseInt(in.readLine());

            PrintWriter out = new PrintWriter(soc.getOutputStream(),true); //za slanje obradjenog podatka
            out.println("Posle racunanja na serveru: " + racunanje(number)); //slanje

            ss.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    static int racunanje(int number) {
        return number + 100;
    }
}
