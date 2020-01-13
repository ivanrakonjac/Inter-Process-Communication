package klijent_server;

import java.io.IOException;

public class Klijent {


    public static void main(String[] args) throws IOException {
        Usluga u = new Usluga("localhost",6001);

        System.out.println(u.zbir(10,60));
        System.out.println(u.setI(10));
        System.out.println(u.setI(15));

        u.closeSocket();
    }
}
