package aukcija_vise_niti;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
       try{
           System.out.println("Client started");
           Socket soc = new Socket("localhost",1600); //localHost=IP adr computera

           //Treba da primi novi broj porta
           BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
           int noviBrPorta = Integer.parseInt(in.readLine());
           System.out.println("Novi broj porta " + noviBrPorta);

           soc.close();
           soc = new Socket("localhost",noviBrPorta);

           while(true){
               BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
               System.out.println("Unesite string");
               String str = userInput.readLine();

               PrintWriter out = new PrintWriter(soc.getOutputStream(),true);
               out.println(str);

               BufferedReader odgovor = new BufferedReader(new InputStreamReader(soc.getInputStream()));
               System.out.println(odgovor.readLine());
           }


       }catch (Exception e){
           e.printStackTrace();
       }
    }
}
