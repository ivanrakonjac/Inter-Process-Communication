package readersWritersReentrantRWLock;

public interface ReadersWriters {
    public void startRead();
    public void endRead();

    public void startWrite();
    public void endWrite();
}
