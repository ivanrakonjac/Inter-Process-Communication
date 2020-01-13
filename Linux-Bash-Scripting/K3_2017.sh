#!/bin/bash

#programer moze da prekine, prevede novu verziju koda i pokrene taj kod
#$1 je ime programa
#$2 je lokacija programa


ime=""
lokacija=""

if (($# == 2))
then
	ime=$1
	lokacija=$2
	cd $lokacija
	pid=$(ps a | grep $ime | grep -v grep | tr -s " " | cut -d " " -f2)

	if kill $pid; then
		pushd $lokacija
		if make; then
			popd
			$ime
		else
			echo "Greska pri prevodjenju"
			exit 1
		fi
	else
		echo "Greska pri gasenju procesa"
	fi
else
	echo "Unesite samo 2 argumenta"
fi
