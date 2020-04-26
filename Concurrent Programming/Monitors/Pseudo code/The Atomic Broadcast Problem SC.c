//disciplina: SC

//1 proizvodjac
//N potrosaca

monitor Atomic{

	const int N=...; //br potrosca
	const int B=...; //velicina buffera 


	int buffer[B];
	int brPristupa[B]={N...N}; //koliko se puta neki element procitao

	condition readCondition[N];
	condition writeCondition;

	int readFromindex[N]={0...0}; //za svaki potrosac sadrzi index sa kog sledeci put treba da cita
	int writeToIndex=0; //na koji index treba proizvodjac da pise

	int procitao[N]={0...0}; //za svakog potrosaca koliko je elemenata procitao
	int proizveo=0; //koliko je elemenata proizvodjac napravio


	//poziva proizvodjac
	procedure putItem(int vrednost){

		//dok svaki proces ne procita podatke sa tog mest
		while(brPristupa[writeToIndex]!=N) writeCondition.wait();

		buffer[writeToIndex]=vrednost;
		brPristupa[writeToIndex]=0; //resetujem na 0 da bi svi procitali

		readCondition[writeToIndex].signal_all();
		proizveo=proizveo+1; //ukupan br elemenata koji su proizvedeni
		writeToIndex=(writeToIndex+1) % B; // sledece mesto za upis

	}

	//poziva potrosac
	int getItem(int id){

		//ako sam ja procitao sve sto je proizvedeno => cekam na sledecem elementu za citanje dok se ne napravi
		while(proizveo==procitao[id]) readCondition[readFromindex[id]].wait();

		int vrednost = buffer[readFromindex[id]];
		brPristupa[readFromindex[id]] = brPristupa[readFromindex[id]] + 1;

		//ako neko ceka da pise na to mesto
		if(brPristupa[readFromindex[id]] == N) writeCondition.signall();

		//povecaj broj procitanih elemenata za bas ovaj proces
		procitao[id] = procitao[id] + 1 ;

		//sledeci za citanje
		readFromindex[id] = (readFromindex[id] + 1)	% B;			 
	}

	return vrednost;
	
}