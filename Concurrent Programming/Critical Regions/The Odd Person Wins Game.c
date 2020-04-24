region izbor{
	bool igraj=true;
	bool sviOdigrali=false;
	bool dalje=false;

	int pobednik=0;

	int pismoBrojac=0;
	int glavaBrojac=0;

	int pismoProces=0;
	int glavaProces=0;

	bool igrao[3]={false,false,false};
	bool videoRez[3]={false,false,false};
}


procedure sudija(){
	while(true){

		region izbor{
			await(igrao[0] && igrao[1] && igrao[2]);

			if(pismoBrojac!=0 && glavaBrojac!=0){ //postoji raspodela 2:1 => imamo pobednika
				if(glavaBrojac<pismoBrojac){
					pobednik=glavaProces;
				}else{
					pobednik=pismoProces;
				}
				dalje=false;
				igraj=false;
			}else{
				dalje=false;
				pobednik=-1;
			}
			sviOdigrali=true;

			await(videoRez[0] && videoRez[1] && videoRez[2]);

			pobednik=0;

			pismoBrojac=0;
			glavaBrojac=0;

			pismoProces=0;
			glavaProces=0;

			for (int i = 0; i < 3; i++)
			{
				igrao[i]=false;
				videoRez[i]=false;
			}

			dalje=true;
		}

	}
}


procedure igrac(int redniBr){//redni brojevi idu od 1
	int id=redniBr;
	bool winner;
	int status=0; //1==pismo ; 2==glava
	while(igraj){

		winner=false;

		region izbor{
			
			status=baciNovcic(id);
			igrao[id-1]=true;
			await(sviOdigrali);
			winner=pobednik;
			videoRez[id]=true;
			await(dalje);

		}
		
	}
}

int baciNovcic(int id){
	rezultat=random(1,2); //1==pismo, 2==glava

	region izbor{
		if(rezultat==1){ //pismo je
			pismoBrojac++;
			pismoProces=id;
		}
		else{ //glava je
			glavaBrojac++;
			glavaProces=id;
		}
	}
}