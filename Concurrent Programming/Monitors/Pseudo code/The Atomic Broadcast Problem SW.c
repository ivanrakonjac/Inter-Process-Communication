//disciplina: SW

//1 proizvodjac
//N potrosaca

monitor Atomic{

	const int N=...; //br potrosca
	const int B=...; //velicina buffera 


	int buffer[B];
	int brPristupa[B]={0...0}; //koliko se puta neki element procitao

	condition readCondition[N];
	condition writeCondition;

	int smeDaProcita[N]={0...0}; //za svaki potrosac sadrzi koliko proizvoda moze da procita
	int smeDaUpise=B; //koliko proizvoda proizvodjac sme da upise

	int procitao[N]={0...0}; //za svakog potrosaca koliko je elemenata procitao
	int proizveo=0; //koliko je elemenata proizvodjac napravio


	//poziva proizvodjac
	procedure putItem(int vrednost){

		proizveo = proizveo + 1;
		if(proizveo > smeDaUpise) writeCondition.wait();

		buffer[proizveo % N]=vrednost;
		for (int i = 0; i < N-1; i++)
		{
			//dozvoli svakom procesu da procita po jos 1 element
			smeDaProcita[i]++;
			//ako neko ceka da cita sa tog mesta => probudi ga
			if(readCondition[i].queue()) readCondition[i].signal();
		}
	}

	//poziva potrosac
	int getItem(int id){
		//povecam br elem koji je ovaj proc procitao
		procitao[id]=procitao[id] + 1;

		//ako je procitao vise nego sto sme => cekaj 
		if(procitao[id]>smeDaProcita[id]) readCondition[id].wait();

		int item = buffer[procitao % B];

		//povecaj br pristupa za element
		brPristupa[procitao[id] % B] = brPristupa[procitao[id] % B] + 1;

		//ako su svi procitali taj element
		if(brPristupa[procitao[id] % B] == N){
			//dozvoli jos 1 upis
			smeDaUpise++;
			//resetuj na 0 br pristupa elementu
			brPristupa[procitao[id] % B] = 0;
			//ako neko ceka da upise u taj element pusti ga
			if(writeCondition[id].queue()) writeCondition[id].signal();
		}


		return item;

	}




}