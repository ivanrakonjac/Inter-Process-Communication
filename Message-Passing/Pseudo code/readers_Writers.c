const int NR=15, NW=5;
const startRead = 0;
const endRead = 1;
const startWrite = 2;
const endWrite = 3;

mbx rBox[NR];
mbx wBox[NW];
mxb serverBox;

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

	queue OKtoread, OKtowrite;

	while(true){

		if(OKtoreadsignal && OKtoread.size()>0){
			m = OKtoread.remove();
		}
		else if(OKtowritesignal && OKtowrite.size()>0){
			m = OKtowrite.remove();
		}
		else{
			mbx_get(m, serverBox, INF, status)
		}
		OKtowritesignal = false;
		OKtoreadsignal = false;

		switch(m.opr){

			case 'startRead':
				if(busy || OKtowrite.size()>0){
					OKtoread.put(m);
					break;
				}
				else{
					readCount = readCount + 1;
					OKtoreadsignal = true;
					m.opr = ack;
					mbx_put(m, rBox[m.id]);
				}
				break;

			case 'endRead':
				readCount = readCount - 1;
				if(readCount==0) OKtowritesignal = true;
				break;

			case 'startWrite':
				if(readCount!=0 || busy){
					OKtowrite.put(m);
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
				if(OKtoread.size()>0) OKtoreadsignal = true;
				else OKtowritesignal=true;
				break;

		}

	}
}