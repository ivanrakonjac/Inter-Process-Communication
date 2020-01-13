#!/bin/bash

#predji u direktorijum zadat kao prvi arg
#pozovi program zadat kao drugi argument i prosledi mu sve ostale parametre kao arg
#ispisi da li se program uspesno izvrsio ili ne
#vrati se u polazni direktorijum
#ako nema dovoljno arg ispisati gresku

komanda=""
tempdir=""
program=""

if [ $# -ge 2 ]
then
	echo "ima dovoljno argumenata"

	tempdir=$PWD
	cd $1
	program="./$2"
	shift
	shift
	program="$program $@"
	$program
	if $?
	then 
		echo "Program uspesno izvrsen"
	else
		echo "Program nije uspesno izvrsen"
	fi
	cd $tempdir
else
	echo "Nema dovoljno argumenata"
fi
