//automobili iz istog smera mogu bez ogranicenja da prelaze most
//ali ih u jednom momentu moze biti maksimalno K
//resenje nije fer

const int K=5;

region {
	int cntNorth=0;
	int cntSouth=0;
} most;


void passNorth(){
	region most{
		await(cntSouth==0 && cntNorth<K);
		cntNorth++;

	}

	passTheBridge();

	region most{
		cntNorth--;
	}
}

void passSouth(){
	region most{
		await(cntNorth==0);
		cntSouth++;
	}

	passTheBridge();

	region most{
		cntSouth--;
	}
}
