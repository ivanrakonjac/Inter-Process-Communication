# Pseudo kod primera iz knjige za KDP (ETF)

Imamo sanducice tipa mbx, koji obuhvataju cele brojeve i specijalni simbol ack za potvrdjivanje.

Operacije:
------------
mbx_put(msg m, mbx box):

	-smesta poruku m u sanduce box

mbx_get(msg m, mbx box, time t, bool status)

	-uzima prvu poruku iz sanduceta i smesta je u promenljivu m

	-ako je operacija uspesta status==true, ako je sanduce prazno status==false i m je nedefinisano

	-vreme t je vreme koje se ceka na dohvatanje poruke, o..maxtime, a moze biti i beskonacno INF


Primer:
-------
2 procesa S i R.

Izmedju njih su sanducici A i B.

	S-------->A--------->R

	S<--------B<---------R


S asinhrono salje vrednost i, R sinhrono prima:
------------------------------------------------
	procedure send(int i){
		msg m;
		
		m.data = i;
		mbx_put(m,A)
	}

	procedure receive(int i){
		msg m;
		bool st;

		mbx_get(m,A,INF,st);
		i=m.data;
	}

S asinhrono salje vrednost i, R uslovno prima:
-----------------------------------------------
	procedure send(int i){
		msg m;
		
		m.data = i;
		mbx_put(m,A)
	}

	procedure receive(int i){
		msg m;
		bool st;

		mbx_get(m,A,0,st);
		if(st){
			i=m.data;
		}
		return st;
	}

S asinhrono salje vrednost i, R prima sa intervalom d:
-------------------------------------------------------
	procedure send(int i){
		msg m;
		
		m.data = i;
		mbx_put(m,A)
	}

	procedure receive(int i, int d){
		msg m;
		bool st;

		mbx_get(m,A,d,st);
		if(st){
			i=m.data;
		}
		return st;
	}

S sinhrono salje vrednost i, R sinhrono prima:
----------------------------------------------
	procedure send(int i){
		msg m;
		
		m.data = i;
		mbx_put(m,A);
		mbx_get(m,B,INF,st);
		return m;
	}

	procedure receive(int i){
		msg m;
		bool st;

		mbx_get(m,A,INF,st);
		i=m.data
		m = ack;
		mbx_put(m, B);
	}

Bidirekciona transakcija 
--------------------------
S sinhrono salje vrednost i rezultat cuva u prom x. U slucaju da transakcija ne uspe tokom intervala d, x dobija vrednost 0. R obradjuje zahtev tako sto za dati argument i vrati vrednost fje f(i).

	procedure request(int i, time d, int x){
		msg m;
		bool st;
		
		m.data = i;
		
		mbx_put(m,A);
		mbx_get(m,B,d,st);
		
		if(st) x=m;
		else x=0;

	}

	procedure reply(){
		msg m;
		bool st;
		int i;

		mbx_get(m,A,INF,st);
		i=m.data
		m = f(i);
		mbx_put(m, B);
	}

