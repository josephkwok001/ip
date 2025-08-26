#!/bin/bash

# create bin directory if it doesn't exist
mkdir -p ../bin

# delete output from previous run
rm -f ACTUAL.TXT

# compile the code into the bin folder
javac -cp ../src/main/java -Xlint:none -d ../bin ../src/main/java/*.java
if [ $? -ne 0 ]; then
    echo "********** BUILD FAILURE **********"
    exit 1
fi

# run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ../bin Leroy < input.txt > ACTUAL.TXT

# compare the output to the expected output
diff ACTUAL.TXT EXPECTED.TXT
if [ $? -eq 0 ]; then
    echo "Test result: PASSED"
else
    echo "Test result: FAILED"
fi