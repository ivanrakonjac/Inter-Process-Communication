package theOneLaneBridge;

public class South extends Car {
    public South(int id,Bridge bridge){
        super(id,bridge);
    }

    @Override
    public void run() {
        super.run();
        starting();

        synchronized (bridge){
            while (bridge.north!=0){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            bridge.south++;
        }

        crossing();

        synchronized (bridge){
            bridge.south--;
            if (bridge.south == 0) bridge.notifyAll();
        }
    }
}
