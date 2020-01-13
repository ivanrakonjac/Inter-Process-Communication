#!/bin/bash

#za stampanje cele datoteke
#while (( $# > 0 )); do
#	cat $1
#	shift
#done

#prvi argument daje pocetne reci
#drugi argument daje datotetku iz koje se stampa red, ako pocinje na pocetnju rec

for i in $(cat $1)
do
	#cat $2 | grep "^$i.*"
	echo  $i
done
