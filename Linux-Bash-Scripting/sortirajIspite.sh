#!/bin/bash

#sortira ispite iz datoteke zadate kao $1

if [ $# -lt 1 ]
then
	echo "Nema dovoljno parametara"
else
	cat $1 | sed "s/\(..\)\(.\)\(..\)\(.\)\(.*\)/\4\1\2\3\5/" | sort | sed "s/\(.\)\(..\)\(.\)\(..\)\(.*\)/\5/"
fi
