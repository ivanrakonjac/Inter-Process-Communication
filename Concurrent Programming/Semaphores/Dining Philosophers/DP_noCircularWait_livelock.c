//resenje koje sprecava situaciju slicnu deadlocku livelock
//javljala bi se da zaredom svi filozofi podizu svoje leve viljuske, a
//zatim shvataju da nemaju desne viljuske pre nego i jedan filozof spusti svoju viljusku na sto.
//Ta situavcija se ponavalja u beskonacnost
//RESENJE: uvesti cekanje izmedju spustanja viljuske i provere

const int N=5;

Semaphore mutexfork[N]={1, 1, 1, 1, 1};
bool forks[N]={true, true, true, true, true};

procedure Philospoher(int i){

	int left,right;
	bool first,again;

	left=i;
	right=(i+1) % N;

	while(true){
		think;
		again=true;
		
		while(again){ //radi dok ne uzmes obe viljuske
			mutexfork[left].wait(); 
			first=forks[left];
			forks[left]=false;	//uzmi prvu viljusku
			mutexfork[left].signal();
			
			if(first){ //ako si uspeo da uzmes prvu
				mutexfork[right].wait();
				if(forks[right]){ //ako je druga slobodna
					
					forks[right]=false; //uzmiu drugu viljusku
					mutexfork[right].signal();

					eat;

					mutexfork[left].wait();
					forks[left]=true; //pusti prvu
					mutexfork[left].signal();

					mutexfork[right].wait();
					forks[right]=true;	//pusti drugu
					mutexfork[right].signal();

					again=false;
				}else{ //ako nije druga slobodna
					mutexfork[right].signal();//pusti semafor
					
					mutexfork[left].wait();
					forks[left]=true; //oslobodi prvu viljusku
					mutexfork[left].signal();
					
					pause(random());
				}
			}else{ //ako nisi sacekaj malo
				pause(random());
			}


		}
	}
}
