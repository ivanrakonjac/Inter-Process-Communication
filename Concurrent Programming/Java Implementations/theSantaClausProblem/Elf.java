package theSantaClausProblem;

public class Elf extends Thread {
    private int id;
    private SantaClausHouse sc;

    public Elf(int id, SantaClausHouse sc) {
        this.id = id;
        this.sc = sc;
    }

    @Override
    public void run() {
        super.run();
        while (true){
            work();
            sc.wakupSantaE();
            talk();
            sc.exitRoom();
        }
    }

    private void work(){}
    private void talk(){}
}
