mvn clean install -o

mvn deploy:deploy-file \
-Durl=file:///Users/luan.phm/engineering/Projects/merlin/merlin-asset/deployed-jar \
-Dfile=./target/merlin-asset-0.0.8-SNAPSHOT.jar \
-DgroupId=com.merlin \
-DartifactId=merlin-asset \
-Dpackaging=jar \
-Dversion=0.0.8-SNAPSHOT
