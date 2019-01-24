#! /bin/bash

echo "->Building Angular project"

pushd $NOTEME_HOME/src/main/angular && ng build --prod
echo "->Cleaning resources/static/*"
rm -rf  ../resources/static/*
echo "->Copying dist/angular/* to resources/static/*"
cp -R ./dist/angular/* ../resources/static/

pushd $NOTEME_HOME
./gradlew build

if [ -z "$1" ]
  then
    echo "->Done"
    exit
fi

if [ $1 = "run" ]; then
    echo "->Running SpringBoot application"
    ./gradlew bootRun
fi


if [ $1 = "jar" ]; then
    echo "->Packing SpringBoot application to jar"
    ./gradlew bootJar
fi

echo "->Done"
