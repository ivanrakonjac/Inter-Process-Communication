#!/bin/bash

ukupno=0
postoji=0

while [ $# -ge 1 ]
do
	let ukupno++
	if [ -f $1 ]
	then
		let postoji++
	fi
	shift
done
echo "Stvarno posotji $postoji od $ukupno fajlova"
