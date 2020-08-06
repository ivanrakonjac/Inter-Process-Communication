//filozofi mogu da salju poruke samo u sanducice susednih viljuski, ne mogu da komuniciraju sa ostalim filozofima
//viljuske su procesi i mogu putem sanducica da komuniciraju samo sa filozofima, ne i medjusobno

//zbog nemogucnosti komunikacije izmedju filozofa, moramo nekako unapred resiti deadlock => 
//filozofi ce prvo uzimati viljusku sa manjim rednim brojem, pa sa vecim =>
//filozof sa najvecim rednim brojem ce uzimati viljusku obrnuto od ostalih


const int N = 5;

mbx forkGetBox[N];
mbx forkPutBox[N];
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
		mbx_put(m,forkGetBox[first]); //hej viljusko 1, ja sam taj id, trebas mi
		mbx_get(t,philosopherBox[id],INF,status); //potvrtda da je na raspolaganju
		mbx_put(m,forkGetBox[second]); //hej viljusko 2, ja sam taj id, trebas mi
		mbx_get(t,philosopherBox[id],INF,status); //potvrda da je na raspolaganju
		eat;
		mbx_put(m,forkPutBox[first]); //vise mi nisi potrebna
		mbx_put(m,forkPutBox[second]); //vise mi nisi potrebna
	}
}

procedure Fork(int id){
	msg m;
	bool status;

	while(true){
		mbx_get(m,forkGetBox[id],INF,status);//kome trabam?
		mbx_put(m,philosopherBox[id]); //okej, mozes me koristiti
		mbx_get(m,forkPutBox[id],INF,status); //zavrsio je, opet sam slobodna
	}
}