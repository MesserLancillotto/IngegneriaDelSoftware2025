#!/bin/sh
# absolute compuler because I needed it to start 
# with a so that it was on top  of the file list
EXTENSION=""
SCALE=1

if [ $1 = "-h" ] || [ $1 = "--help" ]
then
    printf "Uso\n"
    printf "\nabsolute_compiler.sh ESTENSIONE SCALA\n\n"
    printf "Se ESTENSIONE è tra [png, svg, jpg, jpeg] allora compila con quell'estensione\n"
    printf "Se ESTENSIONE è lasciata vuota o è '*', compila come png\n"
    printf "Se SCALA non è lasciata vuota compila usando il flag -s SCALA\n"
    printf "altrimenti SCALA=1 per gli svg e SCALA=3 per gli altri formati\n"
    exit 0
fi

if [ -z $1 ] || [ $1 -eq "*" ]
then
    EXTENSION="png"
elif [ "$1" = "svg" ] || [ "$1" = "png" ] || [ "$1" = "jpg" ] || [ "$1" = "jpeg" ]
then
    EXTENSION=$1
else
    exit 1
fi

case $1 in
    svg)
        SCALE=1
    ;;
    *)
        SCALE=3
esac

if [ $2 != "" ]
then
    SCALE=$2
fi

for i in $(ls | grep mermaid | grep -v png); 
do
    printf "Compiling %s -> %s.%s\n" $i $i $EXTENSION
    printf "Scale: %d\n" $SCALE
    mmdc -i $i -o $i.$EXTENSION -t dark -b transparent -s $SCALE &> /dev/null
done

exit 0