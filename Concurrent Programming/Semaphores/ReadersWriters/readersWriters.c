
Semaphore r(0), w(0), e(1); //raspodeljeni binarni semafori (split binary sempahores), jer samo jedan moze imati vrednost 1 u jednom trenutku
int nr=0, nw=0, blockedReaders=0, blockedWriters=0;

procedure Reader(){
	while(true){
		e.wait(); //zbog ekskluzivnog pristupa
		if(nw>0){
			blockedReaders++;
			e.signal(); //pusti ekskluzivno pravo
			r.wait(); //zabodi ovde
		}

		nr++;

		if(blockedReaders>0){
			dr--;
			r.signal();
		}
		else{
			e.signal();
		}

		//citaj

		e.wait();

		nr--;
		if((nr==0) && (dw>0)){
			dw--;
			w.signal();
		}
		else{
			e.signal();
		}
	}
}

procedure Writer(){
	while(true){
		
		e.wait();

		if((nr>0) || (nw>0)){
			blockedReaders++;
			e.signal();
			w.wait();
		}

		nw = nw + 1;

		e.signal();

		//pisi

		e.wait();
		nw = nw - 1;

		if(blockedReaders>0){
			dr--;
			r.signal();
		}
		else if(blockedWriters>0){
			dw--;
			w.signal();
		}
		else{
			e.signal();
		}

	}
}
