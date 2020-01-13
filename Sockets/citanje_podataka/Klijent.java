package citanje_podataka;

import citanje_podataka.Usluga;

import java.io.IOException;

public class Klijent {


    public static void main(String[] args) throws IOException, InterruptedException {
        Usluga u = new Usluga("localhost",6001);

        System.out.println(u.dohvatiIme(0));
        System.out.println(u.dohvatiIme(1));
        System.out.println(u.dohvatiIme(3));
        u.promeniIme("Stanivukoje",3);
        System.out.println(u.dohvatiIme(3));
        u.closeSocket();
    }
}
