//resenje koje sprecava deadlock uzimanjem atomicno 2 viljuske
//umesto uposlenog cekanja blokiranje na semaforu

const int N=5;

PhilospoherState = {thinking, hungry, eating};


Semaphore forks = 1;
Semaphore ok_to_eat[N] = {0, 0, 0, 0, 0};

int forks_available[N] = {2, 2, 2, 2, 2};
PhilospoherState state[N] = {thinking, thinking, thinking, thinking, thinking};

procedure Philospoher(int i){

	int left,right;
	bool again;

	left=(i-1) % N; //levi komsija
	right=(i+1) % N; //desni komsija

	while(true){

		think;
		again=true;
		
		while(again){ //ponavljaj dok ne uzmes obe
			forks.wait();
			state[i]=hungry;

			if(forks_available[i]=2){
				
				forks_available[left]=forks_available[left]-1;
				forks_available[right]=forks_available[right]-1;
				state[i]=eating;
				ok_to_eat[i].signal();
			}

			forks.signal();
			ok_to_eat.wait();

			eat;

			forks.wait();
			state[i]=thinking;
			forks_available[left]=forks_available[left]+1;
			forks_available[right]=forks_available[right]+1;

			if(forks_available[left]==2 && state[left]==hungry){
				forks_available[(left-1) % N]=forks_available[(left-1) % N]-1;
				forks_available[(left+1) % N]=forks_available[(left+1) % N]-1;
				state[left]=eating;
				ok_to_eat[left].signal();
			}

			if(forks_available[right]==2 && state[right]==hungry){
				forks_available[(right-1) % N]=forks_available[(right-1) % N]-1;
				forks_available[(right+1) % N]=forks_available[(right+1) % N]-1;
				state[right]=eating;
				ok_to_eat[right].signal();
			}

			forks.signal();
		}
	}
}
