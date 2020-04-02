//RW problem, zelimo da procesi prolaze po redosledu dolaska FIFO
//imamo mogucnost koriscenja prioritetnih redova

//queueX.empty() true-red je prazan; false-red nije prazan
//queueX.queue() true-red nije prazan; false-red je prazan
//queueX.peek() vraca prvi na redu po prioritetu
//queueX.minRank() vraca rank procesa max prioriteta(najmanji)

//Disciplina: SW

monitor RW{
	int number=0;
	int next=0;
	int readCnt=0;

	Condition okToWork;

	void startRead(){
		int turn = number;
		number++;

		if(turn!=next){
			okToWork.wait(turn);	
		} 
		readCnt++;
		next++;
		if(!okToWork.empty()){//ako neko ceka u redu
			okToWork.signal();	
		} 
	}

	void endRead(){
		readCnt--;
		if (readCnt==0 && (!okToWork.empty()))
		{
			okToWork.signal();
		}
	}

	void startWrite(){
		int turn=number;
		number++;

		while(readCnt>0 || turn!=number){//while jer oba uslova moraju biti ispunjena
			okToWork.wait(turn);
		}
	}

	void endRead(){
		next++;
		if(!okToWork.empty()){
			okToWork.signal();
		}
	}
}