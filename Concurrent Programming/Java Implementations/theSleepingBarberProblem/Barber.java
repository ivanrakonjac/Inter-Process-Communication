package theSleepingBarberProblem;

public class Barber extends Thread {
    private BarberShop barberShop;
    private int customer;

    public Barber(BarberShop barberShop) {
        this.barberShop = barberShop;
    }

    @Override
    public void run() {
        super.run();
        while (true){
            customer = barberShop.getNextCustomer(); //dohvati novu musteriju
            shaving(customer); //sisaj musteriju
            barberShop.finishedCut(); //zavrsi sisanje
        }
    }

    private void shaving(int customer){

    }
}
