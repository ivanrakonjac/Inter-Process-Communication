#!/bin/bash
if [ $# -eq 0 ];
then
	echo "Nema prosledjenih parametara!"
	exit 1
fi

komanda=""

while [ $# -gt 0 ]; do
	if [ -f $1 -a -x $1 ];
	then
		if [ -z $komanda ];
		then
			komanda="./$1"
		else
			komanda="$komanda | ./$1"
		fi
	else
		echo "Fajl $1 ne postoji ili nema dozvolu za izvrsavanje"
		exit 2
	fi
	
	shift
done
$komanda
