//beskonacna cekaonica

/*musterija:
-dolazi
-budi berberina ako spava
-saceka da berberin zavrsi sisanje
-da javi berberinu da je izasla*/

/*berberin:
-ceka dok se ne pojavi musterija
-sisa musteriju
-isprati musteriju, kada zavrsi sisanje
-ceka da musterija ode*/
 

 monitor barberShop{

 	int barber=0 //da li je brica slobodan/zauzet 1/0
 	int chair=0 //da li je stolica slobodna/zauzeta 0/1
 	int open=0 //da li su vrata otvorena 0/1

 	int val; //za razmenu podataka izmedju klijenta i servera

 	Condition barberAvailable,chairOccupated,doorOpen,customerLeft;

	/*
	*ovu metodu poziva musterija
	-probuditi berberina ako spava
	*/
	void get_haircut(int id){//id da bi se prosledio posao koji treba da se odradi u client-server modelu
		while(barber==0){ //cekaj dok je brica zauzet => dok server nesto odradjuje
			barberAvailable.wait();	
		} 
		barber=barber-1; //brica vise nije slobodan => zauzet je
		chair = chair + 1; //stolica je zauzeta
		val=id;
		chairOccupated.signal(); //budimo drugu stranu (bricu) koja spava, dok se neko ne pojavi => server spava dok nema klijenata
		while(open == 0){
			doorOpen.wait(); //ceka dok ga neko ne isptati (da mu neko otvori vrata)
		}
		open=open-1; //ja sam izasao, vrata vise nisu otvorena
		customerLeft.signal();
	}

	/*
	ovu metodu poziva berberin
	*/
	int get_next_customer(){//vraca ulaz tj. id
		barber = barber + 1; //brica je dosao na posao i moze da primi musteriju
		barberAvailable.signal(); //ja dosao => prvo mora biti pokrenuta serverska strana
		while(chair==0){
			chairOccupated.wait(); //cekaj dok neko ne dodje
		}
		chair = chair - 1; //stolica je ponovo slobodna
		return val;
		//prelazi se na fazu sisanja => izvan monitora
	}

	/*
	*metodu takodje poziva berberin
	*sluzi da isprati musteriju
	*/
	void finished_cut(){
		open=open+1; //vrata su otvorena => 1 proces moze da naupusti
		doorOpen.signal();
		while(open>0)  customerLeft.wait();
	}


 }
