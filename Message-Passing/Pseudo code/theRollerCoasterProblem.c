//N putnika i jedno vozilo na toboganu
//putnici se naizmenicno setaju po parku i voze na toboganu
//tobogan prima najvise K putnika, K<N
//voznja toboganom krece samo ako se skupi K putnika


//resenje gde proces moze imati veci broj sanducica za prijem poruka
//iz 1 sanduceta samo 1 proces cita poruku, a vise procesa upisuje

const int K=...;
const int N=...;

mbx coasterIN, coasterOUT;
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
		mbx_put(m, coasterIN);
		mbx_get(m, passengerBox[id], INF, status);
	}

	function leaveCar(int id){
		msg m;
		bool status;

		mbx_get(m, passengerBox[id], INF, status);
		m.id = id;
		mbx_put(m, coasterOUT);	
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
	msg boarded[K];

	function riding();

	function boardingCar(){
		int i;
		msg m;
		bool status;

		for (int i = 0; i < K; i++)
		{
			mbx_get(m, coasterIN, INF, status);
			boarded[i] = m;
		}

		for (int i = 0; i < K; i++)
		{
			mbx_put(m, passengerBox[boarded[i].id]);
		}
	}

	function leavingCar(){
		int i;
		msg m;
		bool status;

		for (int i = 0; i < K; i++)
		{
			mbx_put(m, passengerBox[boarded[i].id]);
		}

		for (int i = 0; i < K; i++)
		{
			mbx_get(m, coasterIN, INF, status);
		}
	}

	while(true){
		boardingCar();
		riding();
		leavingCar();
	}
}