//ETF, OS1
//SI,IR K2 2019
//Zad 1

//Mutex semafor u kome jedna nit moze imati ugnezdjene pozive wait(), sama nad sobom, bez deadlocka

class Mutex{
private:
	Queue blocked;
	Thread* owner=null;
	int cnt=0;
	int L;

	//2 metode za cuvanje kriticne sekcije
	void lock(int val); 
	void unlock(int val);

protected:
	void block();
	void deblock();
public:
	int signal();
	int wait();
}

int Mutex::signal(){
	if(owner!=Thread::running || cnt==0){
		return -1;
	}else{
		lock(&L);
		cnt++;
		if(cnt!=0){
			unlock(&L);
			return -1;
		}else{
			if(!blocked.isEmpty()){//ako ima procesa koji cekaju
				owner=0;
				deblock(); 
			}
			unlock(&L);
			return 0;
		}
	}
}

int Mutex::wait(){
	if(owner==Thread::running){
		cnt--;
		return 0;
	}
	lock(&L); //Kriticna sekcija
	if(owner) block(); //ako neko vec drzi semafor, zablokiraj niti koja zeli da mu pristupi
	owner=Thread::running;
	cnt--;
	unlock(&L);
	return 0;
}

void Mutex::block(){
	if(setjmp(Thread::running->context)==0){
		//Blokiranje
		blocked.put(Thread:running);
		Thread:running=Scheduler::get();
		longjmp(Thread::running->context,1);
	}else return;
}

void Mutex::deblock(){
	//Deblokiraj
	Thread* t=blocked.get();
	Scheduler::put(t);
}

void Mutex::lock(int val){
	//dok je L==1, uposleno cekaj
	while(test_and_set(L));
}

void Mutex::unlock(int val){
	val=0;
}