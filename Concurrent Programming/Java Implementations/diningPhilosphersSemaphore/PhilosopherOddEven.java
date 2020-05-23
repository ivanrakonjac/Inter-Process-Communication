package diningPhilosphersSemaphore;

import java.util.concurrent.Semaphore;

public class PhilosopherOddEven extends Thread{
    private int id;
    private int firstodd;
    private int secondeven;

    Semaphore[] forks;

    public PhilosopherOddEven(int id,int N, Semaphore[] forks) {
        this.id = id;
        this.forks = forks;

        if(id%2==1){
            this.secondeven = (id+1)%N;
            this.firstodd = id;
        }else{
            this.firstodd = (id+1)%N;
            this.secondeven = id;
        }
    }

    @Override
    public void run() {
        super.run();
        while (true){
            think();
            forks[firstodd].acquireUninterruptibly();
            forks[secondeven].acquireUninterruptibly();
            eat();
            forks[secondeven].release();
            forks[firstodd].release();
        }
    }

    private void think(){}
    private void eat(){}
}

/*
* fora je sto deadlock resavamo da filozofi sa id%2==1 prvo uzimaju levu pa desnu
* a sa id%2==0 prvo desnu pa levu
* */
