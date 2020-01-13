#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#define NUM 1

/*
Child put value in pipe
Parent read thah value and put some other value into secon pipe
Child read parent value
*/

int main(int argc, char* argv[]){
	pid_t pid;
	int mypipefd[2];
	int fd[2];

	int ret;
	char buf[20];

	int share=5;
	int getShare;
	int getParentValue;
	int parentValue=45;

	ret = pipe(mypipefd);

	 if(ret == -1){
                perror("pipe");
                exit(1);
       	 }

	ret=pipe(fd);

	if(ret == -1){
		perror("pipe");
		exit(1);
	}


for(int i=0;i<NUM;i++){

	share = share + 1; 

	pid = fork();
	
	if(pid == 0){
		/*Child*/
		printf("Child process, share = %d\n",share);
		write(mypipefd[1],&share,sizeof(int));
		read(fd[0],&getParentValue,sizeof(int));
		printf("Parent value is %d\n",getParentValue);
		break;
	}
	else{
		/*Parent*/
		printf("Parent process");
		read(mypipefd[0],&getShare,sizeof(int));
		printf("shared value us: %d\n",getShare);
		write(fd[1],&parentValue,sizeof(int));
	}
}

	return 0;
}
