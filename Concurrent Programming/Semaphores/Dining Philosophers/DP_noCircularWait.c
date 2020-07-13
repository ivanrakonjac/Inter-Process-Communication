//resenje koje sprecava deadlock uklanjanjem cirkularnog cekanja

/*
filozof | redosled alok resursa
-------------------------------
   0    |    0     1
   1    |    1     2
   2    |    2     3
   3    |    3     4
   4    |    0     4 umesto 4 0!!!

*/

const int N=5;

Semaphore mutexfork[N]={1,1,1,1,1};


procedure Philospoher(i = 0...N-2){
	while(true){
		think;
		mutexfork[i].wait();
		mutexfork[i+1].wait();
		eat;
		mutexfork[i].signal();
		mutexfork[i+1].signal();
	}
}

procedure Philospoher(i == N-1){
	while(true){
		think;
		mutexfork[0].wait();
		mutexfork[N-1].wait();
		eat;
		mutexfork[0].signal();
		mutexfork[N-1].signal();
	}
}