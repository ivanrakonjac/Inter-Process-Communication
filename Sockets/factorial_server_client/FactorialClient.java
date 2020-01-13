import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class FactorialClient {
    public static void main(String[] args) throws IOException {
        try{
            System.out.println("Client started");
            Socket soc = new Socket("localhost",9806); //localHost=IP adr computera

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in)); //prihvata strim sa ulaza
            System.out.println("Enter a number");
            int number = Integer.parseInt(userInput.readLine());

            PrintWriter out = new PrintWriter(soc.getOutputStream(),true); //za slanje podataka ka serveru
            out.println(number); //salje broj serveru

            BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            System.out.println(in.readLine());

            soc.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
