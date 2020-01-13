package prijava_korisnika;

import java.io.IOException;

public class Klijent2 {
    public static void main(String[] args) throws IOException, InterruptedException {
        Usluga u = new Usluga("localhost",6001);

        System.out.println(u.prijaviMe());
        Thread.sleep(40000);
        u.zavrsi();

        u.closeSocket();
    }
}
