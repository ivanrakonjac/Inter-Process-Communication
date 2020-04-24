//2 parking mesta
//   |__1__ __2__
//parkiranjem na mesto 2 zaglavljuje se vozilo na mestu 1


region parking{
	bool prvo=false;
	bool drugo=false;
}


int zelimDaParkiram(){
	region parking{
		if(!prvo && !drugo){
			await(prvo || drugo);
		}


		if(prvo && drugo){
			prvo = false;
			return 1;
		}
		else if(prvo){
			prvo=false;
			return 1;
		}
		else if(drugo){
			drugo=false;
			return 2;
		}
	}


}

int zelimDaIsparkiram(int mesto){
	if(mesto==1){
		region parking{
			if(drugo==false) await(!drugo);
			prvo=false;
			return 1;
		}
	}
	else{
		region parking{
			drugo=true;
			return 2;
		}

	}
}