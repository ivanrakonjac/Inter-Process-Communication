//samo 1 auto na mostu u jednom momentu

region {
	bool busy=false;
} most;


void pass(){
	region most{
		await(busy);
		busy=true;
	}

	passTheBridge();

	region most{
		busy=false;
	}
}
