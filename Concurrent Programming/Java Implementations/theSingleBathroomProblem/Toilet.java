package theSingleBathroomProblem;

public class Toilet {
    private static int N=10;
    private int numWoman;
    private int numMan;
    private int numChild;
    private int numJanitor;

    public Toilet() {
        this.numWoman = 0;
        this.numMan = 0;
        this.numChild = 0;
        this.numJanitor = 0;
    }

    public synchronized void enterWoman(){
        while (((numWoman+numChild)==N) || numMan==0 || numJanitor==0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        numWoman++;
        if((numWoman==2) && (numChild!=0)) notifyAll(); //mozda postoji zena koja hoce napolje,
                                                        //ali ne sme jer dete ne sme ostati samo
    }

    public synchronized void exitWoman(){
        while ((numWoman==1) && (numChild!=0)){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        numWoman--;
        notifyAll(); //ako neko ceka da udje
    }

    public synchronized void enterMan(){
        while (((numMan+numChild)==N) || numWoman==0 || numJanitor==0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        numMan++;
        if((numMan==2) && (numChild!=0)) notifyAll(); //mozda postoji covek koja hoce napolje, ali ne sme jer dete ne sme ostati samo
    }

    public synchronized void exitMan(){
        while ((numMan==1) && (numChild!=0)){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        numMan--;
        notifyAll(); //ako neko ceka da udje
    }

    public synchronized void enterChild(){
        while (((numMan + numWoman + numChild)==N) || ((numMan + numWoman)==0) || numJanitor==0 ){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        numChild++;
    }

    public synchronized void exitChild(){
        numChild--;
        notifyAll(); //ako neko ceka da udje
    }

    public synchronized void enterJanitor(){
        while (((numMan + numWoman + numChild)!=0) || numJanitor==0 ){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        numJanitor++;
    }

    public synchronized void exitJanitor(){
        numJanitor--;
        notifyAll(); //ako neko ceka da udje
    }


}
