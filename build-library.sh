#!/bin/bash

mkdir animation
mkdir animation/library
cp COPYING animation
cp README animation
cp library.properties animation
cp -R src animation
cp -R examples animation
cd src
javac -source 1.6 -target 1.6 -classpath "../lib/processing-core-1.5.1.jar" animation/*.java
jar cvf ../animation/library/animation.jar animation/*.class
cd ..
