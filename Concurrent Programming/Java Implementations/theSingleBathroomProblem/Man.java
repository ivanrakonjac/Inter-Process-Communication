package theSingleBathroomProblem;

public class Man extends Thread {
    private Toilet toilet;

    public Man(Toilet toilet) {
        this.toilet = toilet;
    }

    @Override
    public void run() {
        super.run();
        while (true){
            toilet.enterMan();
            useToilet();
            toilet.exitMan();
        }
    }

    private void useToilet(){}
}
