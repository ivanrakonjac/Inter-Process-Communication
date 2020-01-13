#include <stdio.h>
#include <sys/ipc.h> 
#include <sys/msg.h>

#define KEY 16
#define M 5
#define N 3

struct message{
	int mtype;
	char msg[1];
}message;

int main(){
	int requestQueue = msgget(KEY,0666 | IPC_CREAT);
	int responseQueue = msgget(KEY,0666 | IPC_CREAT);
	
	int cars = 0;

	while(1){

		if(cars<N){
			printf("Cekam\n");
			msgrcv(requestQueue,&message,sizeof(message),1,0);
			printf("Zahtev primljen, auto pusten\n");
			cars++;
			message.mtype = 2;
			message.msg[0] = (char) 1;
			msgsnd(responseQueue,&message,sizeof(message),0);
			printf("Zahtev na cekanju! cars=%d\n",cars);
		}else{
			printf("Zahtev na cekanju! cars=%d\n",cars);
			msgrcv(requestQueue,&message,sizeof(message),-M,0);
			printf("Auto napustilo!\n");
			cars--;
		}

	}		
}
