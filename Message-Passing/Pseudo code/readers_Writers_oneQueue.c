const int NR=15, NW=5;
const startRead = 0;
const endRead = 1;
const startWrite = 2;
const endWrite = 3;

mbx rBox[NR];
mbx wBox[NW];
mbx serverBox;

procedure reading(){};
procedure writing(){};


procedure Reader(int id){
	msg m;
	bool status;

	while(true){
		//startRead
		m.id = id;
		m.opr = startRead;
		mbx_put(m, serverBox);
		mbx_get(m, rBox[id], INF, status);

		reading();

		//endRead
		m.id = id;
		m.opr = endRead;
		mbx_put(m, serverBox);
	}
}

procedure Writing(int id){
	msg m;
	bool status;

	while(true){
		//startWrite
		m.id = id;
		m.opr = startWrite;
		mbx_put(m, serverBox);
		mbx_get(m, rBox[id], INF, status);

		writing();

		//endWrite
		m.id = id;
		m.opr = endWrite;
		mbx_put(m, serverBox);
	}
}

process Server{
	msg m;
	bool status;

	int readCount=0;
	bool busy=false;
	bool OKtoeadsignal=false;
	bool OKtowritesignal=false;

	queue OKtowork;

	while(true){

		//nije busy i neko ceka i taj neko je citalac => budi ga
		if(!busy && OKtowork.size()>0 && OKtowork.get('opr')=="readRequest"){
			m = OKtowork.remove();
		}
		//nije busy ili da ima citala i neko ceka u redu i bas taj neko je citalac => budi ga
		else if((!busy || readCount!=0) && OKtowork.size()>0 && OKtowork.get('opr')=="writeRequest"){
			m = OKtowork.remove();
		}
		//ako nije ni jedno ni drugo dovuci novi zahtev
		else{
			mbx_get(m, serverBox, INF, status)
		}

		switch(opr){

			case 'startRead':
				if(busy || OKtowork.size()>0){
					OKtowork.put(m);
					break;
				}
				else{
					readCount = readCount + 1;
					m.opr = ack;
					mbx_put(m, rBox[m.id]);
				}

			case 'endRead':
				readCount = readCount - 1;
				break;

			case 'startWrite':
				if(readCount!=0 || busy || OKtowork.size()>0){
					OKtowork.put(clientId,'writeRequest');
					break;
				}
				else{
					busy = true;
					m.opr = ack;
					mbx_put(m, wBox[m.id]);
				}
				break;

			case 'endWrite':
				busy = false;
				break;

		}

	}
}