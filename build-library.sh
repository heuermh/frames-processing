#!/bin/bash

mkdir frames
mkdir frames/library
cp COPYING frames
cp README frames
cp library.properties frames
cp -R src frames
cp -R examples frames
cd src
javac -source 1.8 -target 1.8 -classpath "../lib/processing-core-3.3.6.jar" org/dishevelled/processing/frames/*.java
jar cvf ../frames/library/frames.jar org/dishevelled/processing/frames/*.class
cd ..
