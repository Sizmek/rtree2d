#!/bin/bash
sbt -java-home /usr/lib/jvm/jdk1.8.0 clean 'charts -jvm /usr/lib/jvm/jdk-11/bin/java -p overlap=1 -p rectSize=10 -p nearestMax=10 -p nodeCapacity=16 -p partToUpdate=0.1 -p geometry=plane .*'
sbt -java-home /usr/lib/jvm/jdk1.8.0 clean 'charts -jvm /usr/lib/jvm/jdk-11/bin/java -p overlap=10 -p rectSize=10 -p nearestMax=10 -p nodeCapacity=16 -p partToUpdate=0.1 -p geometry=plane .*'
sbt -java-home /usr/lib/jvm/jdk1.8.0 clean 'charts -jvm /usr/lib/jvm/jdk-11/bin/java -p overlap=1 -p rectSize=10 -p nearestMax=10 -p nodeCapacity=4,8,16 -p partToUpdate=0.1 -p geometry=spherical RTree2D.*'
sbt -java-home /usr/lib/jvm/jdk1.8.0 clean 'charts -jvm /usr/lib/jvm/jdk-11/bin/java -p overlap=10 -p rectSize=10 -p nearestMax=10 -p nodeCapacity=4,8,16 -p partToUpdate=0.1 -p geometry=spherical RTree2D.*'
