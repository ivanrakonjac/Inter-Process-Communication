//nikako voznja unutar monitora => onda samo 1 moze biti u kruznom toku

monitor kruzniTok{

	const int N=3; //u nasem slucaju
	int[N] cnt; //broj automobila u svakom segmentu, svi cnt[i]==0 kao pocetna vrednost
	Condition[N] enter; //broj automobila na ulazu

	/*za ulazak u tok
	*uslovi:
		-da br automobila isped nas bude 0
		-da br automobila u segmentu sa nase leve strane bude 0
	*/
	void start(int segment){
		if(enter[segment].queue() || cnt[segment]>0){//ceka li neko ispred nas || brAuta u segmentu levo od nas > 0
			enter[segment].wait();		
		}
		cnt[(segment+1)%N]++; //usli smo u dati segment
	}


	/*metoda za prelazak iz segmenta u segment
	*uslova za prelazak iz segmenta u segment NEMA
	*kad predjemo npr. iz segm 3 u segm 1, obavestiti aute koji cekaju na ulazu 3
	*radimo move from varijantu (izlazimo iz segmenta koji je parametar)
	*/
	void moveFrom(int segment){
		cnt[(segment+1)%N]++; //bezuslovno mozemo preci u prvi naredni segment

		//u sustini ovo je metoda leave
		cnt[segment]--;
		while(cnt[segment]==0 && enter[segment].queue()){
			enter[segment].signal();
		}
	}

	/*
	*napustanje kruznog toka
	*/
	void leve(int segment){
		cnt[segment]--; //smanjujemo br automobila u segmentu u kome se nalazimo
		while(cnt[segment]==0 && enter[segment].queue()){//ako sam ja poslednji auto => ja smem da budim nekog, ali mora neko da ceka => inace bi bio live lock,
			enter[segment].signal();
		}
	}

}