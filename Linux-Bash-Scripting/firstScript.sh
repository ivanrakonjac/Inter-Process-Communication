#!/bin/bash

echo "Hello World"

brojArgumenata=$#;
myName="Ivan"

echo "Pozdrav ja sam $myName"
echo $brojArgumenata

var="Ivan"
var2="Nikola"
let var=var+var2
echo $var

#funkcija bez argumenata
name="Stevan"

getName(){
	local name="ivan"
	echo $name
}


name=$(getName )
echo $name

#funkcija sa argumentima
getNameArg(){
	local ime=$1
	echo $ime
}


name=$(getNameArg "Tesla")
echo "Povratna vrednost je $name"

#ucitavanje teksta u promenljivu

read -p "Zdravo kako se zoves? " userName
echo "Dobrodosao $userName"
read -p "Koliko imas godina? " age

if [ $age -eq 18 ]
then 
	echo "Da mozes da polazes vozacki"
elif [ $age -lt 18 ]
then
	echo "Zao nam je ali ne mozes da polazes"
elif [ $age -gt 18 ] 
then
	echo "Pa zasto vec nisi polozio"
fi

#if petlja drugaciji stil

read -p "Koliko imas neopravdanih?" brNeopr
if((brNeopr>5));
then
	echo "Sram te bilo"
elif((brNeopr<5));
then
	echo "Super si"
fi

#stringovi
str1=""
str2="Avan"
str3="Vikola"

if((str1));
then
	echo "str1 nije null"
fi

if((str1>str2));
then
	echo "Veci je $str1"
else
	echo "Veci je $str2"
fi


#da li fajl postoji
file1="./test_file1"

if [ -e "$file1" ];
then
	echo "$file1 postoji"
else
	echo "$file1 NE postoji"
fi


#provera datuma

read -p "Validate Date: " date

pat="^[0-9]{8}$"

if [[ $date =~ $pat ]];
then
	echo "$date is valid"
else
	echo "$date is not valid"
fi

#case

read -p "How old are you: " age

case $age in
[0-4])
	echo "Too young for school"
	;;

5)
	echo "Go to Kindergarten"
	;;

[6-9])
	grade=$((age-5))
	echo "go to grade $grade"
	;;
*)
	echo "To old for school"
	;;
esac

#while loop

num=1
while [ $num -le 20 ]; do
	if((((num % 2)) == 0 )); then
		echo $num
	fi
	num=$((num+1))
done


#for za prolazak kroz nesto

for (( i=0; i <= 10; i=i+1 )); do
	echo $i
done


for i in {A..Z}; do
	echo $i
done


#prolazak kroz arugmente komandne linije

while (( $# > 0 )); do
	echo $1
	shift
done
