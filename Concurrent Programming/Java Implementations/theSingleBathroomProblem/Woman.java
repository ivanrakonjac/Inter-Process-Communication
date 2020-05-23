package theSingleBathroomProblem;

public class Woman extends Thread {
    private Toilet toilet;

    public Woman(Toilet toilet) {
        this.toilet = toilet;
    }

    @Override
    public void run() {
        super.run();
        while (true){
            toilet.enterWoman();
            useToilet();
            toilet.exitWoman();
        }
    }

    private void useToilet(){}
}
