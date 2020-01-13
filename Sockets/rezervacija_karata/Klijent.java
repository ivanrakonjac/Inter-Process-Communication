package rezervacija_karata;

import rezervacija_karata.Usluga;

import java.io.IOException;

public class Klijent {


    public static void main(String[] args) throws IOException {
        Usluga u = new Usluga("localhost",6001);

        System.out.println(u.rezervisi(30));

        u.closeSocket();
    }
}
