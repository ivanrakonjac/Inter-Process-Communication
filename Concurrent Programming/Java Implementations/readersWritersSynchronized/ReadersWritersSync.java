package readersWritersSynchronized;

public class ReadersWritersSync implements ReadersWriters{
    private int readCount=0;
    private int writeCount=0;

    @Override
    public synchronized void startRead() {
        while (writeCount!=0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        readCount++;
    }

    @Override
    public  synchronized void endRead() {
        readCount--;
        if(readCount==0) notifyAll();
    }

    @Override
    public synchronized void startWrite() {
        while (readCount!=0 || writeCount!=0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        writeCount++;
    }

    @Override
    public synchronized void endWrite() {
        writeCount--;
        notifyAll();
    }
}
