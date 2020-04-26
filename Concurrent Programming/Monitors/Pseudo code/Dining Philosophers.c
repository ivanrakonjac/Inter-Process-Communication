//disciplina: SW

monitor DiningPhilosophers {

	const int N=5;

	enum Stanje{GLADAN,JEDE,RAZMISLJA};
	
	condition mozeDaJede[5];
	Stanje stanje[5]={RAZMISLJA,RAZMISLJA,RAZMISLJA,RAZMISLJA,RAZMISLJA};

	procedure eat(int id){ }

	procedure think(int id){ }

	procedure Philosophers (int id){
		while(true){
			think(id);
			gladanSam(id);
			eat(id);
			jeoSam();
		}
	}

	procedure gladanSam(int id){
		stanje[id]=GLADAN;
		if(stanje[(id+1) % N]!=JEDE && stanje[(id+1) % N]!=JEDE){
			stanje[id]=JEDE;
		}
		else{
			mozeDaJede[id].wait();
		}
	}

	procedure jeoSam(int id){
		stanje[id]=RAZMISLJA;
		
		int left=(id+1) % N;
		int right=(id+N-1) % N;

		//signal levo
		if(stanje[(left+1) % N]!=JEDE && stanje[(left+1) % N]!=JEDE && stanje[left]==GLADAN){
			stanje[left]=JEDE;
			mozeDaJede[left].signal();
		}

		//signal desnom
		if(stanje[(right+1) % N]!=JEDE && stanje[(right+1) % N]!=JEDE && stanje[right]==GLADAN){
			stanje[right]=JEDE;
			mozeDaJede[right].signal();
		}

	}


}