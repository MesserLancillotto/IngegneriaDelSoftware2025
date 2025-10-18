#!/bin/sh
# absolute compuler because I needed it to start 
# with a so that it was on top  of the file list
EXTENSION=""
SCALE=1

if [ "$1" = "" ]
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

for i in $(ls | grep mermaid | grep -v png); 
do
    printf "Compiling %s -> %s.%s\n" $i $i $EXTENSION
    printf "Scale: %d\n" $SCALE
    mmdc -i $i -o $i.$EXTENSION -t dark -b transparent -s $SCALE &> /dev/null
done

exit 0