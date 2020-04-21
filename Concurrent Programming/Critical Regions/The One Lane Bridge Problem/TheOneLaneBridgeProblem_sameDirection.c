//automobili iz istog smera mogu bez ogranicenja da prelaze most
//resenje nije fer

region {
	int cntNorth=0;
	int cntSouth=0;
} most;


void passNorth(){
	region most{
		await(cntSouth==0);
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
