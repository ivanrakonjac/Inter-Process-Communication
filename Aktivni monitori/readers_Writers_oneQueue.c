chan request(int, string); //ulazni kanal
chan response[](); //za svakog korisnika imamo kanal za odgovor

process Readers_Writers{
	int readCount;
	bool busy;
	queue OKtowork;

	//serverska metoda
	while(true){

		//nije busy i neko ceka i taj neko je citalac => budi ga
		if(!busy && OKtowork.size()>0 && OKtowork.get('opr')=="readRequest"){
			(clientId, opr) = OKtowork.remove();
		}
		//nije busy ili da ima citala i neko ceka u redu i bas taj neko je citalac => budi ga
		else if((!busy || readCount!=0) && OKtowork.size()>0 && OKtowork.get('opr')=="writeRequest"){
			(clientId, opr) = OKtowork.remove();
		}
		//ako nije ni jedno ni drugo dovuci novi zahtev
		else{
			/*
			* primi zahtev preko request kanala
			* clientId - id procesa klijenta
			* opr - operacija koju treba izvrsiti
			*/
			receive request(clientId, opr);
		}

		switch(opr){

			case 'startRead':
				if(busy || OKtowork.size()>0){
					OKtowork.put(clientId, 'readRequest');
					break;
				}

			case 'readRequest':
				readCount = readCount + 1;
				send response[clientId]();
				break;

			case 'endRead':
				readCount = readCount - 1;
				send response[clientId]();
				break;

			case 'startWrite':
				if(readCount!=0 || busy || OKtowork.size()>0){
					OKtowork.put(clientId,'writeRequest');
					break;
				}

			case 'writeRequest':
				busy = true;
				send response[clientId]();
				break;

			case 'endWrite':
				busy = false;
				send response[clientId]();
				break;

		}

	}
}