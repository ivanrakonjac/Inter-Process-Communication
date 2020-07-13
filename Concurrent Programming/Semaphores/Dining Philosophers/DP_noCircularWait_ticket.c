//resenje koje sprecava deadlock sprecavanjem da u kriticnu sekciju udje vise od 4 procesa
//jer da udje nastao bi circular wait nad resursima

const int N=5;

Semaphore mutexfork[N]={1,1,1,1,1};
Semaphore ticket = N-1;


procedure Philospoher(int i){

	int left = i;
	int right = (i+1) % N;

	while(true){
		think;

		ticket.wait();

		mutexfork[first].wait();
		mutexfork[second].wait();
		
		eat;
		
		mutexfork[first].signal();
		mutexfork[second].signal();
	
		ticket.signal();
	}
}
