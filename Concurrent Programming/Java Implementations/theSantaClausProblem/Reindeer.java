package theSantaClausProblem;

public class Reindeer extends Thread {
    private int id;
    private SantaClausHouse sc;

    public Reindeer(int id, SantaClausHouse sc) {
        this.id = id;
        this.sc = sc;
    }

    @Override
    public void run() {
        super.run();
        while (true){
            rest();
            sc.wakupSantaR();
            riding();
            sc.exitTheSleigh();
        }
    }

    private void rest(){}
    private void riding(){}
}
