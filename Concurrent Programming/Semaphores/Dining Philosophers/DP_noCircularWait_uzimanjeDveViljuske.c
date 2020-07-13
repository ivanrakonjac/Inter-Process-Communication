//resenje koje sprecava deadlock uzimanjem atomicno 2 viljuske

const int N=5;

Semaphore forks = 1;
int forks_available[N] = {2, 2, 2, 2, 2};

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
			if(forks_available[i]=2){
				
				forks_available[left]=forks_available[left]-1;
				forks_available[right]=forks_available[right]-1;
				forks.signal();

				eat;

				forks.wait();
				forks_available[left]=forks_available[left]+1;
				forks_available[right]=forks_available[right]+1;
				forks.signal();

				again=false;

			}else{
				forks.signal();
				pause(random());
			}
		}
	}
}
