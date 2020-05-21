package readersWritersReentrantRWLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadersWritersReeRWLock implements ReadersWriters{
    ReadWriteLock rw;
    Lock readLock,writeLock;

    public ReadersWritersReeRWLock() {
        this.rw = new ReentrantReadWriteLock();
        this.readLock = rw.readLock();
        this.writeLock = rw.writeLock();
    }


    @Override
    public void startRead() {
        readLock.lock();
    }

    @Override
    public void endRead() {
        readLock.unlock();
    }

    @Override
    public void startWrite() {
        writeLock.lock();
    }

    @Override
    public void endWrite() {
        writeLock.unlock();
    }
}
