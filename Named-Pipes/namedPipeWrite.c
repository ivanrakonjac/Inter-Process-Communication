#include <stdio.h> 
#include <stdlib.h>
  
int main() 
{ 
   	FILE* outputFile;
	char buf[80];
	outputFile = fopen("a_pype","w");

	if(outputFile == NULL){
		perror("Error opening pipe");
		exit(1);
	}	

	sprintf(buf,"test da vidim bas sta ce biti\n");
	fwrite(buf,1,80,outputFile);

	sprintf(buf,"test da vidim bas bas sta ce biti\n");
	fwrite(buf,1,80,outputFile);

	fclose(outputFile);
	
} 
