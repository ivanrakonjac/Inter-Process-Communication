package diningPhilosphersSemaphore;

import java.util.concurrent.Semaphore;

public class Philosopher extends Thread {
    private int id;
    private int left;
    private int right;

    Semaphore[] forks;
    Semaphore ticket;

    public Philosopher(int id,int N, Semaphore[] forks, Semaphore ticket) {
        this.id = id;
        this.forks = forks;
        this.ticket = ticket;

        this.right = (id+1)%N;
        this.left = id;
    }

    @Override
    public void run() {
        super.run();
        while (true){
            think();
            ticket.acquireUninterruptibly();
            forks[left].acquireUninterruptibly();
            forks[right].acquireUninterruptibly();
            eat();
            forks[left].release();
            forks[right].release();
            ticket.release();
        }
    }

    private void think(){}
    private void eat(){}
}

/*
* ticket ce biti inicijalizovan na 4 tj. na n-1
* a fork[id] na 1
**/