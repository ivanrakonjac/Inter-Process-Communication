package prijava_korisnika;

import java.io.IOException;

public class Klijent3 {
    public static void main(String[] args) throws IOException, InterruptedException {
        Usluga u = new Usluga("localhost",6001);

        System.out.println(u.prijaviMe());
        Thread.sleep(30000);
        u.zavrsi();

        u.closeSocket();
    }
}
