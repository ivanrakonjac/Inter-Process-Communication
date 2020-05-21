package readersWritersStamp;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.StampedLock;

public class ReadersWritersStamp implements ReadersWriters{

    private final StampedLock sl;
    private Map<Thread, Long> stamps;

    public ReadersWritersStamp() {
        this.sl = new StampedLock();
        this.stamps = new ConcurrentHashMap<>();
    }

    @Override
    public void startRead() {
        long stamp = sl.readLock();
        stamps.put(Thread.currentThread(),stamp);
    }

    @Override
    public void endRead() {
        long stamp = stamps.remove(Thread.currentThread());
        sl.unlockRead(stamp);
    }

    @Override
    public void startWrite() {
        long stamp = sl.writeLock();
        stamps.put(Thread.currentThread(),stamp);
    }

    @Override
    public void endWrite() {
        long stamp = stamps.remove(Thread.currentThread());
        sl.unlockWrite(stamp);
    }
}
