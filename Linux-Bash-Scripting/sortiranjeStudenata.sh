#!/bin/bash

if [ $# -eq 1 ]
then
	if [ -f $1 -a -r $1 ]
	then
		cat $1 | sed "s/\([0-9]*\.\)\ \([0-9]*\/[0-9]*\)\ \([a-z,A-Z]*\)\ \([a-z,A-Z]*\)\ \([0-9]*\)/\4 \3 \1 \2 \5/" | sort | grep -n ".*" |
		sed "s/\([0-9]*\):\([a-z,A-Z]*\)\ \([a-z,A-Z]*\)\ \([0-9]*\.\)\ \([0-9]*\/[0-9]*\)\ \([0-9]*\)/\1. \4 \5 \2 \3 \6/"
	else
		echo "Fajl ili ne postoji ili ne moze da se cita"
	fi
else
	echo "Pogresan broj parametara"
fi
