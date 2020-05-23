package theChildCareProblem;

public class Parent extends Thread{
    private int id;
    private ChildCare cc;
    private int num;

    public Parent(int id, ChildCare cc, int num) {
        this.id = id;
        this.cc = cc;
        this.num = num;
    }

    @Override
    public void run() {
        super.run();
        while (true){
            starting();
            boolean temp = cc.bringUpChildren(num);
            if(temp){
                work();
                cc.bringBackChilder(num);
            }
        }
    }

    private void work(){}
    private void starting(){}
}
