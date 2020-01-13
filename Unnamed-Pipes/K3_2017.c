#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>

#define READ 0
#define WRITE 1
#define LEFT 0
#define RIGHT 1
#define NUM 3
#define ITERATIONS (1000)

int main(){
	
	int fd[2][NUM][2];
	
	for(int j=0;j<2;j++){
		for(int i=0;i<NUM;i++){
			pipe(fd[j][i]);
		}
	}

	int id=0;
	for(int i = 2;i<=NUM;i++){
		int pid = fork();
		if(pid==0){
			id = i;
			break;
		}
		else{
			id=1;
		}
	}

	//printf("%d\n",id);

	int vrednost = id;
	int share = vrednost/2;

	int left_sh;
	int right_sh;

	int left;
	int right;

	if(id!=NUM){
		right = right + 1 ;
	}
	else {
		right = 1;
	}

	if(id!=1){
		left = id -1;
	}
	else{
		left = NUM;
	}

	write(fd[0][id-1][WRITE],&share,sizeof(int));
	write(fd[1][id-1][WRITE],&share,sizeof(int));
	
	printf("id = %d\n",id);
	printf("levo[%d] = %d, desno[%d] = %d\n",id,share,id,share);
	
	read(fd[0][left][READ],&left_sh,sizeof(int));
	read(fd[1][right][READ],&right_sh,sizeof(int));

	int value = left_sh + right_sh;

	printf("id = %d, val = %d\n",id,value);
	return 0;
}
