mvn clean install -o

mvn deploy:deploy-file \
-Durl=file://deployed-jar \
-Dfile=./target/merlin-asset-0.0.9-SNAPSHOT.jar \
-DgroupId=com.merlin \
-DartifactId=merlin-asset \
-Dpackaging=jar \
-Dversion=0.0.9-SNAPSHOT
