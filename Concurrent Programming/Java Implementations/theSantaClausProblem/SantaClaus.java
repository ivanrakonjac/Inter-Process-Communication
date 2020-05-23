package theSantaClausProblem;

public class SantaClaus extends Thread {
    private int dir;
    private SantaClausHouse sc;

    public SantaClaus(SantaClausHouse sc) {
        this.sc = sc;
    }

    @Override
    public void run() {
        super.run();
        while (true){
            dir=sc.sleeping();
            if(dir==SantaClausHouse.ELVES){
                talk();
                sc.showoutElves();
            }
            else {
                riding();
                sc.unharnessReindeers();
            }
        }
    }

    private void talk(){}
    private void riding(){}
}
