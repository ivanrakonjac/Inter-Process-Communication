//automobili iz istog smera mogu bez ogranicenja da prelaze most
//resenje nije fer

const int N=5;

region {
	int northPrelazi=0;
	int northCeka=0;
	
	int southPrelazi=0;
	int southCeka=0;

	int northPreslo=0;
	int southPreslo=0;
} most;


void passNorth(){
	region most{
		
		northCeka++; //ceka da udje na most
		await(southPrelazi==0 && northPreslo<N);
		northCeka--;
		northPrelazi++;

		if(southCeka>0){ //ako neko ceka, vodi racuna o broju koji prelazi
			northPreslo++;
		}
	}

	passTheBridge();

	region most{
		northPrelazi--;
		if(northPrelazi=0){ //ako nema nikog sa ove strane pusti drugu stranu
			southPreslo=0;
		}
	}
}

void passSouth(){
	region most{
		
		southCeka++;
		await(northPrelazi==0 && southPreslo<N);
		southCeka--;
		southPrelazi++;

		if(northCeka>0){
			southPreslo++;
		}
	}

	passTheBridge();

	region most{
		southPrelazi--;//presao je
		if(southPrelazi=0){
			northPreslo=0;
		}
	}
}
