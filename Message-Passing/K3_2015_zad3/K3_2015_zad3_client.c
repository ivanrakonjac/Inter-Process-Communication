#include <stdio.h>
#include <sys/ipc.h> 
#include <sys/msg.h>
#include <stdlib.h>
#include <unistd.h>

#define KEY 16
#define M 5
#define N 3
#define len sizeof(char)

struct message{
	int mtype;
	char msg[1];
}message;

int main(int argc,char* argv[]){
	int requestQueue = msgget(KEY,0666 | IPC_CREAT);
	int responseQueue = msgget(KEY,0666 | IPC_CREAT);
	
	int streetID = atoi(argv[2]);

	message.mtype = 1;
	message.msg[0] = (char)1;

	msgsnd(requestQueue,&message,sizeof(message),0);
	printf("Zahtev poslat, %c\n",message.msg[0]);
	msgrcv(responseQueue,&message,len,2,0);
	printf("Zahtev odobren, %c\n",message.msg[0]);
	
	sleep(10);

	message.mtype = 3;
	message.msg[0] = (char)1;

	msgsnd(requestQueue,&message,sizeof(message),0);
	printf("Izlazim\n");
}
