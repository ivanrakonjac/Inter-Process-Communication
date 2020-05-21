package readersWritersSemaphore;

import java.util.concurrent.Semaphore;

public class ReadersWritersSem implements ReadersWriters{
    private Semaphore rw;
    private Semaphore mutexR;
    private Semaphore enter;
    private int readCount;

    public ReadersWritersSem() {
        this.rw = new Semaphore(1);
        this.mutexR = new Semaphore(1);
        this.enter = new Semaphore(1);
        this.readCount = 0;
    }

    @Override
    public void startRead() {
        enter.acquireUninterruptibly();
        mutexR.acquireUninterruptibly();

        readCount++;
        if(readCount == 1) rw.acquireUninterruptibly();

        mutexR.release();
        enter.release();
    }

    @Override
    public void endRead() {
        enter.acquireUninterruptibly();
        mutexR.acquireUninterruptibly();

        readCount--;
        if(readCount == 0) rw.release();

        mutexR.release();
        enter.release();
    }

    @Override
    public void startWrite() {
        enter.acquireUninterruptibly();
        rw.acquireUninterruptibly();
        enter.release();
    }

    @Override
    public void endWrite() {
        rw.release();
    }
}
