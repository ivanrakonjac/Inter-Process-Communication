//filozofi mogu da salju poruke samo u sanducice susednih viljuski, ne mogu da komuniciraju sa ostalim filozofima
//viljuske su procesi i mogu putem sanducica da komuniciraju samo sa filozofima, ne i medjusobno

//zbog nemogucnosti komunikacije izmedju filozofa, moramo nekako unapred resiti deadlock => 
//filozofi ce prvo uzimati viljusku sa manjim rednim brojem, pa sa vecim =>
//filozof sa najvecim rednim brojem ce uzimati viljusku obrnuto od ostalih

//razlika u odnosu na resenje 1 je da proces moze imati samo 1 sanduce iz koga cita poruke
//znaci veci broj procesa moze da upisuje u 1 sanduce, samo 1 proces cita iz 1 sanduceta


const int N = 5;

mbx forkBox[N];
mbx philosopherBox[N];

procedure Philosopher(int id){

	int first, second;
	msg m, t;
	bool status;


	//odredjivanje koji proces koju viljusku prvu podize
	if(id != N-1){
		first = id;
		second = (id+1)%N;
	}
	else{
		first = (id+1)%N;
		second = id;	
	}

	while(true){
		think;
		m.id = id;
		mbx_put(m,forkBox[first]); //hej viljusko 1, ja sam taj id, trebas mi
		mbx_get(t,philosopherBox[id],INF,status); //potvrtda da je na raspolaganju
		mbx_put(m,forkBox[second]); //hej viljusko 2, ja sam taj id, trebas mi
		mbx_get(t,philosopherBox[id],INF,status); //potvrda da je na raspolaganju
		eat;
		mbx_put(m,forkBox[first]); //vise mi nisi potrebna
		mbx_put(m,forkBox[second]); //vise mi nisi potrebna
	}
}

procedure Fork(int id){
	msg m, t;
	bool status;
	int pid = -1; //id procesa koji trenutno koristi viljusku
	int waiting = -1;

	while(true){
		
		mbx_get(m,forkBox[id],INF,status);//dohvatam poruku
		
		if(pid!=m.id){//faza zauzimanja
			if(pid==-1){
				mbx_put(t,philosopherBox[id]);
				pid = m.id;
			}else{
				waiting = m.id;
			}
		}
		else{//faza oslobadjanja
			if(waiting!=-1){
				mbx_put(t,philosopherBox[waiting]);
				pid = waiting;
				waiting = -1;
			}else{
				pid = -1;
			}
		}


		 //okej, mozes me koristiti
		mbx_get(m,forkPutBox[id],INF,status); //zavrsio je, opet sam slobodna
	}
}