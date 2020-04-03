//Disciplina: SW

monitor Sleep{

	int now = 0;
	Condition time;

	void sleep(long n){
		if(n>0){
			long wakeUp = now + n;
			time.wait(wakeUp);
		}
	}


	void tick(){//tick poziva hardver
		now++;
		while(!time.empty() && time.minRank()<=now) time.singal();
	}
}