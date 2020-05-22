package theOneLaneBridge;

public class Bridge {
    public static final int MAX_AUTA=10;

    public int north;
    public int south;

    public boolean northBool;
    public boolean southBool;
    public char trenutniSmer; //N-North;S-South;/-ni jedno ni drugo
    public int cnt;

    public Bridge(int north, int south){
        this.north=north;
        this.south=south;
        this.northBool=false;
        this.southBool=false;
        this.trenutniSmer='/';
        this.cnt=0;
    }

    public boolean dozvolaZaPrelazakNorth(){
        if(trenutniSmer=='/'){ //ako nema definisanog smera
            trenutniSmer='N'; //neka trenutni smer bude N
            if(southBool) cnt++; //ako neko ceka na southu inc cnt
            return true;
        }
        else if(trenutniSmer=='S'){ //ako vec prelaze sa Southa
            northBool=true; //reci da cekas
            return false; //nema dozvole za prelaz
        }
        else if(trenutniSmer=='N'){ //ako vec prelaze sa Northa
            if(southBool==true) cnt++;  //inc cnt ako neko ceka na southu
            return true; //prodji
        }
        else{
            return false; //ovo je za svaki sl
        }
    }

    public boolean dozvolaZaPrelazakSouth(){
        if(trenutniSmer=='/'){
            trenutniSmer='S';
            if(northBool) cnt++;
            return true;
        }
        else if(trenutniSmer=='N'){
            southBool=true;
            return false;
        }
        else if(trenutniSmer=='S'){
            if(northBool==true) cnt++;
            return true;
        }
        else{
            return false;
        }
    }

    public boolean promenaSmera(){
        if (cnt==MAX_AUTA) {
            if(trenutniSmer=='S'){
                trenutniSmer='N';
            }else{
                trenutniSmer='S';
            }
            return true;
        }
        else return false;
    }
}
