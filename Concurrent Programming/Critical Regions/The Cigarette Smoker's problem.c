region sto{
	bool sibice=false;
	bool papir=false;
	bool duvan=false;

	bool mozeDalje=false;
};

procedure Agent(){
	int n=0;

	while(true){
		n=random(0,2);

		if(n==0){
			region sto{
				sibice=false;
				papir=true;
				duvan=true;
			}
		} else if(n==1){
			region sto{
				sibice=true;
				papir=false;
				duvan=true;
			}
		}else if(n==2){
			region sto{
				sibice=true;
				papir=true;
				duvan=false;
			}
		}

		region{
			mozeDalje=false;
			await(mozeDalje);
		}

	}
}

procedure PusacSaSibicama(){
	while(true){
		region sto{
			await(papir && duvan);
			papir=false;
			duvan=false;
		}

		duvani();

		region{
			mozeDalje=true;
		}
	}
}

procedure PusacSaPapirom(){
	while(true){
		region sto{
			await(sibice && duvan);
			sibice=false;
			duvan=false;
		}

		duvani();

		region{
			mozeDalje=true;
		}
	}
}

procedure PusacSaDuvanom(){
	while(true){
		region sto{
			await(sibice && papir);
			sibice=false;
			papir=false;
		}

		duvani();

		region{
			mozeDalje=true;
		}
	}
}



