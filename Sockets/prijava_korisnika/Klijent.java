package prijava_korisnika;

import prijava_korisnika.Usluga;

import java.io.IOException;

public class Klijent {


    public static void main(String[] args) throws IOException, InterruptedException {
        Usluga u = new Usluga("localhost",6001);

        System.out.println(u.prijaviMe());
        //Thread.sleep(5000);
        u.zavrsi();

        u.closeSocket();
    }
}
