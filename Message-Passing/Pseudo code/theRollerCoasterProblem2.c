//N putnika i jedno vozilo na toboganu
//putnici se naizmenicno setaju po parku i voze na toboganu
//tobogan prima najvise K putnika, K<N
//voznja toboganom krece samo ako se skupi K putnika


//resenje gde proces moze SAMO 1 sanduce za prijem poruka
//iz 1 sanduceta samo 1 proces cita poruku, a vise procesa upisuje

const int K=...;
const int N=...;

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

		mbx_get(m, passengerBox[id], INF, status);
		m.id = id;
		m.opr = endRide;
		mbx_put(m, coasterBox);	
	}


	while(true){
		walking(id);
		boardCar(id);
		riding(id);
		leaveCar(id);
	}
}


procedure Coaster{
	int i;
	int boardedSize;

	msg boarded[K];
	msg waiting[N];

	int waitingSize, waitingW, waitingR;

	function riding();

	function boardingCar(){
		int i;
		msg m;
		bool status;

		boardedSize=0;
		while(boardedSize!=K){
			if(waitingSize>0){
				boarded[boardedSize]=waiting[waitingR];
				boardedSize++;
				waitingSize--;
				waitingR = (waitingR % N) + 1;
			}
			else{
				mbx_get(m, coasterBox, INF, status);
				boarded[boardedSize]=m;
				boardedSize++;
			}
		}

		m.opr = ack;
		for (int i = 0; i < K; i++)
		{
			mbx_put(m, passengerBox[boarded[i].id]);
		}
	}

	function leavingCar(){
		int i;
		msg m;
		bool status;

		m.opr = ack;
		for (int i = 0; i < K; i++)
		{
			mbx_put(m, passengerBox[boarded[i].id]);
		}

		boardedSize=0;
		while(boardedSize!=K){
			mbx_get(m, coasterBox, INF, status);
			if(m.opr=startRide){
				waiting[waitingW] = m;
				waitingSize++;
				waitingW = (waitingW % N) + 1;
			} 
			else if(m.opr=endRide){
				boardedSize++;
			}
		}
	}

	while(true){
		boardingCar();
		riding();
		leavingCar();
	}
}