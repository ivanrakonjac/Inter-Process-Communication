#include <stdio.h> 
#include <stdlib.h>

int main() 
{ 
   
   	FILE* inputFile;
	char buf[80];
	inputFile = fopen("a_pype","r");

	if(inputFile == NULL){
		perror("Error opening pipe");
		exit(1);
	}	

	fread(buf,1,80,inputFile);
	printf("%s\n",buf);

	fread(buf,1,80,inputFile);
	printf("%s\n",buf);

	fclose(inputFile); 
} 
