chan request(int, string); //ulazni kanal
chan response[](); //za svakog korisnika imamo kanal za odgovor

process Readers_Writers{
	int readCount;
	bool busy;
	bool OKtoeadsignal;
	bool OKtowritesignal;
	queue OKtoread, OKtowrite;

	//serverska metoda
	while(true){

		if(OKtoreadsignal && OKtoread.size()>0){
			(clientId, opr) = OKtoread.remove();
		}
		else if(OKtowritesignal && OKtowrite.size()>0){
			(clientId, opr) = OKtowrite.remove();
		}
		else{
			/*
			* primi zahtev preko request kanala
			* clientId - id procesa klijenta
			* opr - operacija koju treba izvrsiti
			*/
			receive request(clientId, opr);
		}
		OKtowritesignal = false;
		OKtoreadsignal = false;

		switch(opr){

			case 'startRead':
				if(busy || OKtowrite.size()>0){
					OKtoread.put(clientId, 'readRequest');
					break;
				}

			case 'readRequest':
				readCount = readCount + 1;
				OKtoreadsignal = true;
				send response[clientId]();
				break;

			case 'endRead':
				readCount = readCount - 1;
				if(readCount==0) OKtowritesignal = true;
				send response[clientId]();
				break;

			case 'startWrite':
				if(readCount!=0 || busy){
					OKtowrite.put(clientId,'writeRequest');
					break;
				}

			case 'writeRequest':
				busy = true;
				send response[clientId]();
				break;

			case 'endWrite':
				busy = false;
				if(OKtoread.size()>0) OKtoreadsignal = true;
				else OKtowritesignal=true;
				send response[clientId]();
				break;

		}

	}
}