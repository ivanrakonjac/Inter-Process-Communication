#include <stdio.h>
#include <stdlib.h> 
#include <sys/types.h> 
#include <sys/ipc.h> 
#include <sys/msg.h> 
#include <unistd.h> 

const int BOX_KEY = 1; 
const int NUM_PROCESS = 3; 

struct msgbuf{
	int mtype;
	int msg;
}message; 

void barrier(int id, int msg_box){
	for(int i=0;i<NUM_PROCESS;i++){
		if(i==id) continue;
		message.mtype= i + 1;
		message.msg = id;
		msgsnd(msg_box,&message,sizeof(message),0);	
	}

	for(int i=0;i<NUM_PROCESS;i++){
		if(i==id) continue;
		msgrcv(msg_box,&message,sizeof(message),id+1,0);
		printf("Primljena poruka %d\n",message.msg);	
	}
}

int main(int argc, char *argv[]) {   

	if (argc != 2) {     
		fprintf(stderr, "Nedovoljno argumenata.\n");     
		return 1;   
	}
	
	int id = atoi(argv[1]);	
        int msg_box = msgget(BOX_KEY, IPC_CREAT | 0666);
      
	int brojac=0;
        while (brojac<5) {
		//Work     
		barrier(id, msg_box);
		sleep(5);
		brojac++;

	}
   
	msgctl(msg_box, IPC_RMID, 0);
	return 0;
}
