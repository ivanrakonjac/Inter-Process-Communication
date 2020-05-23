package theSingleBathroomProblem;

public class Child extends Thread{
    private Toilet toilet;

    public Child(Toilet toilet) {
        this.toilet = toilet;
    }

    @Override
    public void run() {
        super.run();
        while (true){
            toilet.enterChild();
            useToilet();
            toilet.exitChild();
        }
    }

    private void useToilet(){}
}
