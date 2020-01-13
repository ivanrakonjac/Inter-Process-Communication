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
  
    sprintf(message.msg,"Test poruka"); 
  
    msgsnd(msgid, &message, sizeof(message), 0); 
   
    printf("Data send is : %s \n", message.msg); 
  
    return 0; 
} 
