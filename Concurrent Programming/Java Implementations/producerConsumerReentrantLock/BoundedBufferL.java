package producerConsumerReentrantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBufferL<T> implements Buffer<T> {
    private Lock lock;
    private Condition notFull;
    private Condition notEmpty;

    private T[] buffer;
    private int tail, head, count;

    public BoundedBufferL(int size) {
        this.buffer = (T[])new Object[size];
        this.tail = 0;
        this.head = 0;
        this.count = 0;

        this.lock = new ReentrantLock();
        this.notFull = lock.newCondition();
        notEmpty = lock.newCondition();
    }

    @Override
    public synchronized void put(T x) throws InterruptedException {
        lock.lock();
        try {
            while (count==buffer.length){
                notFull.await();
            }
            buffer[tail] = x;
            tail = (tail + 1)% buffer.length;
            count++;
            notEmpty.signal();
        }finally {
            lock.unlock();
        }

    }

    @Override
    public T get() throws InterruptedException {
        try {
            while (count==0){
                notEmpty.await();
            }
            T x = buffer[head];
            head = (head + 1) % buffer.length;
            count--;
            notFull.signal();
            return x;
        }finally {
            lock.unlock();
        }

    }
}
