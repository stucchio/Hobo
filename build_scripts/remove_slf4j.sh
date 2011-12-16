#!/bin/bash

function remove_slf4j(){
    local filepath=$1;
    local tmppath=`mktemp`;
    sed '/slf4j/d' $filepath | sed '/LOGGER/d' > $tmppath
    mv $tmppath $1
}
echo "Stripping slf4j from generated java thrift files."
for fn in `ls gen-java/org/styloot/hobo/gen/*.java`;
do
    remove_slf4j $fn;
done;