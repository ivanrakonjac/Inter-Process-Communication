package theSleepingBarberProblem;

public class Customer extends Thread {
    private BarberShop barberShop;
    private int id;

    public Customer(BarberShop barberShop, int id) {
        this.barberShop = barberShop;
        this.id = id;
    }

    @Override
    public void run() {
        super.run();
        starting(); //prilazi radnji
        boolean result = barberShop.getHaircut(id); //trazi sisanje
        System.out.println("Musterija " + id + " je osisana: " + result);
    }

    public void starting(){

    }
}
