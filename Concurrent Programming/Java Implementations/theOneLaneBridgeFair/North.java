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
            while(!bridge.dozvolaZaPrelazakNorth()){ //dokle god nema dozvole cekaj
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            bridge.north++; //pocinjem da prelazim
        }

        crossing();

        synchronized (bridge){
            bridge.north--; //presao
            if(bridge.north==0){ //ako nema vise nikog sa moje strane
                bridge.northBool=false; //ugasi da neko ceka sa Northa
                if(bridge.southBool){
                    bridge.trenutniSmer='S'; //ako neko ceka na Southu neka dobiju smer
                    bridge.cnt=0; //ponisiti brojac
                    bridge.notifyAll(); //probudi preku stranu
                }
                else bridge.trenutniSmer='/'; //ako niko ne ceka sa preke str mogu oba smera
            }
            else if (bridge.promenaSmera()) bridge.notifyAll(); //ako nekog ima i ispucali smo N vozila menjaj stranu
        }
    }
}
