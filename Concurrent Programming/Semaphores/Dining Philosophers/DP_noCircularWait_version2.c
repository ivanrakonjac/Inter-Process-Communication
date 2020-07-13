//resenje koje sprecava deadlock uklanjanjem cirkularnog cekanja
//procesi nece izvrsavati kriticne sekcije istim redosledom


/*
filozof | redosled alok resursa
-------------------------------
   0    |    1     0
   1    |    1     2
   2    |    3     2
   3    |    3     4
   4    |    0     4

*/

const int N=5;

Semaphore mutexfork[N]={1,1,1,1,1};


procedure Philospoher(int i){

	int first;
	int second;

	if(i % 2 == 1){
		first = i;
		second = (i+1) % N;
	}
	else{
		first = (i+1) % N;
		second = i;
	}

	while(true){
		think;
		mutexfork[first].wait();
		mutexfork[second].wait();
		eat;
		mutexfork[first].signal();
		mutexfork[second].signal();
	}
}
