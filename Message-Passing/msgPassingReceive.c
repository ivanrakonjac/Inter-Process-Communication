#include <stdio.h>
#include <sys/ipc.h> 
#include <sys/msg.h>
#include <string.h> 
   
struct msg { 
    long mesg_type; 
    char msg[100]; 
} message; 
  
int main() 
{ 
    key_t key; 
    int msgid; 
   
    key = 11; 
  
    msgid = msgget(key, 0666 | IPC_CREAT); 
    message.mesg_type = 1;  

    msgrcv(msgid, &message,sizeof(message), 1, 0); 
   
    printf("Data received is : %s \n", message.msg); 
  
    return 0; 
} 
