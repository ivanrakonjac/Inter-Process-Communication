package ograniceni_br_klijenata;

import ograniceni_br_klijenata.Usluga;

import java.io.IOException;

public class Klijent {


    public static void main(String[] args) throws IOException, InterruptedException {
        Usluga u = new Usluga("localhost",6001);

        System.out.println(u.opsluziMe());
        Thread.sleep(30000);
        System.out.println(u.zavrsi());

        u.closeSocket();
    }
}
