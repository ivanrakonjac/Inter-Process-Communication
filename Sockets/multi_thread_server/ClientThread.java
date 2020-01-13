package multi_thread_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThread extends Thread {
    public static int generic_id=0;

    private int id = ++generic_id;
    private boolean kraj=false;

    public void run(){
        try{
            System.out.println("Client started");
            Socket soc = new Socket("localhost",1200); //localHost=IP adr computera

            //Treba da primi novi broj porta
            BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            int noviBrPorta = Integer.parseInt(in.readLine());
            if(noviBrPorta==-1){
                System.out.println("OK SVI RESURSI SU ZAUZETI");
                soc.close();
            }else {
                System.out.println("Novi broj porta " + noviBrPorta);

                soc.close();
                soc = new Socket("localhost",noviBrPorta);

                BufferedReader inputSaChannela = new BufferedReader(new InputStreamReader(soc.getInputStream()));
                System.out.println(inputSaChannela.readLine());

                while(!kraj){
                    try {
                        System.out.println("Radim jos uvek " + id);
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args){
        ClientThread ct1 = new ClientThread();
        ct1.start();
        ClientThread ct2 = new ClientThread();
        ct2.start();
        ClientThread ct3 = new ClientThread();
        ct3.start();
        ClientThread ct4 = new ClientThread();
        ct4.start();
        ClientThread ct5 = new ClientThread();
        ct5.start();
        ClientThread ct6 = new ClientThread();
        ct6.start();
    }
}
