package multi_thread_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
       try{
           System.out.println("Client started");
           Socket soc = new Socket("localhost",1200); //localHost=IP adr computera

           //Treba da primi novi broj porta
           BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
           int noviBrPorta = Integer.parseInt(in.readLine());
           System.out.println("Novi broj porta " + noviBrPorta);

           soc.close();
           soc = new Socket("localhost",noviBrPorta);

           BufferedReader inputSaChannela = new BufferedReader(new InputStreamReader(soc.getInputStream()));
           System.out.println(inputSaChannela.readLine());

           while(true){
               System.out.println("Ide");
           }
       }catch (Exception e){
           e.printStackTrace();
       }
    }
}
