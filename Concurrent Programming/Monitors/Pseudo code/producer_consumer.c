//producer consumer sa bounded bufferom
//Disciplina: SW

monitor producer_consumer{
	T[N] buffer;
	int head=0;
	int tail=0;
	int size=0;

	cond full,empty;

	void produce(T item){
		if(size == N){
			empty.wait();//nema mesta
		}
		size++;
		buffer[tail] = item;
		tail = (tail + 1)%N;
		
		if(full.queue()){//ako neko ceka, jer nema nicega u baferu
			full.sinal();
		}
	}

	T consume(){
		if(size==0){
			full.wait();//cekaj dok neko nesto ne stavi
		}
		T item = buffer[head];
		head= (head + 1) % N;
		size--;

		if(empty.queue()){ //ako neko ceka jer nema mesta
			empty.signal();
		}

		return item;
	}
}
