//Disciplina: SW

//Izmena: tick budi samo prvi proces iz time queuea, a onda taj probudjeni proverava ima li jos koga, a da ga treba probuditi
//To se ponavlja dokle god ima procea koje treba buditi

monitor Sleep{

	int now = 0;
	Condition time;

	void sleep(long n){
		if(n>0){
			long wakeUp = now + n;
			time.wait(wakeUp);
			if(!time.empty() && time.minRank()<=now) time.singal();
		}
	}


	void tick(){//tick poziva hardver
		now++;
		if(!time.empty() && time.minRank()<=now) time.singal();
	}
}