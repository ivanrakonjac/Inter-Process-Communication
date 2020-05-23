package diningPhilosophers;

public class Philosopher extends Thread {
    private int id;
    private Forks forks;

    public Philosopher(int id, Forks forks) {
        this.id = id;
        this.forks = forks;
    }

    @Override
    public void run() {
        super.run();
        while (true){
            think();
            forks.pickup(id);
            eat();
            forks.putdown(id);
        }
    }

    private void think(){}
    private void eat(){}
}
