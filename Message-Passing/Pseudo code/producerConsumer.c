
const int B=...;
const int NP=...;
const int NC=...;

mbx serverBox;
mbx cBox[NC];
mbx pBox[NP];

procedure Server{
	msg m;
	bool status=false;
	int buffer[B]
	int head=0, tail=0, cnt=0;
	bool notifyConsumer=false, notifyProducer=false;

	queue producers, consumers;

	while(true){
		if(consumers.size()>0 && notifyConsumer==true){
			m = consumers.remove();
		}
		else if(producers.size()>0 && notifyProducer==true){
			m = producers.remove();
		}
		else{
			mbx_get(m, serverBox, INF, status);
		}

		notifyProducer=false;
		notifyConsumer=false;

		switch(m.opr){
			case 'get':
				if(cnt==0){
					consumers.put(m);
					break;
				}
				else{
					m.data = buffer[head];
					head = (head + 1 ) % B;
					cnt = cnt - 1;

					m.opr = ack;
					mbx_put(m, cBox[m.id]);
					notifyProducer=true; 
				}
				break;

			case 'put':
				if(cnt == B){
					producers.put(m);
					break;
				}
				else{
					buffer[tail] = m.data;
					tail = (tail + 1) % B;
					cnt = cnt + 1;

					m.opr = ack;
					mbx_put(m, pBox[m.id]);
					notifyConsumer = true;
				}
				break;
		}


	}


}