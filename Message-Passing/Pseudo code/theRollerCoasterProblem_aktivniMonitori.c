//N putnika i jedno vozilo na toboganu
//putnici se naizmenicno setaju po parku i voze na toboganu
//tobogan prima najvise K putnika, K<N
//voznja toboganom krece samo ako se skupi K putnika


const int K=...;
const int N=...;
const int startRide = 0;
const int endRide = 1;

mbx coasterBox;
mbx passengerBox[N];

procedure Passenger(int id){
	msg m;
	bool status;

	function riding(int id);
	function walking(int id);
	
	function boardCar(int id){
		msg m;
		bool status;

		m.id = id;
		m.opr=startRide;

		mbx_put(m, coasterBox);
		mbx_get(m, passengerBox[id], INF, status);
	}

	function leaveCar(int id){
		msg m;
		bool status;

		m.id = id;
		m.opr = endRide;
		mbx_put(m, coasterBox);	
		mbx_get(m, passengerBox[id], INF, status);
	}


	while(true){
		walking(id);
		boardCar(id);
		riding(id);
		leaveCar(id);
	}
}


procedure Coaster{
	int numeEnter, numeExit;
	int boardedSize;

	msg boarded[K];
	list<msg> msgs;
	msg m;

	function riding();

	function getMsg(msg m){
		bool status;

		if(msgs.size>0 && numeEnter!=K){
			m = msgs.remove();
		}
		else{
			msg_get(m, coasterBox, INF, status);
		}
	}

	function notifyAll(){
		msg m;

		m.opr = ack;
		for (int i = 0; i < K; i++)
		{
			msg_put(m, passengerBox[boarded[i].id])
		}
	}

	numeEnter=0;
	numeExit=0;
	
	while(true){
		getMsg(m);

		switch(m.opr){

			case 'startRide':
				if(numeEnter == K) msgs.put(m);
				else{
					boarded[numeEnter]=m;
					numeEnter++;
					if(numeEnter==K){
						notifyAll();
						numeExit=0;
					}
				}
				break;
			case 'endRide':
				boarded[numeExit] = m;
				numeExit++;
				if(numeExit == K){
					notifyAll();
					numeEnter=0;
				}
				break;
		}
	}
}