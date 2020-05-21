package readersWritersStampThreadLocal;

import java.util.concurrent.locks.StampedLock;

//resenje brze nego kada se koristi ConcurrentHashMap jer nema sinhronizacije medju nitima

public class ReadersWritersStampThreadLocal implements ReadersWriters{

    private final StampedLock sl;
    private final ThreadLocal<Long> stamps;

    public ReadersWritersStampThreadLocal() {
        this.sl = new StampedLock();
        this.stamps = new ThreadLocal<Long>();
    }

    @Override
    public void startRead() {
        long stamp = sl.readLock();
        stamps.set(stamp);
    }

    @Override
    public void endRead() {
        long stamp = stamps.get();
        sl.unlockRead(stamp);
    }

    @Override
    public void startWrite() {
        long stamp = sl.writeLock();
        stamps.set(stamp);
    }

    @Override
    public void endWrite() {
        long stamp = stamps.get();
        sl.unlockWrite(stamp);
    }
}
