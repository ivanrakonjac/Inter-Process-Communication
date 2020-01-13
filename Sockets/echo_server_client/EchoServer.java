import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) throws IOException {
        try{
            System.out.println("Waiting for clients...");
            ServerSocket ss = new ServerSocket(9806); //portNumber naseg servera
            Socket soc = ss.accept();
            System.out.println("Connection established");

            BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            String str = in.readLine();
            PrintWriter out = new PrintWriter(soc.getOutputStream(),true);
            out.println("Server says:" + str);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
