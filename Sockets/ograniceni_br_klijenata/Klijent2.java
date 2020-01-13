package ograniceni_br_klijenata;

import java.io.IOException;

public class Klijent2 {

    public static void main(String[] args) throws IOException, InterruptedException {
        Usluga u = new Usluga("localhost",6001);

        System.out.println(u.opsluziMe());
        Thread.sleep(15000);
        System.out.println(u.zavrsi());
        u.closeSocket();
    }
}
