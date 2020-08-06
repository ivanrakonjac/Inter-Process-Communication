//filozofi mogu da salju poruke samo u sanducice susednih viljuski, ne mogu da komuniciraju sa ostalim filozofima
//viljuske su procesi i mogu putem sanducica da komuniciraju samo sa filozofima, ne i medjusobno

//zbog nemogucnosti komunikacije izmedju filozofa, moramo nekako unapred resiti deadlock => 
//filozofi ce prvo uzimati viljusku sa manjim rednim brojem, pa sa vecim =>
//filozof sa najvecim rednim brojem ce uzimati viljusku obrnuto od ostalih

//razlika u odnosu na resenje 1 je da proces moze imati samo 1 sanduce iz koga cita poruke
//znaci veci broj procesa moze da upisuje u 1 sanduce, samo 1 proces cita iz 1 sanduceta


const int N = 5;
const int thinking = 0;
const int hungry = 1;
const int eating = 2;

mbx serverBox;
mbx philosopherBox[N];

procedure Philosopher(int id){
	msg m;
	bool status;

	while(true){
		think;
		
		m.id = id;
		m.opr = pickup;
		mbx_put(m,serverBox);
		mbx_get(m,philosopherBox[id],INF,status);

		eat;
		m.id = id;
		m.opr = putdown;
		mbx_put(m,serverBox);
	}
}

procedure Server{
	msg m;
	bool status;
	int state[N];

	function test(int id){
		if(state[id]==hungry && state[(id+N-1)%N]!=eating && state[(id+1)%N]!=eating){
			state[id]=eating;
			mbx_put(m,philosopherBox[id]);			
		}
	}

	while(true){
		if()
	}
}
