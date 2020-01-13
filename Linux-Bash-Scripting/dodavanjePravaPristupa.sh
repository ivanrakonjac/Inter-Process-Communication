#!/bin/bash

#dodaje prava pristupa fajlovima
#$1 regex kojim fajlovima se dodaje pravo pristupa
#$2 u kom folderu se nalaze ti fajlovi
#$3 koje pravo im se dodaje r/w/x

naziv="rez"
putanja=""
sablon=""

if [ $# -lt 3 ]
then
	echo "Nema dovoljno argumenata"
else
	sablon=$1
	folder=$2
	operacija=$3

	echo $sablon
	echo $folder
	echo $operacija

	for i in $(find $folder -iname "*"| grep $sablon)
	do
		if [ -f $i ]
		then
			chmod o+$operacija $i
			echo "Prava pristupa nad $i uspesno promenjena"
		else
			echo "$i nije pravi fajl"
		fi
	done
fi
