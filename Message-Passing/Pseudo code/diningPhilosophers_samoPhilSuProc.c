//samo filozofi su procesi, viljuske ne
//izbeci mrtvu blokadu vracanjem prve viljuske, nakon ne dobijanja druge posle odredjenovg vremena


const int N = 5;
const int MAXPAUSE = 1000;

mbx fork[N];


procedure Philosopher{
	msg m;
	bool status;
	int d;

	//svaki proces dodace 1 poruku u fork[id]
	mbx_put(m,fork[id]);

	while(true){
		think;
		mbx_get(m, fork[id], INF, status);//zauzima prvu
		mbx_get(m, fork[(id+1)%N], d, status);//ceka na zauzimanje druge vreme d

		if(status){//ako je zauzeo jede i vraca ih
			eat;
			mbx_put(m,fork[(id+1)%N]);
			mbx_put(m,fork[id]);
		}else{//ako nije vraca prvu i ceka
			mbx_put(m,fork[id]);
			pause(random(MAXPAUSE));
		}

	}
}
