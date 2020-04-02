//Resenje u kome znamo kog nam je tipa proces koji se budi
//Disciplina: SW

// 2 * cnt + 0 - R //2 je br razlicitih tipova procesa
// 2 * cnt + 1 - W

//0 1 | R W
//2 3 | R W
//4 5 | R W

monitor RW{

	int numw=0;
	int numR=0;
	int cnt=1;

	Condition cond;

	void startRead(){
		if(numW > 0 || cond.queue()){
			int me = 2 * cnt++; //moj prioritet
			cond.wait(me)
		}
		else{
			numR++;
		}
	}

	void startWrite(){
		if(numW>0 || numR>0){
			int me = 1 + 2 * cnt++; 
			cond.wait(me);
		}
		else{
			numW++;
		}
	}


	void endRead(){
		numR--;
		if(numR == 0){
			if(cond.queue()){
				cond.signal();
				numW++;// ovo jer posle citaoca je mogao biti zablokiran samo pisac
			}
		}
	}


	void endWrite(){
		numW--;
		if(cond.queue()){
			int nextPriority = cond.minRank();
			if(nextPriority % 2 == 1){
				//pisac
				cond.signal();
				numW++;
			}
			else{
				//citalac
				while(cond.queue() && (cond.minRank() % 2 ==0)){
					cond.signal();
					numR++;
				}
			}
		}
	}
}