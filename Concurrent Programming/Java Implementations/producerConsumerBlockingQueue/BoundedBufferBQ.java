package producerConsumerBlockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BoundedBufferBQ<T> implements Buffer<T> {
    private BlockingQueue<T> buffer;

    public BoundedBufferBQ(int size){
        buffer = new ArrayBlockingQueue<T>(size);
    }
    @Override
    public void put(T x) throws InterruptedException {
        buffer.put(x);
    }

    @Override
    public T get() throws InterruptedException {
        return buffer.take();
    }
}
