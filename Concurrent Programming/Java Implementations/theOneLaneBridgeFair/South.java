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
            while(!bridge.dozvolaZaPrelazakSouth()){ //dokle god nema dozvole cekaj
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            bridge.south++; //pocinjem da prelazim
        }

        crossing();

        synchronized (bridge){
            bridge.south--; //presao
            if(bridge.south==0){ //ako nema vise nikog sa moje strane
                bridge.southBool=false; //ugasi da neko ceka sa Southa
                if(bridge.northBool){
                    bridge.trenutniSmer='N'; //ako neko ceka na Northu neka dobiju smer
                    bridge.cnt=0; //ponisiti brojac
                    bridge.notifyAll(); //probudi preku stranu
                }
                else bridge.trenutniSmer='/'; //ako niko ne ceka sa preke str mogu oba smera
            }
            else if (bridge.promenaSmera()) bridge.notifyAll(); //ako nekog ima i ispucali smo N vozila menjaj stranu i budi ih
        }
    }
}
