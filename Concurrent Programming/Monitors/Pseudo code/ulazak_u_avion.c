monitor ulazak_u_avion{

	Condition prioritet;

	void zelimDaUdjem(int grupa){//pozivaju putnici
		prioritet.wait(grupa);
		if(!prioritet.empty() && prioritet.minRank()<=grupa) prioritet.signal();//prvi iz grupe koji prodje budi ostale
	}

	void pocinjeUlazak(){//poziva stjuardesa
		prioritet.signal();//stjuardesa pusti samo prvog iz grupe
	}
}