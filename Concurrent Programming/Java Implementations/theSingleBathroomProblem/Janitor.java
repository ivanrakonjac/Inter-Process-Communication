package theSingleBathroomProblem;

public class Janitor extends Thread {
    private Toilet toilet;

    public Janitor(Toilet toilet) {
        this.toilet = toilet;
    }

    @Override
    public void run() {
        super.run();
        while (true){
            toilet.enterJanitor();
            work();
            toilet.exitJanitor();
        }
    }

    private void work(){}
}
