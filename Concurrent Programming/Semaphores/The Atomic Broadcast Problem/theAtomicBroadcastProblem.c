//jedan proizvodjac
//N potrosaca

const int N=...;
Semaphore mutex(1), empty(1), full[N](0);
int data, num;

procedure Producer(){
	int item;

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
		item = data;

		mutex.wait();	//mutex jer menjam num
		num=num+1;
		if(num==N){
			num=0;
			empty.signal();	//kada svi pokupe oslobodi mesto
		}
		mutex.signal();	//napusti eksl pravo

		consume_item(item);
	}
}