//jedan proizvodjac
//N potrosaca

const int N=...; // kolicina consumera
const int B=...; //vel bafera
Semaphore mutex[B](1),mutexProducer(1), empty(B), full[N](0);
int buffer[B];
int num[B];

int readFromIndex[N];
int writeToIndex;

procedure Producer(){
	int item;

	function make_new(item){}

	function put_item(item){
		mutexProducer.wait();
		buffer[writeToIndex] = item;
		writeToIndex = (writeToIndex % B ) + 1;
		mutexProducer.signal();
	}

	while(true){
		make_new(item); //napravi novi
		empty.wait(); 	//sacekaj da bude mesta
		put_item(item);	//stavi item u data
		for(int i =0; i<N; i++){
			full[i].signal();	//obavesti sve consumere da je postavljen novi elem
		}	
	}
} 

procedure Consumer(int id){
	while(true){
		
		full[id].wait(); //cekaj da bude nesto u baferu
		item = buffer[readFromIndex[id]];

		mutex[readFromIndex[id]].wait();
		num[readFromIndex[id]]++;

		if(num[readFromIndex[id]]==N){
			num[readFromIndex[id]]=0;
			empty.signal();	//kada svi pokupe oslobodi mesto
		}
		mutex[readFromIndex[id]].signal();	//napusti eksl pravo

		readFromIndex[id] = (readFromIndex[id] % B) + 1;

		consume_item(item);
	}
}