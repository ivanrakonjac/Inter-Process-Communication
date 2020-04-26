//disciplina: SW

monitor DiningPhilosophers {

	const int N=5;

	enum Stanje{GLADAN,JEDE,RAZMISLJA};
	
	condition mozeDaJede;
	Stanje stanje[5]={RAZMISLJA,RAZMISLJA,RAZMISLJA,RAZMISLJA,RAZMISLJA};
	int number=0;


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

	void testMe(int id){
		//ako niko ne ceka da jede i levi ne jede, a ni desni ne jede i na sve to je jos je i on gladan
		if(mozeDaJede.empty() && stanje[(id+1) % N]!=JEDE && stanje[(id+1) % N]!=JEDE && stanje[id]==GLADAN){
			stanje[id]=JEDE;
		}
	}

	procedure gladanSam(int id){

		int turn;

		stanje[id]=GLADAN; //zelim da jedem
		testMe(id);	//mogu li da jedem	
		
		if(stanje[id]!=JEDE){ //ako ne mogu stani u red, koristeci ticket algoritam
			turn=number;
			number=number+1;
			mozeDaJede.wait(N*turn + id); //N*turn + id mora, jer da nije ovako reseno, prioritet bi zavisio od id-a proc
		}
	}

	bool test(){
		//ako uopste neko ceka
		if(mozeDaJede.queue()){

			//da dobijemo id proc
			int id = mozeDaJede.minRank() % N;
			
			//levi i desni ne jedu i proces je gladan
			if(stanje[(id+1) % N]!=JEDE && stanje[(id+1) % N]!=JEDE && stanje[id]==GLADAN){
				
				stanje[id]=JEDE;
				mozeDaJede.signal();
				return true;

			}else{
				return false;
			}

		}
		else{
			return false;
		}
	}

	procedure jeoSam(int id){
		stanje[id]=RAZMISLJA;

		//da li moze da se pusti prvi koji ceka
		bool ok = test();
		
		//da li moze sledeci ako je prvi probudjen
		if(ok) ok=test();
		
	}


}