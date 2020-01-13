#!/bin/bash

if [ $# -gt 8 -o $# -lt 3 ]
then 
	echo "Pogresan broj parametara prosledjen"
else
	s=0
	izlaz=$1
	shift

	for i in $*
	do
		if [ -f $i -a -r $i ]
		then
			let s++
		fi
	done

	if [ $s -gt 2 ]
	then 
		ulaz=""
		while [ ! -z "$1" ]
		do
			if [ -f $1 -a -r $1 ]
			then
				ulaz="$ulaz $1"
			fi
			shift
		done
		cat $ulaz > $izlaz
	else
		echo "Nema dovoljno fajlova"
	fi
fi
