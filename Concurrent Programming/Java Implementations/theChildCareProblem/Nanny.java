package theChildCareProblem;

public class Nanny extends Thread {
    private int id;
    private ChildCare cc;

    public Nanny(int id, ChildCare cc) {
        this.id = id;
        this.cc = cc;
    }

    @Override
    public void run() {
        super.run();
        while (true){
            starting();
            cc.nannyEnter();
            work();
            cc.nannyExit();
        }
    }

    private void starting(){}
    private void work(){}
}
