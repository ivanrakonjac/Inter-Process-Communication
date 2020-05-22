package theOneLaneBridge;

public class North extends Car {

    public North(int id,Bridge bridge){
        super(id,bridge);
    }

    @Override
    public void run() {
        super.run();
        starting();

        synchronized (bridge){
            while (bridge.south!=0){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            bridge.north++;
        }

        crossing();

        synchronized (bridge){
            bridge.north--;
            if (bridge.north == 0) bridge.notifyAll();
        }
    }
}
