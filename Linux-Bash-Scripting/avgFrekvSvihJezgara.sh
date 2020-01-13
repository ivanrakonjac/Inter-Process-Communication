#!/bin/bash

fajl="/proc/cpuinfo"

frekv=$(cat $fajl | grep MHz |cut -d ":" -f2 | cut -b 2-| cut -d "." -f1)

brojFrekv=0
suma=0

for i in $frekv
do
	
        suma=$((suma + i))
	let brojFrekv++
done

echo "Suma=$suma"
echo "Broj jezgara=$brojFrekv"
echo "Prosek je: $((suma /  brojFrekv))"
